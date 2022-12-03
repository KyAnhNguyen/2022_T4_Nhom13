package com.example.webdw.Service;

import com.example.webdw.beans.Province;
import com.example.webdw.dao.ProvinceDAO;
import com.example.webdw.db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class ProvinceService {
    private static ProvinceService instance = null;
    Jdbi jdbi = JDBIConnector.get();

    public ProvinceService() {
    }

    public static ProvinceService getInstance() {
        if (instance == null)
            instance = new ProvinceService();

        return instance;
    }

    public List<Province> getProvinceByCD() {
        return jdbi.withExtension(ProvinceDAO.class, handle -> handle.findByCD());
    }
}
