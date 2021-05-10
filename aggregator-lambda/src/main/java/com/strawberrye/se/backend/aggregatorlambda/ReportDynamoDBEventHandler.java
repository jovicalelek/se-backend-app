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

    private final ReportDynamoDBService reportDynamoDBService = new ReportDynamoDBService();

    @Override
    public Void handleRequest(DynamodbEvent dynamodbEvent, Context context) {
        try {
            LambdaLogger logger = context.getLogger();
            List<DynamodbEvent.DynamodbStreamRecord> records = dynamodbEvent.getRecords();

            for (DynamodbEvent.DynamodbStreamRecord record :
                    records) {
                logger.log("Record:\n" + record);
                logger.log("Record body:\n" + record.getDynamodb());
                logger.log("New image (item):\n" + record.getDynamodb().getNewImage());

                Map<String, AttributeValue> newImage = record.getDynamodb().getNewImage();

                String sk = newImage.get("SK").getS();
                if (sk.startsWith("REPORT#CORE#RAW#")) {
                    int reportChargerAllNumberOfUsers = reportDynamoDBService.getReportChargerAllNumberOfUsers(newImage);
                    logger.log("Total number of users: " + reportChargerAllNumberOfUsers);

                    int deviceId = Integer.parseInt(newImage.get("PK").getS().substring(7));
                    int chargerAllSumTot = reportDynamoDBService.updateChargerAllSumTot(deviceId, reportChargerAllNumberOfUsers);
                    logger.log("Total sum of users on all chargers: " + chargerAllSumTot);
                }
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

}
