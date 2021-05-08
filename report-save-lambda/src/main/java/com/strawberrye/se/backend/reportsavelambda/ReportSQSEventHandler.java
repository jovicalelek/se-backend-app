package com.strawberrye.se.backend.reportsavelambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.strawberrye.se.backend.reportsavelambda.dto.CoreSystemReport;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;
import software.amazon.awssdk.services.sqs.model.DeleteMessageResponse;

import java.util.List;

public class ReportSQSEventHandler implements RequestHandler<SQSEvent, Void> {

    private final ReportsTableClient reportsTableClient = new ReportsTableClient();
    private final ReportSQSClient reportSQSClient = new ReportSQSClient();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public Void handleRequest(SQSEvent sqsEvent, Context context) {
        try {
            LambdaLogger logger = context.getLogger();

            List<SQSEvent.SQSMessage> records = sqsEvent.getRecords();
            for (SQSEvent.SQSMessage record :
                    records) {
                logger.log("Message:\n" + record);
                logger.log("Receipt handle\n" + record.getReceiptHandle());
                logger.log("Message body:\n" + record.getBody());
                logger.log("Message attributes:\n" + record.getAttributes());

                PutItemResponse putItemResponse = reportsTableClient
                        .putItem(gson.fromJson(record.getBody(), CoreSystemReport.class));
                logger.log("Item saved in the table:\n" + putItemResponse);

                DeleteMessageResponse deleteMessageResponse = reportSQSClient.deleteMessage(record);
                logger.log("Item deleted fom the queue:\n" + deleteMessageResponse);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }

}
