package com.gxd.crm.utils;

import com.gxd.crm.exceptions.ParamsException;


public class AssertUtil {


    public  static void isTrue(Boolean flag,String msg){
        if(flag){
            throw  new ParamsException(msg);
        }
    }

}
