package com.iot.dd.controller;

import com.iot.dd.Parameter.pathOfImg;
import com.iot.dd.dao.entity.Indent.ConfirmEntity;
import com.iot.dd.dao.mapper.OrderMapper;
import com.iot.dd.service.ConfirmService;
import com.iot.dd.service.OrderService;
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
    OrderService orderService;


    @RequestMapping(value = "/androidComplete4", method = RequestMethod.POST)
    public String multipleSaveAll(@RequestParam("img") MultipartFile[] files, @RequestParam("technicianId") String technicianId,
                                  @RequestParam("orderId") String orderId) {

        String batterIMG = filePathPrefix + technicianId + "_" + orderId + "_battery.jpg";
        String carNumIMG = filePathPrefix + technicianId + "_" + orderId + "_CarNum.jpg";
        String qualityIMG = filePathPrefix + technicianId + "_" + orderId + "_quality.jpg";
        String path = "";
        MultipartFile file = null;
        for (int i = 0; i <= 2; i++) {
            file = files[i];
            if (file != null) {
                try {
                    if (i == 0) {
                        path = batterIMG;
                    } else if (i == 1) {
                        path = carNumIMG;
                    } else {
                        path = qualityIMG;
                    }
                    File file2 = new File(path);
                    byte[] bytes = file.getBytes();
                    BufferedOutputStream buffStream =
                            new BufferedOutputStream(new FileOutputStream(file2));
                    buffStream.write(bytes);
                    buffStream.close();
                } catch (Exception e) {
                    return "上传失败！！";
                }
            } else {
                return "图片不存在！.";
            }
        }
        ConfirmEntity entity = new ConfirmEntity();
        entity.setOrderId(orderId);
        entity.setTechnicianId(technicianId);
        entity.setQualityAssuranceImg(qualityIMG);
        entity.setBatteryImg(batterIMG);
        entity.setLicensePlateNumberImg(carNumIMG);
        entity.setTime(new Date());

        List<ConfirmEntity> list0 = confirmService.find(orderId, technicianId);
        if (list0.size()>0) {
            confirmService.updateQualityAllPath(entity);
        } else {
            confirmService.addOneRecord(entity);
        }
        orderService.setFinishTime(orderId, new Date());
        int statues =4;
        orderService.updateStatues(orderId, statues);

        return "ok";
    }
}


