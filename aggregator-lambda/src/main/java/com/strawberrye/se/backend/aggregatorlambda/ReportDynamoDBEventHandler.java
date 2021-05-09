package com.strawberrye.se.backend.aggregatorlambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;
import com.google.gson.JsonSyntaxException;

import java.util.List;
import java.util.Map;

public class ReportDynamoDBEventHandler implements RequestHandler<DynamodbEvent, Void> {

    @Override
    public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {
        try {
            LambdaLogger logger = context.getLogger();
            List<DynamodbEvent.DynamodbStreamRecord> records = dynamodbEvent.getRecords();

            for (DynamodbEvent.DynamodbStreamRecord record :
                    records) {
                logger.log("Record:\n" + record);
                logger.log("Record body:\n" + record.getDynamodb());
                logger.log("New image:\n" + record.getDynamodb().getNewImage());

                Map<String, AttributeValue> item = record.getDynamodb().getNewImage();

                int totNumOfUsers = getTotNumOfUsers(item);

                logger.log("Total number of users: " + totNumOfUsers);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int getTotNumOfUsers(Map<String, AttributeValue> item) {
        item.get("DataGroups").getL().get(0).getM().get("ChargerReports").getL().get(0).getM().get("NumberOfUsers").getN();

        int totNumOfUsers = 0;
        for (AttributeValue dataGroup :
                item.get("DataGroups").getL()) {
            for (AttributeValue chargerReport:
                 dataGroup.getM().get("ChargerReports").getL()) {
                totNumOfUsers += Integer.parseInt(chargerReport.getM().get("NumberOfUsers").getN());
            }
        }
        return totNumOfUsers;
    }

}
