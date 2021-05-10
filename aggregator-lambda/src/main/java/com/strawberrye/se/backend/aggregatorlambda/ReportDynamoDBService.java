package com.strawberrye.se.backend.aggregatorlambda;

import com.amazonaws.services.lambda.runtime.events.models.dynamodb.AttributeValue;

import java.util.Map;

public class ReportDynamoDBService {

    private final ReportsDynamoDBTableClient reportsDynamoDBTableClient = new ReportsDynamoDBTableClient();

    public int getReportChargerAllNumberOfUsers(Map<String, AttributeValue> item) {
        item.get("DataGroups").getL().get(0).getM().get("ChargerReports").getL().get(0).getM().get("NumberOfUsers").getN();

        int result = 0;
        for (AttributeValue dataGroup :
                item.get("DataGroups").getL()) {
            for (AttributeValue chargerReport :
                    dataGroup.getM().get("ChargerReports").getL()) {
                result += Integer.parseInt(chargerReport.getM().get("NumberOfUsers").getN());
            }
        }
        return result;
    }

    public int updateChargerAllSumTot(int deviceId, int reportChargerAllNumberOfUsers) {
        Map<String, software.amazon.awssdk.services.dynamodb.model.AttributeValue> chargerAllSumTot =
                reportsDynamoDBTableClient.getChargerAllSumTot(deviceId);

        if (chargerAllSumTot.isEmpty()) {
            reportsDynamoDBTableClient.updateChargerAllSumTot(deviceId, reportChargerAllNumberOfUsers);
            return reportChargerAllNumberOfUsers;
        } else {
            int newValue = Integer.parseInt(chargerAllSumTot.get("NumberOfUsers").n()) + reportChargerAllNumberOfUsers;
            reportsDynamoDBTableClient.updateChargerAllSumTot(deviceId, newValue);
            return newValue;
        }
    }

}
