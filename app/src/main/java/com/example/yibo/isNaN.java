package com.example.yibo;

public class isNaN {

    public static boolean isInteger(String str){
        try{
            Integer.parseInt(str);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }
    public static boolean isDouble(String str){
        try{
            Double.parseDouble(str);
        }catch(NumberFormatException e){
            return false;
        }
        return true;
    }

}
