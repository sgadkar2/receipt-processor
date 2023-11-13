package com.fetch.receiptprocessor.service;

import com.fetch.receiptprocessor.request.Receipt;
import com.fetch.receiptprocessor.response.GetPointsResponse;
import com.fetch.receiptprocessor.response.ReceiptProcessResponse;

public interface ReceiptService {
    
    ReceiptProcessResponse processReceipt(Receipt receipt);

    GetPointsResponse getPoints(String id);

}
