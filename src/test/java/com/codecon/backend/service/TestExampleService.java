package com.codecon.backend.service;

import com.codecon.backend.rule.DatabaseCleanupRule;
import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class, SqlScriptsTestExecutionListener.class})
@FieldDefaults(level = AccessLevel.PUBLIC)
public class TestExampleService {

    @Rule
    @Autowired
    DatabaseCleanupRule databaseCleanupRule;

    @Autowired
    ExampleService exampleService;

    @Test
    @Sql(scripts = {"classpath:initial_db.sql",  "classpath:service/example_service/initial_for_update_example.sql"})
    @ExpectedDatabase(value = "classpath:service/example_service/expected_for_update_example.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldUpdateExample() {
        Example example = new Example();
        example.setExampleString("example");
        example.setExampleLong(100L);

        exampleService.update(example, 2L);
    }

    @Test
    @Sql(scripts = {"classpath:initial_db.sql",  "classpath:service/example_service/initial_for_create_example.sql"})
    @ExpectedDatabase(value = "classpath:service/example_service/expected_for_create_example.xml", assertionMode = DatabaseAssertionMode.NON_STRICT)
    public void shouldCreateExample() {
        Example example = new Example();
        example.setExampleString("new_example");
        example.setExampleLong(100L);

        exampleService.create(example);
    }

}