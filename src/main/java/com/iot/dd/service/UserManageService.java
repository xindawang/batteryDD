package com.iot.dd.service;

import com.iot.dd.dao.mapper.UserManagementMapper;
import com.iot.dd.dao.entity.customer.CustomerEntity;
import com.iot.dd.dao.entity.worker.AdminEntity;
import com.iot.dd.dao.entity.worker.StaffEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */
@Service
public class UserManageService {
    @Autowired
    private UserManagementMapper usermanage;

    //查询所有信息
    public List<AdminEntity> findAdminall(){
        return usermanage.findAdminAll();
    }

    public List<StaffEntity> findStaffall(){

        return usermanage.findStaffAll();
    }

    public List<TechnicianEntity> findTechnicianall(){

        return usermanage.findTechnicianAll();
    }

    public List<CustomerEntity> findCustomerall(){
        return usermanage.findCustomerAll()
                ;
    }


    //查询一个用户信息

    public AdminEntity findAdminOne(String loginName){
        return usermanage.findAdminOne( loginName);
    }

    public StaffEntity findStaffOne(String loginName){

        return usermanage.findStaffOne( loginName);
    }

    public TechnicianEntity findTechnicianOne(String loginName){

        return usermanage.findTechnicianOne( loginName);
    }

    public CustomerEntity findCustomerOne(String loginName){
        return usermanage.findCustomerOne(loginName);
    }





   //删除信息
    public int deleteOneCustomer(String loginName){
        return usermanage.deleteCustomer(loginName);
   }
    public int deleteOneAdmin(String loginName){
        return usermanage.deleteAdmin(loginName);
    }
    public int deleteOneStaff(String loginName){
        return usermanage.deleteStaff(loginName);
    }
    public int deleteOneTechnician(String loginName){
        return usermanage.deleteTechnician(loginName);
    }





    //更新信息
   public String updateOneCustomer(CustomerEntity user){
        boolean b= usermanage.modifyCustomerInfo(user);
        if(b) {
            return "信息修改成功！";
        }
        else {
            return "信息修改失败！";
        }
   }
    public String updateOneStaff(StaffEntity user){
        boolean b=usermanage.modifyStaffInfo(user);
        if(b) {
            return "信息修改成功！";
        }
        else {
            return "信息修改失败！";
        }
    }
    public String updateOneAdmin(AdminEntity user) {
        boolean b=usermanage.modifyAdminInfo(user);
        if(b) {
            return "信息修改成功！";
        }
        else {
            return "信息修改失败！";
        }
    }

    public String updateOneTechnician(TechnicianEntity user) {
        boolean b=usermanage.modifyTechnician(user);
        if(b) {
            return "信息修改成功！";
        }
        else {
            return "信息修改失败！";
        }
    }

    /*
    * android
    * */
    //注册技师信息
    public String technicianLogin(TechnicianEntity user){
        boolean result=usermanage.technicianLogin(user);
        if(result) {
            return "技师注册成功！";
        }
        else {
            return "技师注册失败！";
        }
    }
  //更新技师位置信息
    public boolean updateTechnicianLocation(String technician,double longitude,double latitude){
        return usermanage.updateTechnicianLocation(technician,longitude,latitude);
    }
   public  boolean updateName(String technicianId,String name){
        return usermanage.updateName(technicianId,name);
   }

   public  boolean updateGender(String technicianId,String gender){
       return  usermanage.updateGender(technicianId,gender);
   }
   public  boolean updateCellphone(String technicianId,String cellphone){
       return usermanage.updateCellphone(technicianId,cellphone);
   }
   public boolean updateTelephone(String techcicianId,String telephone){
       return  usermanage.updateTelephone(techcicianId,telephone);
   }
   public boolean updateEmial(String technicianId,String email){
      return usermanage.updateEmail(technicianId,email);
   }

    public boolean updateIdNumber(String technicianId,String idNumber){
     return usermanage.updateIdNumber(technicianId,idNumber);
    }

    public  boolean updateCarNumber(String technicianId,String carNum){
        return usermanage.updateCarNumber(technicianId,carNum);
    }

    public  boolean updateAddress(String technicianId,String cityCode,String address){
        return usermanage.updateAddress(technicianId,cityCode,address);
    }
}
