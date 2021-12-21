package com.yizhuoyan.txtgen.module.vm.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yizhuoyan.common.exception.DataAlreadyExistsException;
import com.yizhuoyan.common.exception.DataNotFoundException;
import com.yizhuoyan.txtgen.module.dm.dao.DataClassFieldEntityDao;
import com.yizhuoyan.txtgen.module.dm.dao.EnumClassFieldEntityDao;
import com.yizhuoyan.txtgen.module.vm.ao.TemplateViewFileQueryAo;
import com.yizhuoyan.txtgen.module.vm.dao.ViewFileEntityDao;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileTypeEnum;
import com.yizhuoyan.txtgen.support.file.FileService;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileAddAo;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileModAo;
import com.yizhuoyan.txtgen.module.vm.ao.ViewFileQueryAo;
import com.yizhuoyan.txtgen.module.vm.entity.ViewFileEntity;
import com.yizhuoyan.txtgen.module.vm.service.ViewFileEntityManageService;
import com.yizhuoyan.txtgen.support.importfile.ImportStatement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ViewFileEntityManageServiceImpl implements ViewFileEntityManageService {

    private final ViewFileEntityDao viewEntityDao;
    private final DataClassFieldEntityDao dataClassFieldEntityDao;
    private final EnumClassFieldEntityDao enumClassFieldEntityDao;
    private final FileService fileService;


    @Override
    public ViewFileEntity getByExample(ViewFileEntity exp) throws Exception {
        Optional<ViewFileEntity> one = viewEntityDao.findOne(Example.of(exp));
        if (one.isPresent()) {
            ViewFileEntity viewFileEntity = one.get();
            String fileContent = fileService.readFileContent(viewFileEntity.getFileLocation());
            viewFileEntity.setFileContent(fileContent);
            return viewFileEntity;
        }
        return null;
    }

    private void determineUnique(String namespace, String name,ViewFileTypeEnum fileType) {
        ViewFileEntity exp = new ViewFileEntity();
        exp.setNamespace(namespace);
        exp.setName(name);
        exp.setFileType(fileType);
        long count = viewEntityDao.count(Example.of(exp));
        if (count > 0) {
            throw new DataAlreadyExistsException("命名空间{}下名称{}已存在", namespace, name);
        }
    }


    private String cleanNamespace(String namespace) {
        namespace = StrUtil.trimToNull(namespace);
        if (namespace == null) {
            return "";
        }
        if (namespace.startsWith("/")) {
            namespace = namespace.substring(1);
        }
        if (namespace.endsWith("/")) {
            namespace = namespace.substring(0, namespace.length() - 2);
        }
        return namespace;
    }

    public void add(ViewFileAddAo ao) throws Exception {
        String namespace = cleanNamespace(ao.getNamespace());
        String name = ao.getName();
        determineUnique(namespace, name,ao.getFileType());
        ViewFileEntity e = new ViewFileEntity();
        e.setNamespace(namespace);
        e.setName(name);
        e.setFileType(ao.getFileType());
        e.setGeneratePath(ao.getGeneratePath());
        String savePath = namespace + "/" + name + "." + ao.getFileType().getValue();
        fileService.replaceFile(savePath, ao.getFileContent());
        e.setFileLocation(savePath);
        viewEntityDao.save(e);
    }

    @Transactional
    public void modify(ViewFileModAo ao) throws Exception {
        ViewFileEntity old = viewEntityDao.getById(ao.getId());
        if (old == null) {
            throw new DataNotFoundException("数据{}不存在", ao.getId());
        }
        String newNamespace = ao.getNamespace();
        if (newNamespace != null) {
            newNamespace = cleanNamespace(newNamespace);
            if (!Objects.equals(newNamespace, old.getNamespace())){
                determineUnique(newNamespace, old.getName(),old.getFileType());
                old.setNamespace(newNamespace);
            }
        }
        String newName = ao.getName();
        if (newName != null && !Objects.equals(newName, old.getName())) {
            determineUnique(old.getNamespace(), newName,old.getFileType());
            old.setName(newName);
        }
        String newGeneratePath = ao.getGeneratePath();
        if (newGeneratePath != null && !Objects.equals(newGeneratePath, old.getGeneratePath())) {
            old.setGeneratePath(newGeneratePath);
        }

        String newFileContent = ao.getFileContent();
        if (newFileContent != null) {
            String namespace = cleanNamespace(old.getNamespace());
            String savePath = namespace + "/" + old.getName() + "." + old.getFileType().getValue();
            fileService.replaceFile(savePath, newFileContent);
            old.setFileLocation(savePath);
        }
        viewEntityDao.flush();
        log.debug("更新完毕{}", old);
    }

    @Override
    public ViewFileEntity load(Long id) throws Exception {
        ViewFileEntity e = viewEntityDao.getById(id);
        if (e == null) {
            throw new DataNotFoundException("数据不存在");
        }
        String fileContent = fileService.readFileContent(e.getFileLocation());
        e.setFileContent(fileContent);
        return e;
    }

    @Override
    public List<ViewFileEntity> query(ViewFileQueryAo ao) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("查询参数:{}", ao);
        }
        ViewFileEntity probe = new ViewFileEntity();
        probe.setNamespace(ao.getNamespaceLike());
        probe.setName(ao.getNameLike());
        probe.setFileType(ao.getFileType());
        Example<ViewFileEntity> exp = Example.of(probe, ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING));
        return viewEntityDao.findAll(exp);
    }

    @Override
    public List<ViewFileEntity> queryTemplateView(TemplateViewFileQueryAo ao) throws Exception {
        return viewEntityDao.listTemplateViewFile();
    }

    @Override
    public void remove(Long... ids) throws Exception {
        for (Long id : ids) {
            ViewFileEntity e = viewEntityDao.getById(id);
            if (e != null) {
                viewEntityDao.delete(e);
                fileService.deleteFile(e.getFileLocation());
            }
        }
    }

}
