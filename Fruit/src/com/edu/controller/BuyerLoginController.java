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
			request.setAttribute("temp", "��� �׸��� �Է��� �ֽñ� �ٶ��ϴ�");
			HttpUtil.forward(request, response, "/buyer/fruitBuyerLogin.jsp");
			return;
		}
		
		FruitService service = FruitService.getInstance();
		String dbPw = service.fruitBuyerLogin(id);
		
		if(dbPw==null) {
			request.setAttribute("temp", "���̵� Ȥ�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			HttpUtil.forward(request,response,"/buyer/fruitBuyerLogin.jsp");
		}
		
		if(pw.equals(dbPw)) {
			if(session.isNew()||session.getAttribute("id")==null) {
				session.setAttribute("id",id);
				HttpUtil.forward(request,response,"/buyer/fruitBuyerMenu.jsp");
			}else {
				request.setAttribute("temp", "�̹� �α����� �����Դϴ�.");
				HttpUtil.forward(request,response,"/buyer/fruitBuyerLogin.jsp");
			}
		}else {
			request.setAttribute("temp", "���̵� Ȥ�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
			HttpUtil.forward(request,response,"/buyer/fruitBuyerLogin.jsp");
		}
		
	}

}
