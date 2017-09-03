package com.iot.dd.Parameter;

/**
 * Created by admin on 2017/9/2.
 * Android上传图片，路径暂存
 */
public class pathOfImg {
    public static String batterIMG="";
    public static String carNumIMG="";
    public static String qualityIMG="";

    public static String orderID="";
    public static String technicianID="";

    public static String check(){
        if(batterIMG!=""&&carNumIMG!=""&&qualityIMG!=""){
            return "ok";
        }
        return "error";
    }




}
