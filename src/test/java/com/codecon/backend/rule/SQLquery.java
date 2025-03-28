package com.codecon.backend.rule;

import lombok.Getter;

@Getter
public enum SQLquery {

    FIND_ALL_TABLES("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE table_schema = 'public'"),
    DROP_TABLE_BY_NAME("DROP TABLE %s CASCADE");

    private final String query;

    SQLquery(String query) {
        this.query = query;
    }

}
