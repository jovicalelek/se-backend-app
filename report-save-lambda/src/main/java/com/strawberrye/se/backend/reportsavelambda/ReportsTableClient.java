package com.strawberrye.se.backend.reportsavelambda;

import com.strawberrye.se.backend.reportsavelambda.dto.CoreSystemReport;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.util.HashMap;
import java.util.Map;

public class ReportsTableClient {

    public void putItem(CoreSystemReport report) {
        DynamoDbClient client = DynamoDbClient.builder().build();

        Map<String, AttributeValue> itemValues = new HashMap<>();

        itemValues.put("PK", AttributeValue.builder().s("DEVICE#" + report.getDeviceId()).build());
        itemValues.put("SK", AttributeValue.builder().s("REPORT#CORE#RAW#" + report.getSendingTime()).build());
        itemValues.put("SoftwareVersion", AttributeValue.builder().s(String.valueOf(report.getSoftwareVersion())).build());
        itemValues.put("SystemMode", AttributeValue.builder().n(String.valueOf(report.getSystemMode())).build());


        client.putItem(PutItemRequest.builder()
                .tableName("dev-se-backend-reports")
                .item(itemValues)
                .build());
    }

}
