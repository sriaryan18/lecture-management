package com.learning_platform.lectureMgmt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
class LectureMgmtApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Test
	void contextLoads() {
		try (Connection connection = dataSource.getConnection()) {
			assertNotNull(connection);
			assertTrue(connection.isValid(1));
			assertFalse(connection.isClosed());
		} catch (SQLException e) {
			fail("Database connection test failed: " + e.getMessage());
		}
	}

}
