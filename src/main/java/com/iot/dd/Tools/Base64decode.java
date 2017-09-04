package com.iot.dd.Tools;


import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

/**
 * Created by admin on 2017/8/28.
 */
public class Base64decode {

    public boolean string2Image(String imgStr, String imgFilePath) {
        //  对字节数组字符串进行Base64解码并生成图片    
        if (imgStr == null)
            return false;
        try {
            //  Base64解码
//            byte[] b = new BASE64Decoder().decodeBuffer(imgStr);
            byte[] b= Base64.getDecoder().decode(imgStr);;
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
