package com.backbase.progfun.camel;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Message;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.ModelCamelContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.backbase.progfun.camel.processors.DefaultErrorProcessor;

@ContextConfiguration("classpath:app-config.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DefaultErrorProcessorIT {

    @Autowired
    private ModelCamelContext context;

    @Test
    public void myContext() throws Exception {

        context.getRouteDefinitions().get(0).adviceWith(context, new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                // intercept sending to mock:start and throw an exception
                interceptSendToEndpoint("direct:start")
                        .skipSendToOriginalEndpoint().throwException(new Exception("error"));
            }
        });

        final ProducerTemplate producerTemplate = context.createProducerTemplate();
        try {
            producerTemplate.sendBody("direct:start", "Hello World");
            fail("Should have thrown CamelExecutionException, but did not");
        } catch (CamelExecutionException e) {
            final Message out = e.getExchange().getOut();
            assertThat(out.getBody().toString(), is("{\"error\":{\"code\":\"500\",\"message\":\"Unexpected server error occurred\"}}"));
            assertThat(out.getHeader(DefaultErrorProcessor.STATUS_CODE).toString(), is("500"));
        }
    }


}
