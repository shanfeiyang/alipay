package com.example.common.util;

import org.springframework.util.ClassUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Time;
import java.util.UUID;

/**
 * @Author: shanfeiyang
 * @Date: 2019/3/19 8:50
 * @Version 1.0
 */
public class Test {
    @org.junit.Test
    public void test1() throws Exception {
        BigDecimal b = new BigDecimal(888);
        BigDecimal a = new BigDecimal(888);
        int i = a.compareTo(b);
        System.out.println(i);
    }
}
