package com.strawberrye.se.backend.reportsavelambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import java.util.List;

public class ReportSQSEventHandler implements RequestHandler<SQSEvent, Void> {

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        List<SQSEvent.SQSMessage> records = sqsEvent.getRecords();
        for (SQSEvent.SQSMessage message :
                records) {
            context.getLogger().log(message.getMessageId() + ": " + message.getBody());
        }
        return null;
    }

}
