package com.strawberrye.se.backend.aggregatorlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;

import java.util.List;

public class ReportDynamoDBEventHandler implements RequestHandler<DynamodbEvent, Void> {

    @Override
    public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {
        LambdaLogger logger = context.getLogger();
        List<DynamodbEvent.DynamodbStreamRecord> records = dynamodbEvent.getRecords();

        for (DynamodbEvent.DynamodbStreamRecord record :
                records) {
            logger.log("Record:\n" + record);
            logger.log("Record body:\n" + record.getDynamodb());
        }

        return null;
    }

}
