package com.edu.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.edu.vo.FruitSeller;
import com.edu.service.FruitService;

public class FruitSellerInformationController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		FruitService service = FruitService.getInstance();
		FruitSeller fs = service.fruitSellerInformation(id);
		
		request.setAttribute("fs", fs);
		HttpUtil.forward(request, response, "fruitSellerInformation.jsp");
		
		
	}
}
