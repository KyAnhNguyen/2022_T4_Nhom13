package com.example.webdw.db;

import com.example.webdw.Service.DateDimService;
import com.example.webdw.Service.LottoService;
import com.example.webdw.Service.PrizeService;
import com.example.webdw.Service.ProvinceService;
import com.example.webdw.beans.DateDim;
import com.example.webdw.beans.Lotto;
import com.example.webdw.beans.Prize;
import com.example.webdw.beans.Province;

import java.sql.Date;
import java.util.List;

public class Test {
    public static void main(String[] args) {

//        ProvinceService provinceService = new ProvinceService();
//        List<Province> lp = provinceService.getProvinceByCD();
//        for (Province p : lp) {
//            System.out.println(p);
//        }

//        LottoService lottoService = new LottoService();
//        List<Lotto> lt = lottoService.getAllLottoByDPP(lp.get(2).getId_province(), "giai1");
//        lt.size()


//        for (Lotto l : lt) {
//            System.out.println(l);
//        }

//        DateDimService ddService = new DateDimService();
//        List<DateDim> ld = ddService.getAllDateDimByDS(lt.get(0).getCreated_date());
//        for (DateDim d : ld) {
//            System.out.println(d);
//        }

//        PrizeService prizeService = new PrizeService();
//        List<Prize> prizeList = prizeService.getAllPrizeByCD();
//
//        for (Prize p: prizeList) {
//            System.out.println(p);
//        }
        int i = 4;
        System.out.println(String.valueOf(i).equals("4"));


    }
}
