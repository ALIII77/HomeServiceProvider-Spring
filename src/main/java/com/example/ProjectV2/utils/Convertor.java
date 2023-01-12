package com.example.ProjectV2.utils;

import com.example.ProjectV2.entity.enums.OrderStatus;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;

import java.time.LocalDateTime;

public class Convertor {


    public static Long toLong(String stringId){
        try {
            return Long.valueOf(stringId);
        }catch (Exception e){
            throw new CustomizedIllegalArgumentException(" cannot be convert value of predicate to 'Long' id! ");
        }
    }




    public static LocalDateTime toLocalDateTime(String localDateTime){
        try {
            return LocalDateTime.parse(localDateTime);
        }catch (Exception e){
            throw new CustomizedIllegalArgumentException(" cannot be  convert value of predicate to 'LocalDateTime' date! ");
        }
    }



    public static OrderStatus toOrderStatusValue(String orderStatus){
        try {
            return OrderStatus.valueOf(orderStatus);
        }catch (Exception e){
            throw new CustomizedIllegalArgumentException(" cannot be  convert value of predicate to 'OrderStatus' string value! ");
        }
    }


}
