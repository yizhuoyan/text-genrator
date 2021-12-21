package com.yizhuoyan.txtgen.support.file;

import com.yizhuoyan.txtgen.configuration.properties.ApplicationProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileService {

    final ApplicationProperties applicationProperties;




    public void replaceFile(String path,String content)throws Exception{
        Path actualPath=loadActualPath(path);
        if(!Files.deleteIfExists(actualPath)){
            Files.createDirectories(actualPath.getParent());
        }
        if(content==null){
            content="";
        }
        Files.write(actualPath, content.getBytes(StandardCharsets.UTF_8) );
    }

    public boolean isExists(String path){
        return Files.exists(loadActualPath(path));
    }

    public boolean deleteFile(String path)throws Exception{
        Path actualPath=loadActualPath(path);
        return Files.deleteIfExists(actualPath);
    }

    public String readFileContent(String fileLocation) throws Exception{
        Path actualPath=loadActualPath(fileLocation);
        return new String(Files.readAllBytes(actualPath),StandardCharsets.UTF_8);
    }

    private Path loadActualPath(String path){
        return Paths.get(applicationProperties.getFileSaveLocation(),path);
    }
}
