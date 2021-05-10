package com.strawberrye.se.backend.aggregatorlambda;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.*;

import java.util.HashMap;
import java.util.Map;

public class ReportsDynamoDBTableClient {

    public Map<String, AttributeValue> getChargerAllSumTot(int deviceId) {
        DynamoDbClient client = DynamoDbClient.create();

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("PK", AttributeValue.builder().s("DEVICE#" + deviceId).build());
        key.put("SK", AttributeValue.builder().s("REPORT#CORE#CHARGER#ALL#SUM#TOT").build());

        GetItemResponse getItemResponse = client.getItem(GetItemRequest.builder()
                .tableName("dev-se-backend-reports")
                .key(key)
                .build());

        client.close();

        return getItemResponse.item();
    }

    public void updateChargerAllSumTot(int deviceId, int newValue) {
        DynamoDbClient client = DynamoDbClient.create();

        Map<String, AttributeValue> key = new HashMap<>();
        key.put("PK", AttributeValue.builder().s("DEVICE#" + deviceId).build());
        key.put("SK", AttributeValue.builder().s("REPORT#CORE#CHARGER#ALL#SUM#TOT").build());
        Map<String, AttributeValueUpdate> updatedValues = new HashMap<>();

        updatedValues.put("NumberOfUsers", AttributeValueUpdate.builder()
                .value(AttributeValue.builder().n(String.valueOf(newValue)).build())
                .action(AttributeAction.PUT).build());

        client.updateItem(UpdateItemRequest.builder()
                .tableName("dev-se-backend-reports")
                .key(key)
                .attributeUpdates(updatedValues)
                .build());

        client.close();
    }

}
