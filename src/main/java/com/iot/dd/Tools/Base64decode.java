package com.iot.dd.Tools;

import sun.misc.BASE64Decoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

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
            byte[] b = new BASE64Decoder().decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    //  调整异常数据
                    b[i] += 256;
                }
            }
            //  生成Jpeg图片
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
