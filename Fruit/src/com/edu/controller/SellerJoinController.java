package com.edu.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.edu.service.FruitService;
import com.edu.vo.FruitSeller;

public class SellerJoinController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		if(id.isEmpty()||pw.isEmpty()) {
			request.setAttribute("temp", "모든 항목을 입력해 주시기 바랍니다");
			HttpUtil.forward(request, response, "/seller/fruitSellerJoin.jsp");
			return;
		}
		
		FruitSeller fs = new FruitSeller();
		fs.setId(id);
		fs.setPw(pw);
		
		FruitService service = FruitService.getInstance();
		service.sellerJoin(fs);
		
		HttpUtil.forward(request, response, "/seller/fruitSellerJoinOutput.jsp");
	}
}
