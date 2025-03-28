package com.codecon.backend.rule;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DatabaseCleanupRule extends TestWatcher {

    private final DataSource dataSource;
    private final JdbcTemplate jdbcTemplate;


    @Override
    protected void finished(Description description) {
        clearDB();
    }

    @Override
    protected void failed(Throwable e, Description description) {
        clearDB();
    }

    public void clearDB() {

            jdbcTemplate.execute("DROP SCHEMA public CASCADE");
            jdbcTemplate.execute("CREATE SCHEMA public");

    }

}
