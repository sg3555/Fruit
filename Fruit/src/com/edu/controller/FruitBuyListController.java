package com.edu.controller;

import java.io.*;
import java.util.ArrayList;
import javax.servlet.*;
import javax.servlet.http.*;

import com.edu.service.FruitService;
import com.edu.vo.*;

public class FruitBuyListController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		FruitService service = FruitService.getInstance();
		ArrayList<FruitSeller> list = service.SellerList();
		
		request.setAttribute("list", list);
		HttpUtil.forward(request, response, "fruitBuy.jsp");
	}

}
