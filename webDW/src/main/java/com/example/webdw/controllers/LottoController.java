package com.example.webdw.controllers;

import com.example.webdw.Service.DateDimService;
import com.example.webdw.Service.LottoService;
import com.example.webdw.Service.PrizeService;
import com.example.webdw.Service.ProvinceService;
import com.example.webdw.beans.DateDim;
import com.example.webdw.beans.Lotto;
import com.example.webdw.beans.Prize;
import com.example.webdw.beans.Province;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "LottoController", value = "/LottoController")
public class LottoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrizeService prizeService = new PrizeService();
        List<Prize> prizeList = prizeService.getAllPrizeByCD();

        ProvinceService provinceService = new ProvinceService();
        List<Province> provinceList = provinceService.getProvinceByCD();

        LottoService lottoService = new LottoService();

        DateDimService dateDimService = new DateDimService();
        int date_sk = lottoService.getAllLottoByDP(provinceList.get(0).getId_province()).get(0).getCreated_date();
        DateDim dateDim = dateDimService.getAllDateDimByDS(date_sk).get(0);
        dateDim.translateDay_of_week();

        request.setAttribute("provinceList", provinceList);
        request.setAttribute("prizeList", prizeList);
        request.setAttribute("lottoService", lottoService);
        request.setAttribute("dateDim", dateDim);
        request.getRequestDispatcher("lotto.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
