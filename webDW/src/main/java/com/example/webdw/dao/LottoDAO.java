package com.example.webdw.dao;

import com.example.webdw.beans.Lotto;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterBeanMapper(Lotto.class)
public interface LottoDAO {
    @SqlQuery("select * from Lotto WHERE created_date=(select max(created_date) from Lotto) and id_province=:id_province")
    List<Lotto> findByDP(@Bind("id_province") String id_province);

    @SqlQuery("select * from Lotto WHERE created_date=(select max(created_date) from Lotto) and id_province=:id_province and id_prize=:id_prize")
    List<Lotto> findByDPP(@Bind("id_province") String id_province, @Bind("id_prize") String id_prize);

}
