package com.iot.dd.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by SunYi on 2016/2/1/0001.
 */
@Configuration
//扫描实体类的包
@ComponentScan(basePackages = "com.iot.dd.dao.entity")
//扫描仓库类的包，在mybatis里面被称为Mapper，一般用来完成数据库的操作
@MapperScan(basePackages = "com.iot.dd.dao")
public class DataBaseConfig {


    @Bean
    public DataSource getDataSource() {

        Connection connection = null;

        BasicDataSource dataSource = new BasicDataSource();
        // 数据库连接配置
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        //设置useSSL=false
        dataSource.setUrl("jdbc:mysql://rm-uf6jhajapzy4cza3qo.mysql.rds.aliyuncs.com/batterydd?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("huangyan71!!");
        dataSource.setTestWhileIdle(true);
        dataSource.setTimeBetweenEvictionRunsMillis(60000);
        dataSource.setValidationQuery("select 1");


//        String initialSize = "10";//初始化连接
//        String minIdle = "5";
//        String maxIdle = "20";
//        String maxWait = "600";
//        String maxActive = "50";
//        String timeBetweenEvictionRunsMillis = "3";
//        String minEvictableIdleTimeMillis = "12";
//
//        //取得连接时是否进行有效性验证（即是否还和数据库连通的）
//        boolean testOnBorrow = true;
//        //返回连接时是否进行有效性验证（即是否还和数据库连通的）
//        boolean testOnReturn = true;
//        //连接空闲时是否进行有效性验证（即是否还和数据库连通的）
//        boolean testWhileIdle = true;
//        //是否在自动回收超时连接的时候打印连接的超时错误
//        boolean logAbandoned = true;
//        //是否自动回收超时连接
//        boolean removeAbandoned = true;
//        //超时时间(以秒数为单位)
//        int removeAbandonedTimeout = 30;
//        //初始化连接数
//        if (StringUtils.isNotBlank(initialSize)) {
//            dataSource.setInitialSize(Integer.parseInt(initialSize));
//        }
//        //最小空闲连接数
//        if (StringUtils.isNotBlank(minIdle)) {
//            dataSource.setMinIdle(Integer.parseInt(minIdle));
//        }
//        //最大空闲连接数
//        if (StringUtils.isNotBlank(maxIdle)) {
//            dataSource.setMinIdle(Integer.parseInt(maxIdle));
//        }
//        //超时回收时间（以毫秒为单位）
//        if (StringUtils.isNotBlank(maxWait)) {
//            dataSource.setMinIdle(Integer.parseInt(minIdle));
//        }
//        //最大连接数
//        if (StringUtils.isNotBlank(maxActive)) {
//            dataSource.setMinIdle(Integer.parseInt(minIdle));
//        }
//
//
//        //是否在自动回收超时连接的时候打印连接的超时错误
//        dataSource.setLogAbandoned(logAbandoned);
//        //是否自动回收超时连接
//        dataSource.setRemoveAbandoned(removeAbandoned);
//        //超时时间(以秒数为单位)
//        dataSource.setRemoveAbandonedTimeout(removeAbandonedTimeout);
//        //取得连接时是否进行有效性验证（即是否还和数据库连通的）
//        dataSource.setTestOnBorrow(testOnBorrow);
//        //返回连接时是否进行有效性验证（即是否还和数据库连通的）
//        dataSource.setTestOnReturn(testOnReturn);
//        //连接空闲时是否进行有效性验证（即是否还和数据库连通的）
//        dataSource.setTestWhileIdle(testWhileIdle);
//
//
//        /**
//         * 他们两个配合，可以持续更新连接池中的连接对象，当timeBetweenEvictionRunsMillis 大于0时，每过timeBetweenEvictionRunsMillis 时间，
//         * 就会启动一个线程，校验连接池中闲置时间超过minEvictableIdleTimeMillis的连接对象。
//         * */
//        if (StringUtils.isNotBlank(timeBetweenEvictionRunsMillis)) {
//            dataSource.setTimeBetweenEvictionRunsMillis(Long.parseLong(timeBetweenEvictionRunsMillis));
//        }
//
//        if (StringUtils.isNotBlank(minEvictableIdleTimeMillis)) {
//
//            dataSource.setMinEvictableIdleTimeMillis(Long.parseLong(minEvictableIdleTimeMillis));
//        }
//
//        try {
//            connection = dataSource.getConnection();
//            System.out.println("从BasicDataSource池中获取连接：" + connection);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("对不起连接数据库失败，请查看您的配置.......");
//        }
        return dataSource;
    }

    //事务管理
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
        sqlSessionFactory.setDataSource(getDataSource());
        return sqlSessionFactory.getObject();
    }
}
