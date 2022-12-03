package com.example.webdw.Service;

import com.example.webdw.beans.Lotto;
import com.example.webdw.dao.LottoDAO;
import com.example.webdw.db.JDBIConnector;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class LottoService {
    private static LottoService instance = null;
    Jdbi jdbi = JDBIConnector.get();

    public LottoService() {
    }

    public static LottoService getInstance() {
        if (instance == null)
            instance = new LottoService();

        return instance;
    }

    public List<Lotto> getAllLottoByDP(String id_province) {
        return jdbi.withExtension(LottoDAO.class, handle -> handle.findByDP(id_province));
    }

    public List<Lotto> getAllLottoByDPP(String id_province, String id_prize) {
        return jdbi.withExtension(LottoDAO.class, handle -> handle.findByDPP(id_province, id_prize));
    }

}
