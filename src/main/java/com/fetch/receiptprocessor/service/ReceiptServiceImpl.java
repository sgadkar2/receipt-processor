package com.fetch.receiptprocessor.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fetch.receiptprocessor.entity.PointsEntity;
import com.fetch.receiptprocessor.repository.PointsRepository;
import com.fetch.receiptprocessor.request.Receipt;
import com.fetch.receiptprocessor.request.Item;
import com.fetch.receiptprocessor.response.GetPointsResponse;
import com.fetch.receiptprocessor.response.ReceiptProcessResponse;

import io.micrometer.common.util.StringUtils;

@Service
public class ReceiptServiceImpl implements ReceiptService{

    @Autowired
    private PointsRepository pointsRepository;

    @Override
    public ReceiptProcessResponse processReceipt(Receipt receipt) {
        
        ReceiptProcessResponse response = new ReceiptProcessResponse();

        try{
            PointsEntity pointsEntity = new PointsEntity();

            String id = UUID.randomUUID().toString();
            pointsEntity.setId(id);
            pointsEntity.setPoints(getPoints(receipt));

            pointsEntity = pointsRepository.save(pointsEntity);

            response.setId(id);
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }

        return response;
    }

    private int getPoints(Receipt receipt){

        int points = 0;

        points = countAlphanumericCharacters(receipt.getRetailer()) + checkIfRound(receipt.getTotal())
                  + checkIfMultiple(receipt.getTotal()) + checkIf2Items(receipt.getItems() != null ? receipt.getItems().size() : 0)
                  + checkItemDescription(receipt.getItems()) + checkPurchaseDay(receipt.getPurchaseDate())
                  + purchaseTime(receipt.getPurchaseTime());
        return points;
    }

    private int countAlphanumericCharacters(String retailer){

        int count = 0;

        if(StringUtils.isNotBlank(retailer)){
            for (char c : retailer.toCharArray()) {
                if (Character.isLetterOrDigit(c)) {
                    count++;
                }
            }
        }
        
        return count;
    }

    private int checkIfRound(String total){

        if(StringUtils.isNotBlank(total)){
            double totalAmount = Double.parseDouble(total);

            if(totalAmount == Math.floor(totalAmount)){
                return 50;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
        

    }

    private int checkIfMultiple(String total){

        if(StringUtils.isNotBlank(total)){
            double totalAmount = Double.parseDouble(total);

            if(totalAmount % 0.25 == 0){
                return 25;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
        
    }

    private int checkIf2Items(int itemLength){

        return (itemLength / 2) * 5;
    }

    private int checkItemDescription(List<Item> items){

        int price = 0;

        if(items != null){
            for(Item item : items){
                if(item.getShortDescription().trim().length() % 3 == 0){
                    price += Math.ceil(Double.parseDouble(item.getPrice()) * 0.2);
                }
            }
        }
        

        return price;
    }

    private int checkPurchaseDay(String purchaseDate){

        if(StringUtils.isNotBlank(purchaseDate)){
            int purchaseDay = Integer.parseInt(purchaseDate.split("-")[2]);

            if(purchaseDay % 2 == 0){
                return 0;
            }else{
                return 6;
            }
        }else{
            return 0;
        }
        
    }

    private int purchaseTime(String purchaseTime){

        if(StringUtils.isNotBlank(purchaseTime)){
            String format = "HH:mm";

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            LocalTime formattedPurchaseTime = LocalTime.parse(purchaseTime, formatter);

            LocalTime startTime = LocalTime.of(14, 0);
            LocalTime endTime = LocalTime.of(16, 0);

            if(formattedPurchaseTime.isAfter(startTime) && formattedPurchaseTime.isBefore(endTime)){
                return 10;
            }else{
                return 0;
            }
        }else{
            return 0;
        }
        
    }

    @Override
    public GetPointsResponse getPoints(String id) {
        
        GetPointsResponse response = new GetPointsResponse();

        try{
             Optional<PointsEntity> pointsEntity = pointsRepository.findById(id);

            if(pointsEntity.isPresent()){
                response.setPoints(pointsEntity.get().getPoints());
            }else{
                return null;
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
       

        return response;
    }
    
}
