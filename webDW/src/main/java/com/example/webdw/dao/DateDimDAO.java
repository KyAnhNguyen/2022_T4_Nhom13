package com.example.webdw.dao;

import com.example.webdw.beans.DateDim;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(DateDim.class)
public interface DateDimDAO {
    @SqlQuery("select * from date_dim WHERE date_sk=:date_sk")
    List<DateDim> findByDateSk(@Bind("date_sk") int date_sk);

}
