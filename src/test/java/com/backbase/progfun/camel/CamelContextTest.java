package com.backbase.progfun.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration("classpath:app-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CamelContextTest {

    @Autowired
    private CamelContext context;

    @Test
    public void myContext() {
        final ProducerTemplate producerTemplate = context.createProducerTemplate();

        producerTemplate.sendBody("direct://start", "Hello World");
    }


}
