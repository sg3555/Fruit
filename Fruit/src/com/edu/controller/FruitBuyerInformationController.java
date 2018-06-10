package com.edu.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import com.edu.vo.FruitBuyer;
import com.edu.service.FruitService;

public class FruitBuyerInformationController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		FruitService service = FruitService.getInstance();
		FruitBuyer fb = service.fruitBuyerInformation(id);
		
		request.setAttribute("fb", fb);
		HttpUtil.forward(request, response, "fruitBuyerInformation.jsp");
		
	}
}
