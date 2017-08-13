package com.swellwu.service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author swellwu
 * @create 2017-08-13-22:47
 */
@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSimpleMail() throws Exception {
        boolean success = mailService.sendSimpleMail("***@qq.com","test simple mail"," hello this is simple mail");
        Assert.assertTrue(success);
    }
}