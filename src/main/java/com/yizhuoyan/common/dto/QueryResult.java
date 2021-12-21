package com.yizhuoyan.common.dto;


import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@SuppressWarnings("unchecked")
@Data
public class QueryResult implements Serializable {
    private final int totalRows;
    private final List rows;

    public QueryResult(List rows) {
        this.rows = rows;
        this.totalRows = rows.size();
    }

    public QueryResult(long totalSize, List rows) {
        this.totalRows = (int) totalSize;
        this.rows = rows;
    }

    public int getTotalPages(int pageSize) {
        final int tr = this.totalRows;
        if (tr <= 0) return 0;
        int tp = tr / pageSize;
        if ((tr % pageSize) != 0) {
            tp++;
        }
        return tp;
    }


    @SuppressWarnings("unchecked")
    public <T> QueryResult transferRows(Function<T, Map> transfer) {
        if (this.totalRows <= 0) {
           return this;
        }
        final int z = this.rows.size();
        final List<Map> arr = new ArrayList<>(z);
        for (int i = 0; i < z; i++) {
            arr.add(transfer.apply((T) this.rows.get(i)));
        }
        return new QueryResult(this.totalRows,arr);
    }

}
