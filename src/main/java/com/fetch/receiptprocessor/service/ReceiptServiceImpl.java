package com.fetch.receiptprocessor.service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fetch.receiptprocessor.entity.PointsEntity;
import com.fetch.receiptprocessor.repository.PointsRepository;
import com.fetch.receiptprocessor.request.Receipt;
import com.fetch.receiptprocessor.request.Item;
import com.fetch.receiptprocessor.response.GetPointsResponse;
import com.fetch.receiptprocessor.response.ReceiptProcessResponse;
import com.fetch.receiptprocessor.configuration.Constants;

import io.micrometer.common.util.StringUtils;

@Service
public class ReceiptServiceImpl implements ReceiptService{

    @Autowired
    private PointsRepository pointsRepository;

    private static final Logger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);

    @Override
    public ReceiptProcessResponse processReceipt(Receipt receipt) {
        
        ReceiptProcessResponse response = new ReceiptProcessResponse();

        try{
            PointsEntity pointsEntity = new PointsEntity();

            String id = UUID.randomUUID().toString();
            pointsEntity.setId(id);
            pointsEntity.setPoints(calculateTotalPoints(receipt));

            pointsEntity = pointsRepository.save(pointsEntity);

            response.setId(id);
        }catch(Exception ex){
            logger.error("Error occured while processing the receipt: ", ex.getMessage());
        }

        return response;
    }

    private int calculateTotalPoints(Receipt receipt){

        int points = 0;

        points = countAlphanumericCharacters(receipt.getRetailer()) + checkIfRound(receipt.getTotal())
                  + checkIfMultiple(receipt.getTotal()) + checkIf2Items(receipt.getItems())
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
                return Constants.POINTS_TOTAL_IS_WHOLE_DOLLAR;
            }
        }

        return 0;
    }

    private int checkIfMultiple(String total){

        if(StringUtils.isNotBlank(total)){
            double totalAmount = Double.parseDouble(total);

            if(totalAmount % 0.25 == 0){
                return Constants.POINTS_TOTAL_IS_MULTIPLE_OF_QUARTERS;
            }
        }
        return 0; 
    }

    private int checkIf2Items(List<Item> items){

        if(items == null){
            return 0;
        }

        return (items.size() / 2) * Constants.POINTS_EVERY_PAIR_OF_ITEMS;
    }

    private int checkItemDescription(List<Item> items){

        int price = 0;

        if(items != null){
            for(Item item : items){
                if(item.getShortDescription().trim().length() % 3 == 0){
                    price += Math.ceil(Double.parseDouble(item.getPrice()) * Constants.POINTS_MULTIPLIER_ITEM_LENGTH_IS_MULTIPLE_OF_THREE);
                }
            }
        }
        

        return price;
    }

    private int checkPurchaseDay(String purchaseDate){

        if(StringUtils.isNotBlank(purchaseDate)){
            int purchaseDay = Integer.parseInt(purchaseDate.split("-")[2]);

            if(purchaseDay % 2 == 1){
                return Constants.POINTS_PURCHASE_DATE_IS_ODD;
            }
        }

        return 0; 
    }

    private int purchaseTime(String purchaseTime){

        if(StringUtils.isNotBlank(purchaseTime)){

            LocalTime formattedPurchaseTime = LocalTime.parse(purchaseTime);
            LocalTime startTime = LocalTime.parse(Constants.PURCHASE_TIME_INTERVAL_START);
            LocalTime endTime = LocalTime.parse(Constants.PURCHASE_TIME_INTERVAL_END);

            if(formattedPurchaseTime.isAfter(startTime) && formattedPurchaseTime.isBefore(endTime)){
                return Constants.POINTS_PURCHASE_TIME_IS_WITHIN_INTERVAL;
            }
        }

        return 0;
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
            logger.error("Error occured while fetching points for receipt: ", ex.getMessage());
            return null;
        }
       

        return response;
    }
    
}
