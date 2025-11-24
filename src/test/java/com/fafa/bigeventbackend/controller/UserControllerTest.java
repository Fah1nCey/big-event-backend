package com.fafa.bigeventbackend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Test
    void register() {
        userController.register("test", "123456");
    }
}