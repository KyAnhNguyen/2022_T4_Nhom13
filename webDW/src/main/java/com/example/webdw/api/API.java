package com.example.webdw.api;

import com.example.webdw.Service.DateDimService;
import com.example.webdw.Service.LottoService;
import com.example.webdw.Service.PrizeService;
import com.example.webdw.Service.ProvinceService;
import com.example.webdw.beans.DateDim;
import com.example.webdw.beans.Prize;
import com.example.webdw.beans.Province;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet (urlPatterns = "/api/getProvince")
public class API extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        ProvinceService provinceService = new ProvinceService();
        List<Province> provinceList = provinceService.getProvinceByCD();
        PrintWriter pw = resp.getWriter();
        String gson = new Gson().toJson(provinceList);
        pw.print(gson);
        pw.flush();
        pw.close();
    }

}
