package com.backbase.progfun.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Dummy processor
 */
public class MyProcessor implements Processor {

    private static Logger LOGGER = LoggerFactory.getLogger(MyProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        LOGGER.info("MyProcessor called");
    }
}
