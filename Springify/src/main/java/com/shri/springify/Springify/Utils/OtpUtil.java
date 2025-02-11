package com.shri.springify.Springify.Utils;

import java.util.Random;

public class OtpUtil {


    public  static String  generateOtp()
    {
        int otpLen=6;


        Random random=new Random();
StringBuilder otp=new StringBuilder();
        for(int i=0;i<otpLen;i++)
        {
            otp.append(random.nextInt(10));
        }
        return otp.toString();
    }
}
