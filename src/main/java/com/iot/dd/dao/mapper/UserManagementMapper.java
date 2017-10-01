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
            @Result(property = "technicianId", column = "technician_id"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "licensePlateNumber", column = "license_plate_number"),
            @Result(property = "organizationId", column = "organization_id")
    })
    List<TechnicianEntity> findTechnicianAll();

    @Select("select * from technician where login_name=#{loginName}")
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "technicianId", column = "technician_id"),
            @Result(property = "cityCode", column = "city_code"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "licensePlateNumber", column = "license_plate_number"),
            @Result(property = "organizationId", column = "organization_id"),
            @Result(property = "longitude", column = "technician_longitude"),
            @Result(property = "latitude", column = "technician_latitude"),
    })
    TechnicianEntity findTechnicianOne(String loginName) ;


    @Insert("insert into technician (LOGIN_NAME,PASSWORD,TECHNICIAN_ID,NAME,SEX,CELLPHONE,TELEPHONE,EMAIL," +
            "ID_NUMBER,ADDRESS,CITY_CODE,LICENSE_PLATE_NUMBER) values(#{loginName},#{password}," +
            "#{technicianId},#{name},#{sex},#{cellphone},#{telephone},#{email},#{idNumber},#{address},#{cityCode}," +
            "#{licensePlateNumber})")
    boolean addTechnician(TechnicianEntity user);



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
    int deleteTechnician(String loginName);

    @Update("update admin set password=#{password}, name=#{name},sex=#{sex} ,cellphone=#{cellphone},telephone=#{telephone},email=#{email},id_number=#{idNumber},address=#{address},role=#{role} where login_name=#{loginName}")
    boolean modifyAdminInfo(AdminEntity user);

    @Update("update staff set password=#{password}, name=#{name},sex=#{sex},cellphone=#{cellphone},telephone=#{telephone},email=#{email},id_number=#{idNumber},address=#{address},role=#{role}, organization_id=#{organizationId} where login_name=#{loginName} ")
    boolean modifyStaffInfo(StaffEntity user);

    @Update("update customer set password=#{password}, name=#{name},sex=#{sex},cellphone=#{cellphone},telephone=#{telephone},email=#{email},address=#{address},postcode=#{postcode} where login_name=#{loginName}")
    boolean modifyCustomerInfo(CustomerEntity user);


    @Update("update technician set password=#{password}," +
            "name=#{name},sex=#{sex},cellphone=#{cellphone},telephone=#{telephone},email=#{email}," +
            "id_number=#{idNumber},address=#{address},license_plate_number=#{licensePlateNumber}," +
            "city_code=#{cityCode} where technician_id=#{technicianId}")
    boolean modifyTechnician(TechnicianEntity user);

    /*
    *
    * android端
    * */
    //技师注册
    @Insert("insert into technician (login_name,technician_id,password,name,sex,telephone,email,address,city_code,id_number,license_plate_number) values(#{loginName},#{technicianId},#{password},#{name},#{sex},#{telephone},#{email},#{address},#{cityCode},#{idNumber},#{licensePlateNumber})")
    boolean technicianLogin(TechnicianEntity user);

    //更新技师经纬度
    @Update("update technician set technician_longitude=#{technicianLongitude},technician_latitude=#{technicianLatitude} where technician_id=#{technicianId}")
    boolean updateTechnicianLocation(@Param("technicianId")String technicianId,@Param("technicianLongitude")double technicianLongitude,@Param("technicianLatitude")double technicianLatitude);


    @Select("select * from technician where city_code=#{cityCode}")
    @Results({
            @Result(property = "loginName", column = "login_name"),
            @Result(property = "technicianId", column = "technician_id"),
            @Result(property = "idNumber", column = "id_number"),
            @Result(property = "licensePlateNumber", column = "license_plate_number"),
            @Result(property = "organizationId", column = "organization_id"),
            @Result(property="cityCode", column="city_code")
    })
    TechnicianEntity selectTechMsgFromCity(String cityCode);


    @Update("update technician set name=#{name} where technician_id=#{technicianId}")
    boolean updateName(@Param("technicianId") String technicianId,@Param("name") String name);

    @Update("update technician set sex=#{gender} where technician_id=#{technicianId}")
    boolean updateGender(@Param("technicianId") String technicianId,@Param("gender") String gender);

    @Update("update technician set cellphone=#{cellphone} where technician_id=#{technicianId}")
    boolean updateCellphone(@Param("technicianId") String technicianId,@Param("cellphone") String cellphone);

    @Update("update technician set telephone=#{telephone} where technician_id=#{technicianId}")
    boolean updateTelephone(@Param("technicianId") String technicianId,@Param("telephone") String telephone);

    @Update("update technician set email=#{email} where technician_id=#{technicianId}")
    boolean updateEmail(@Param("technicianId") String technicianId,@Param("email") String email);

    @Update("update technician set id_number=#{idNumber} where technician_id=#{technicianId}")
    boolean updateIdNumber(@Param("technicianId") String technicianId,@Param("idNumber") String idNumber);

    @Update("update technician set license_plate_number=#{carNumber} where technician_id=#{technicianId}")
    boolean updateCarNumber(@Param("technicianId") String technicianId,@Param("carNumber") String carNumber);

    @Update("update technician set address=#{address},city_code=#{cityCode} where technician_id=#{technicianId}")
    boolean updateAddress(@Param("technicianId") String technicianId,@Param("cityCode") String cityCode,@Param("address") String  address);

}
