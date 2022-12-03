package com.example.webdw.Service;

import com.example.webdw.beans.DateDim;
import com.example.webdw.dao.DateDimDAO;
import com.example.webdw.db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class DateDimService {
    private static DateDimService instance = null;
    Jdbi jdbi = JDBIConnector.get();

    public DateDimService() {
    }

    public static DateDimService getInstance() {
        if (instance == null)
            instance = new DateDimService();

        return instance;
    }

    public List<DateDim> getAllDateDimByDS(int date_sk) {
        return jdbi.withExtension(DateDimDAO.class, handle -> handle.findByDateSk(date_sk));
    }
}
