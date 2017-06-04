package com.mrrusof.thecourtroom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.TestPropertySource;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(properties="spring.profiles.active=test")
public class ApplicationTests {

	@Test
	public void contextLoads() {
	}

}
