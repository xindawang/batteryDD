package com.iot.dd.service;

import com.iot.dd.Dao.UserManagementDao;
import com.iot.dd.domain.customer.CustomerEntity;
import com.iot.dd.domain.worker.AdminEntity;
import com.iot.dd.domain.worker.StaffEntity;
import com.iot.dd.domain.worker.TechnicianEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */
@Service
public class UserManageService {
    @Autowired
    private UserManagementDao usermanage;

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
        return usermanage.deleteTchnician(loginName);
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
    public String updateOneTechnician(TechnicianEntity user){
        boolean b=usermanage.modifyTechnicianInfo(user);
        if(b) {
            return "信息修改成功！";
        }
        else {
            return "信息修改失败！";
        }
    }





}
