package com.github.uuidcode.springboot.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class CoreTest {
    public String getHost() {
        return "http://localhost:8080";
    }
}
