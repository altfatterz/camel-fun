package com.backbase.progfun.camel.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;

/**
 * Created with IntelliJ IDEA.
 * User: zoltan
 * Date: 10/24/13
 * Time: 11:15 AM
 * To change this template use File | Settings | File Templates.
 */

public class MyProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        final Message in = exchange.getIn();

        final Object body = in.getBody();
        System.out.println("Body:" + body);

        final String fromRouteId = exchange.getFromRouteId();
        System.out.println("FromRouteId:" + fromRouteId);

        throw new Exception("error occurred");

        //To change body of implemented methods use File | Settings | File Templates.
    }
}
