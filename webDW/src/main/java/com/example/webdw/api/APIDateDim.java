package com.example.webdw.api;

import com.example.webdw.Service.DateDimService;
import com.example.webdw.Service.LottoService;
import com.example.webdw.Service.ProvinceService;
import com.example.webdw.beans.DateDim;
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

@WebServlet(urlPatterns = "/api/getDateDim")
public class APIDateDim extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        LottoService lottoService = new LottoService();

        DateDimService dateDimService = new DateDimService();
        int date_sk = lottoService.getAllToday().get(0).getCreated_date();
        DateDim dateDim = dateDimService.getAllDateDimByDS(date_sk).get(0);
        dateDim.translateDay_of_week();

        PrintWriter pw = resp.getWriter();
        String gson = new Gson().toJson(dateDim);
        pw.print(gson);
        pw.flush();
        pw.close();
    }

}
