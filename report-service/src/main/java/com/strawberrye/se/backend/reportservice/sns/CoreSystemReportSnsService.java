package com.strawberrye.se.backend.reportservice.sns;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strawberrye.se.backend.reportservice.dto.CoreSystemReport;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
@Slf4j
public class CoreSystemReportSnsService {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    public boolean publishCoreSystemReport(CoreSystemReport report) {
        SnsClient client = SnsClient.builder().build();
        PublishResponse response = client.publish(PublishRequest.builder()
                .message(gson.toJson(report))
                .messageGroupId("CoreSystemReport")
                .topicArn("arn:aws:sns:eu-west-2:712524022545:dev-se-backend-report.fifo")
                .build());
        client.close();
        return response.sdkHttpResponse().isSuccessful();
    }

}
