package com.backbase.progfun.camel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.ErrorHandlerBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.OnExceptionDefinition;
import org.json.simple.JSONObject;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backbase.progfun.camel.processors.DefaultFailureProcessor;

@ContextConfiguration("classpath:app-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class CamelContextTest {

    @Autowired
    private CamelContext context;

    @Test
    public void myContext() throws Exception {

        final ProducerTemplate producerTemplate = context.createProducerTemplate();
        try {
            producerTemplate.sendBody("direct:start", "Hello World");
            fail("Should have thrown CamelExecutionException, but did not");
        } catch (CamelExecutionException e) {
            final Message out = e.getExchange().getOut();
            assertThat(out.getBody().toString(), is("{\"error\":{\"code\":\"500\",\"message\":\"Unexpected server error occurred\"}}"));
            assertThat(out.getHeader(DefaultFailureProcessor.STATUS_CODE).toString(), is("500"));
        }
    }


}
