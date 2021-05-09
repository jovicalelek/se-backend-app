package com.strawberrye.se.backend.aggregatorlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strawberrye.se.backend.aggregatorlambda.dto.CoreSystemReport;

import java.util.List;
import java.util.Map;

public class ReportDynamoDBEventHandler implements RequestHandler<DynamodbEvent, Void> {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    @Override
    public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        List<DynamodbEvent.DynamodbStreamRecord> records = dynamodbEvent.getRecords();

        for (DynamodbEvent.DynamodbStreamRecord record :
                records) {
            logger.log("Record:\n" + record);
            logger.log("Record body:\n" + record.getDynamodb());
            logger.log("Keys:\n" + record.getDynamodb().getKeys());

            Map<String, AttributeValue> keys = record.getDynamodb().getKeys();

            logger.log("DeviceId: " + keys.get("PK").getS());
        }

        return null;
    }

}
