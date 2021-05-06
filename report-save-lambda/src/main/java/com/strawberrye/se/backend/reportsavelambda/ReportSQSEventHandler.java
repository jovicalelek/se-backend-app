package com.strawberrye.se.backend.reportsavelambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ReportSQSEventHandler implements RequestHandler<SQSEvent, Void> {

    private final ReportsTableClient reportsTableClient = new ReportsTableClient();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        List<SQSEvent.SQSMessage> records = sqsEvent.getRecords();
        for (SQSEvent.SQSMessage record :
                records) {
            context.getLogger().log("Message:");
            context.getLogger().log(record.toString());
            context.getLogger().log("Receipt handle");
            context.getLogger().log(record.getReceiptHandle());
            context.getLogger().log("Message body:");
            context.getLogger().log(record.getBody());
            context.getLogger().log("Message attributes:");
            context.getLogger().log(record.getAttributes().toString());
        }
        return null;
    }

}
