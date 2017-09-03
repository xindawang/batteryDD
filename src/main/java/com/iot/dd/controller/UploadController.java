package com.iot.dd.controller;

import com.iot.dd.Parameter.pathOfImg;
import com.iot.dd.dao.entity.Indent.ConfirmEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.service.ConfirmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;

/**
 * Created by admin on 2017/9/2.
 */
@RestController
public class UploadController {
    @Value("${filePathPrefix}")
    private String filePathPrefix;

    @Autowired
    ConfirmService confirmService;

    @Autowired
    OrderMapper orderMapper;


    /*
   * 第一张照片
   * */
    @RequestMapping(value = "/androidComplete1", method = RequestMethod.POST)
    public String multipleSave(@RequestParam("img") MultipartFile file) {
        String fileName = null;
        if (file != null) {
            try {
                fileName = file.getOriginalFilename();//文件名包括后缀technicianId_orderID_XXX.jpg
                String[] ss = fileName.split("_");
                pathOfImg.technicianID = ss[0];
                pathOfImg.orderID = ss[1];
                pathOfImg.batterIMG = "/confirmImg/" + fileName;

                String path = filePathPrefix + fileName;

                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(path)));
                buffStream.write(bytes);
                buffStream.close();
            } catch (Exception e) {
                return "You     failed to upload" + fileName + ":" + e.getMessage();
            }
        } else {
            return "图片不存在！.";
        }

        return "ok";
    }

    /*
  * 第二张照片
  * */
    @RequestMapping(value = "/androidComplete2", method = RequestMethod.POST)
    public String multipleSave2(@RequestParam("img") MultipartFile file) {
        String fileName = null;
        if (file != null) {
            try {
                fileName = file.getOriginalFilename();//文件名包括后缀

                String[] ss = fileName.split("_");
                pathOfImg.technicianID = ss[0];
                pathOfImg.orderID = ss[1];
                pathOfImg.carNumIMG = "/confirmImg/" + fileName;

                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePathPrefix + fileName)));
                buffStream.write(bytes);
                buffStream.close();

            } catch (Exception e) {
                return "You     failed to upload" + fileName + ":" + e.getMessage();
            }
        } else {
            return "图片不存在！.";
        }
        return "ok";
    }

    /*
  * 第三张照片
  * */
    @RequestMapping(value = "/androidComplete3", method = RequestMethod.POST)
    public String multipleSave3(@RequestParam("img") MultipartFile file) {
        String fileName = null;
        if (file != null) {

            try {

                fileName = file.getOriginalFilename();//文件名包括后缀

                String[] ss = fileName.split("_");
                pathOfImg.technicianID = ss[0];
                pathOfImg.orderID = ss[1];
                pathOfImg.qualityIMG = "/confirmImg/" + fileName;

                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(new File(filePathPrefix + fileName)));
                buffStream.write(bytes);
                buffStream.close();


                ConfirmEntity entity3 = new ConfirmEntity();
                entity3.setOrderId(pathOfImg.orderID);
                entity3.setTechnicianId(pathOfImg.technicianID);
                entity3.setBatteryImg(pathOfImg.batterIMG);
                entity3.setLicensePlateNumberImg(pathOfImg.carNumIMG);
                entity3.setQualityAssuranceImg(pathOfImg.batterIMG);
                entity3.setTime(new Date());
                confirmService.addOneRecord(entity3);

                int statues = 4;//
                //修改indent中的statuew字段
                boolean t = orderMapper.updateStatues(pathOfImg.orderID, statues);
            } catch (Exception e) {
                return "You     failed to upload" + fileName + ":" + e.getMessage();
            }
        } else {
            return "图片不存在！.";
        }
        return "ok";
    }


}


