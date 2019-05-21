package com.example.alipay;

import com.example.common.util.IdWorker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlipayApplicationTests {

    @Test
    public void contextLoads() {
    }

    @Test
    public void test1() throws Exception {
//        String id = UUID.randomUUID().toString();
//        String content = "47.99.244.14:9000/alipay/F2FPay";
//        String codeurl = String.format("F://qr/qr-2.png");
//        QRCodeUtil.encode(content, null, codeurl, true);
        IdWorker idWorker = new IdWorker();
        for (int i = 0; i < 10; i++) {
            long l = idWorker.nextId();
            String s = String.valueOf(l);
            System.out.println(s + "长度：" + s.length());

        }
    }

    @Test
    public void test2() {
    }

}
