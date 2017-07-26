package com.iot.dd.dao.mapper;

import com.iot.dd.dao.entity.customer.CustomerEntity;
import com.iot.dd.dao.entity.worker.AdminEntity;
import com.iot.dd.dao.entity.worker.StaffEntity;
import com.iot.dd.dao.entity.worker.TechnicianEntity;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */
@Repository
public interface UserManagementMapper {

    //管理员
    @Select("select * from admin")
    @Results({
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "loginName", column = "login_name")
    })
    List<AdminEntity> findAdminAll() ;

    @Select("select * from admin where login_name=#{loginName}")
    @Results({
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "loginName", column = "login_name")
    })
    AdminEntity findAdminOne(String loginName) ;

    //客服
    @Select("select * from staff")
    @Results({
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "organizationId", column = "organization_id")
    })
    List<StaffEntity> findStaffAll() ;


    @Select("select * from staff where login_name=#{loginName}")
    @Results({
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "organizationId", column = "organization_id")
    })
    StaffEntity findStaffOne(String loginName) ;


    //技师
    @Select("select * from technician")
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "licensePlateNumber", column = "license_plate_number"),
            @Result(property = "organizationId", column = "organization_id")
    })
    List<TechnicianEntity> findTechnicianAll() ;

    @Select("select * from technician where login_name=#{loginName}")
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "licensePlateNumber", column = "license_plate_number"),
            @Result(property = "organizationId", column = "organization_id")
    })
    TechnicianEntity findTechnicianOne(String loginName) ;



    //客户
    @Select("select * from customer")
    @Results({
            @Result(property = "loginName", column = "login_name")
    })
    List<CustomerEntity> findCustomerAll() ;

    //客户
    @Select("select * from customer where login_name=#{loginName}")
    @Results({
            @Result(property = "loginName", column = "login_name")
    })
    CustomerEntity findCustomerOne(String loginName) ;



   //删除

    @Delete("delete from customer where login_name=#{loginName}")
    int deleteCustomer(String loginName);

    @Delete("delete from admin where login_name=#{loginName}")
    int deleteAdmin(String loginName);

    @Delete("delete from staff where login_name=#{loginName}")
    int deleteStaff(String loginName);

    @Delete("delete from technician where login_name=#{loginName}")
    int deleteTchnician(String loginName);

    @Update("update admin set password=#{password}, name=#{name} ,cellphone=#{cellphone},telephone=#{telephone},email=#{email},id_number=#{idNumber},address=#{address},role=#{role} where login_name=#{loginName}")
    boolean modifyAdminInfo(AdminEntity user);

    @Update("update staff set password=#{password}, name=#{name},sex=#{sex},cellphone=#{cellphone},telephone=#{telephone},email=#{email},id_number=#{idNumber},address=#{address},role=#{role}, organization_id=#{organizationId} where login_name=#{loginName} ")
    boolean modifyStaffInfo(StaffEntity user);

    @Update("update customer set password=#{password}, name=#{name},sex=#{sex},cellphone=#{cellphone},telephone=#{telephone},email=#{email},address=#{address},postcode=#{postcode} where login_name=#{loginName}")
    boolean modifyCustomerInfo(CustomerEntity user);

    @Update("update technician set technician_id={technicianId} password=#{password}, name=#{name},sex=#{sex},cellphone=#{cellphone},telephone=#{telephone},email=#{email},address=#{address},id_number=#{idNumber}, organization_id=#{organizationId} ,license_plate_number=#{licensePlateNumber}  where login_name=#{loginName}")
    boolean modifyTechnicianInfo(TechnicianEntity user);


    @Update("update technnician set technician_longitude=#{technicianLongitude},technician_latitude=#{technicianLatitude} where technician_id=#{technicianId}")
    boolean updateTechnicianAddress(String technicianId,Float technicianLongitude,Float technicianLatitude);


}
