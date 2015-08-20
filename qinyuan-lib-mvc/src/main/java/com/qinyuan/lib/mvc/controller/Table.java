package com.qinyuan.lib.mvc.controller;

import java.util.List;

/**
 * Class used to show table in web page
 * Created by qinyuan on 15-7-23.
 */
public interface Table extends PaginationItemFactory<Table.Row> {
    List<String> getAliases();

    List<String> getHeads();

    List<String> getHeadStyles();

    List<Row> getRows(int firstResult, int maxResults);

    List<Row> getRows();

    public static class Row {
        private final Integer id;
        private final Object[] cols;

        public Row(Integer id, Object[] cols) {
            this.id = id;
            this.cols = cols;
        }

        public Integer getId() {
            return id;
        }

        public Object[] getCols() {
            return cols;
        }
    }
}
