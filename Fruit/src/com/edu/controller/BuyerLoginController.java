package com.edu.controller;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.edu.service.FruitService;

public class BuyerLoginController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		HttpSession session = request.getSession();
		
		if(id.isEmpty()||pw.isEmpty()) {
			request.setAttribute("temp", "모든 항목을 입력해 주시기 바랍니다");
			HttpUtil.forward(request, response, "/buyer/fruitBuyerLogin.jsp");
			return;
		}
		
		FruitService service = FruitService.getInstance();
		String dbPw = service.fruitBuyerLogin(id);
		
		if(dbPw==null) {
			request.setAttribute("temp", "아이디 혹은 비밀번호가 일치하지 않습니다.");
			HttpUtil.forward(request,response,"/buyer/fruitBuyerLogin.jsp");
		}
		
		if(pw.equals(dbPw)) {
			if(session.isNew()||session.getAttribute("id")==null) {
				session.setAttribute("id",id);
				HttpUtil.forward(request,response,"/buyer/fruitBuyerMenu.jsp");
			}else {
				request.setAttribute("temp", "이미 로그인한 상태입니다.");
				HttpUtil.forward(request,response,"/buyer/fruitBuyerLogin.jsp");
			}
		}else {
			request.setAttribute("temp", "아이디 혹은 비밀번호가 일치하지 않습니다.");
			HttpUtil.forward(request,response,"/buyer/fruitBuyerLogin.jsp");
		}
		
	}

}
