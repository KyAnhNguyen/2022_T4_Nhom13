package com.example.webdw.api;

import com.example.webdw.Service.LottoService;
import com.example.webdw.beans.Lotto;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@WebServlet(urlPatterns = "/api/getNumSizeByIdPrize")
public class APIGetNumSizeByIdPrize extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String idPrize = req.getParameter("idPrize");
        LottoService lottoService = new LottoService();
        int output = lottoService.numberSizeById_prize(idPrize);
        PrintWriter pw = resp.getWriter();
        String gson = new Gson().toJson(output);
        pw.print(gson);
        pw.flush();
        pw.close();
    }
}
