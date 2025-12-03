package com.fafa.bigeventbackend.manager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class CosManagerTest {

    @Autowired
    private CosManager cosManager;

    @Test
    public void testDeleteFile() {
        cosManager.deleteObject("Avator_upload/bd8b6305-796c-4962-8d59-1664295853d9-孤独1.webp");
    }

    @Test
    public void testextractKeyFromUrl() {
        String url = "https://big-event-1349256487.cos.ap-guangzhou.myqcloud.com/Avator_upload/bd8b6305-796c-4962-8d59-1664295853d9-孤独1.webp";
        System.out.println(cosManager.extractKeyFromUrl(url));
    }
}