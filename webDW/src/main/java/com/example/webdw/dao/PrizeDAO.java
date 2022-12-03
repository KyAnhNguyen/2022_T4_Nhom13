package com.example.webdw.dao;

import com.example.webdw.beans.Prize;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(Prize.class)
public interface PrizeDAO {
    @SqlQuery("select * from prize WHERE created_date=(select max(created_date) from prize) order by value_prize")
    List<Prize> findByCD();
}
