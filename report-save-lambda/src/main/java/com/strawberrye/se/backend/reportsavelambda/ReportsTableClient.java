package com.strawberrye.se.backend.reportsavelambda;

import com.strawberrye.se.backend.reportsavelambda.dto.ChargerReport;
import com.strawberrye.se.backend.reportsavelambda.dto.CoreSystemReport;
import com.strawberrye.se.backend.reportsavelambda.dto.DataGroup;
import com.strawberrye.se.backend.reportsavelambda.dto.FuseReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.PutItemResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportsTableClient {

    private static final Logger logger = LoggerFactory.getLogger(ReportsTableClient.class);

    public PutItemResponse putItem(CoreSystemReport report) {
        DynamoDbClient client = DynamoDbClient.builder().build();

        Map<String, AttributeValue> coreReportValues = new HashMap<>();

        coreReportValues.put("PK", AttributeValue.builder().s("DEVICE#" + report.getDeviceId()).build());
        coreReportValues.put("SK", AttributeValue.builder().s("REPORT#CORE#RAW#" + report.getSendingTime()).build());
        coreReportValues.put("SoftwareVersion", AttributeValue.builder().s(String.valueOf(report.getSoftwareVersion())).build());
        coreReportValues.put("SystemMode", AttributeValue.builder().n(String.valueOf(report.getSystemMode())).build());

        putDataGroups(report, coreReportValues);

        PutItemResponse putItemResponse = client.putItem(PutItemRequest.builder()
                .tableName("dev-se-backend-reports")
                .item(coreReportValues)
                .build());

        client.close();

        return putItemResponse;
    }

    private void putDataGroups(CoreSystemReport report, Map<String, AttributeValue> coreReportValues) {
        List<AttributeValue> dataGroups = new ArrayList<>();

        for (DataGroup dataGroup :
                report.getDataGroups()) {
            Map<String, AttributeValue> dataGroupValues = new HashMap<>();

            dataGroupValues.put("StartTime", AttributeValue.builder().s(dataGroup.getStartTime().toString()).build());
            dataGroupValues.put("EndTime", AttributeValue.builder().s(dataGroup.getEndTime().toString()).build());

            dataGroupValues.put("BatteryMode", AttributeValue.builder().n(String.valueOf(dataGroup.getBatteryMode())).build());
            dataGroupValues.put("SystemMode", AttributeValue.builder().n(String.valueOf(dataGroup.getSystemMode())).build());

            dataGroupValues.put("BatteryVoltageMean", AttributeValue.builder().n(String.valueOf(dataGroup.getBatteryVoltageMean())).build());
            dataGroupValues.put("BatteryVoltageMin", AttributeValue.builder().n(String.valueOf(dataGroup.getBatteryVoltageMin())).build());
            dataGroupValues.put("BatteryVoltageMax", AttributeValue.builder().n(String.valueOf(dataGroup.getBatteryVoltageMax())).build());

            dataGroupValues.put("TemperatureMean", AttributeValue.builder().n(String.valueOf(dataGroup.getTemperatureMean())).build());
            dataGroupValues.put("TemperatureMin", AttributeValue.builder().n(String.valueOf(dataGroup.getTemperatureMin())).build());
            dataGroupValues.put("TemperatureMax", AttributeValue.builder().n(String.valueOf(dataGroup.getTemperatureMax())).build());

            putChargerReports(dataGroup, dataGroupValues);
            putFuseReports(dataGroup, dataGroupValues);

            dataGroups.add(AttributeValue.builder().m(dataGroupValues).build());
        }
        coreReportValues.put("DataGroups", AttributeValue.builder().l(dataGroups).build());
    }

    private void putChargerReports(DataGroup dataGroup, Map<String, AttributeValue> dataGroupValues) {
        List<AttributeValue> chargerReports = new ArrayList<>();

        for (ChargerReport chargerReport :
                dataGroup.getChargerReports()) {
            Map<String, AttributeValue> chargerReportValues = new HashMap<>();

            chargerReportValues.put("ChargerId", AttributeValue.builder()
                    .n(String.valueOf(chargerReport.getChargerId())).build());
            chargerReportValues.put("NumberOfUsers", AttributeValue.builder()
                    .n(String.valueOf(chargerReport.getNumberOfUsers())).build());

            chargerReports.add(AttributeValue.builder().m(chargerReportValues).build());
        }
        dataGroupValues.put("ChargerReports", AttributeValue.builder().l(chargerReports).build());
    }

    private void putFuseReports(DataGroup dataGroup, Map<String, AttributeValue> dataGroupValues) {
        List<AttributeValue> fuseReports = new ArrayList<>();

        for (FuseReport fuseReport :
                dataGroup.getFuseReports()) {
            Map<String, AttributeValue> fuseReportValues = new HashMap<>();

            fuseReportValues.put("FuseId", AttributeValue.builder().n(String.valueOf(fuseReport.getFuseId())).build());
            fuseReportValues.put("FuseBlown", AttributeValue.builder().bool(fuseReport.isFuseBlown()).build());

            fuseReports.add(AttributeValue.builder().m(fuseReportValues).build());
        }
        dataGroupValues.put("FuseReports", AttributeValue.builder().l(fuseReports).build());
    }

}
