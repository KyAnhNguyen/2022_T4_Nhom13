package com.example.webdw.Service;

import com.example.webdw.beans.Prize;
import com.example.webdw.dao.PrizeDAO;
import com.example.webdw.db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class PrizeService {
    private static PrizeService instance = null;
    Jdbi jdbi = JDBIConnector.get();

    public PrizeService() {
    }

    public static PrizeService getInstance() {
        if (instance == null)
            instance = new PrizeService();

        return instance;
    }

    public List<Prize> getAllPrizeByCD() {
        return jdbi.withExtension(PrizeDAO.class, handle -> handle.findByCD());
    }
}
