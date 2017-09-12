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
import java.util.List;

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
                String technicianID = ss[0];
                String orderID = ss[1];
                String batterIMG=filePathPrefix+fileName;

                File file0=new File(filePathPrefix + fileName);
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(file0));
                buffStream.write(bytes);
                buffStream.close();

                List<ConfirmEntity> list0=confirmService.find(orderID,technicianID );
                if(list0.size()>0){
                    confirmService.updateBatteryPath(batterIMG);
                }
                else{
                    ConfirmEntity entity1=new ConfirmEntity();
                    entity1.setOrderId(orderID);
                    entity1.setTechnicianId(technicianID);
                    entity1.setBatteryImg(batterIMG);
                    entity1.setTime(new Date());
                    confirmService.addOneBatteryIMG(entity1);
                }
            } catch (Exception e) {
                return "上传失败！！";
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
                String technicianID = ss[0];
                String orderID = ss[1];
                String carNumIMG = filePathPrefix + fileName;

                File file1=new File(filePathPrefix + fileName);

                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(file1));
                buffStream.write(bytes);
                buffStream.close();

                List<ConfirmEntity> list1=confirmService.find(orderID,technicianID );
                if(list1!=null){
                    confirmService.updateCarNumPath(carNumIMG);
                }
                else{
                    ConfirmEntity entity=new ConfirmEntity();
                    entity.setOrderId(orderID);
                    entity.setTechnicianId(technicianID);
                    entity.setLicensePlateNumberImg(carNumIMG);
                    entity.setTime(new Date());
                    confirmService.addOneCarNumIMG(entity);
                }



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
                String technicianID = ss[0];
                String orderID = ss[1];
                String qualityIMG = filePathPrefix + fileName;

                File file2=new File(filePathPrefix + fileName);
                byte[] bytes = file.getBytes();
                BufferedOutputStream buffStream =
                        new BufferedOutputStream(new FileOutputStream(file2));
                buffStream.write(bytes);
                buffStream.close();

                List<ConfirmEntity> list2=confirmService.find(orderID,technicianID );
                if(list2.size()>0){
                    confirmService.updateQualityPath(qualityIMG);
                }
                else{
                    ConfirmEntity entity=new ConfirmEntity();
                    entity.setOrderId(orderID);
                    entity.setTechnicianId(technicianID);
                    entity.setQualityAssuranceImg(qualityIMG);
                    entity.setTime(new Date());
                    confirmService.addOneQualityIMG(entity);
                }
                int statues = 4;
                //修改indent中的statuew字段
                boolean t = orderMapper.updateStatues(orderID, statues);
            } catch (Exception e) {
                return "上传失败！！";
            }
        } else {
            return "图片不存在！.";
        }
        return "ok";
    }


}


