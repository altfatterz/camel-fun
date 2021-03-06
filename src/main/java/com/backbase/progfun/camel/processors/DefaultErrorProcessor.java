package com.backbase.progfun.camel.processors;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.json.simple.JSONObject;

/**
 *
 */
public class DefaultErrorProcessor implements Processor {

    public static final String STATUS_CODE = "statusCode";

    @Override
    public void process(Exchange exchange) throws Exception {

        final Map<String, String> errorDetails = new LinkedHashMap<String, String>();
        errorDetails.put("code", "500");
        errorDetails.put("message", "Unexpected server error occurred");

        final JSONObject result = new JSONObject();
        result.put("error", errorDetails);

        final Message out = exchange.getOut();
        out.setHeader(STATUS_CODE, 500);
        out.setBody(result);
    }

}
