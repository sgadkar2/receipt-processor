package com.fetch.receiptprocessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fetch.receiptprocessor.request.Receipt;
import com.fetch.receiptprocessor.response.GetPointsResponse;
import com.fetch.receiptprocessor.response.ReceiptProcessResponse;
import com.fetch.receiptprocessor.service.ReceiptService;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    @Autowired
    private ReceiptService receiptService;

    @PostMapping("/process")
    public ResponseEntity<ReceiptProcessResponse> process(@RequestBody Receipt receipt) {

        ReceiptProcessResponse response = receiptService.processReceipt(receipt);

        if(response.getId() != null){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<GetPointsResponse> getPoints(@PathVariable String id) {

        GetPointsResponse response = receiptService.getPoints(id);

        if(response == null){
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
