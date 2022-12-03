package com.example.webdw.dao;

import com.example.webdw.beans.Province;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(Province.class)
public interface ProvinceDAO {
    @SqlQuery("select id_province, name_province from Province WHERE created_date = (SELECT max(created_date) FROM Province)")
    List<Province> findByCD();

}
