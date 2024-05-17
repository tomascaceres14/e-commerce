package com.tomasdev.akhanta;

import com.tomasdev.akhanta.controller.HomeController;
import org.bson.assertions.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HomeControllerTest {

    HomeController controller;

    @Test
    public void getAssociatesAndArticles() {
        Assertions.assertNotNull(controller.findAllAssociates(0).getBody());
        Assertions.assertNotNull(controller.findAllArticles(0).getBody());
    }

}
