package com.edu.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.edu.service.FruitService;
import com.edu.vo.FruitBuyer;

public class BuyerJoinController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		if(id.isEmpty()||pw.isEmpty()) {
			request.setAttribute("temp", "모든 항목을 입력해 주시기 바랍니다");
			HttpUtil.forward(request, response, "/buyer/fruitBuyerJoin.jsp");
			return;
		}
		
		FruitBuyer fb = new FruitBuyer();
		fb.setId(id);
		fb.setPw(pw);
		
		FruitService service = FruitService.getInstance();
		service.buyerJoin(fb);
		
		HttpUtil.forward(request, response, "/buyer/fruitBuyerJoinOutput.jsp");
	}
}
