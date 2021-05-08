package com.strawberrye.se.backend.reportsavelambda;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.DeleteMessageResponse;

public class ReportSQSClient {
    private static final Logger logger = LoggerFactory.getLogger(ReportsTableClient.class);

    public DeleteMessageResponse deleteMessage(SQSEvent.SQSMessage record) {
        SqsClient sqsClient = SqsClient.builder().build();

        DeleteMessageResponse deleteMessageResponse = sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl("https://sqs.eu-west-2.amazonaws.com/712524022545/dev-se-backend-report.fifo")
                .receiptHandle(record.getReceiptHandle())
                .build());

        sqsClient.close();

        return deleteMessageResponse;
    }

}
