package com.fetch.receiptprocessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fetch.receiptprocessor.request.Receipt;
import com.fetch.receiptprocessor.response.GetPointsResponse;
import com.fetch.receiptprocessor.response.ReceiptProcessResponse;
import com.fetch.receiptprocessor.service.ReceiptService;

@SpringBootTest
class ReceiptProcessorApplicationTests {

	@Autowired
    private ReceiptService receiptService;

	@Test
    public void testReceipt1() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("test-receipt-1.json");
        Receipt receipt = objectMapper.readValue(resource.getFile(), Receipt.class);

        ReceiptProcessResponse receiptProcessResponse = receiptService.processReceipt(receipt);
        GetPointsResponse getPointsResponse = receiptService.getPoints(receiptProcessResponse.getId());

        assertEquals(28, getPointsResponse.getPoints());
    }

	@Test
    public void testReceipt2() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("test-receipt-2.json");
        Receipt receipt = objectMapper.readValue(resource.getFile(), Receipt.class);

        ReceiptProcessResponse receiptProcessResponse = receiptService.processReceipt(receipt);
        GetPointsResponse getPointsResponse = receiptService.getPoints(receiptProcessResponse.getId());

        assertEquals(109, getPointsResponse.getPoints());
    }

	@Test
    public void testReceipt3() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("test-receipt-3.json");
        Receipt receipt = objectMapper.readValue(resource.getFile(), Receipt.class);

        ReceiptProcessResponse receiptProcessResponse = receiptService.processReceipt(receipt);
        GetPointsResponse getPointsResponse = receiptService.getPoints(receiptProcessResponse.getId());

        assertEquals(15, getPointsResponse.getPoints());
    }

	@Test
    public void testReceipt4() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("test-receipt-4.json");
        Receipt receipt = objectMapper.readValue(resource.getFile(), Receipt.class);

        ReceiptProcessResponse receiptProcessResponse = receiptService.processReceipt(receipt);
        GetPointsResponse getPointsResponse = receiptService.getPoints(receiptProcessResponse.getId());

        assertEquals(31, getPointsResponse.getPoints());
    }
}
