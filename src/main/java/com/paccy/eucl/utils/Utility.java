package com.paccy.eucl.utils;

import java.util.Random;

public class Utility {

    public static String generateRandomToken(int length){
        String NUM="0123456789";
        StringBuilder token = new StringBuilder();

        Random random= new Random();


       for (int i=0;i<length;i++){
           int index= random.nextInt(NUM.length());
           token.append(NUM.charAt(index));
       }
       return token.toString();
    }
}
