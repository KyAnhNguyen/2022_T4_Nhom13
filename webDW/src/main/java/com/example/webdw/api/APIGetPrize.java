package com.example.webdw.api;

import com.example.webdw.Service.PrizeService;
import com.example.webdw.Service.ProvinceService;
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

@WebServlet(urlPatterns = "/api/getPrize")
public class APIGetPrize extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        PrizeService prizeService = new PrizeService();
        List<Prize> prizeList = prizeService.getAllPrizeByCD();
        PrintWriter pw = resp.getWriter();
        String gson = new Gson().toJson(prizeList);
        pw.print(gson);
        pw.flush();
        pw.close();
    }
}
