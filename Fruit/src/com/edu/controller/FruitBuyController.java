package com.edu.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import com.edu.vo.*;
import com.edu.service.FruitService;

public class FruitBuyController implements Controller{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException{
		HttpSession session = request.getSession();
		String buyerid = (String)session.getAttribute("id");
		String sellerid = request.getParameter("sellerid");
		String buycount = request.getParameter("buycount");
		
		
		if(sellerid.isEmpty()||buycount.isEmpty()) {
			request.setAttribute("temp", "��� �׸��� �Է��� �ֽñ� �ٶ��ϴ�");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		
		int wantbuy = Integer.parseInt(buycount);
		
		if(wantbuy<0) {
			request.setAttribute("temp", "���� ������ 0���� ���� ���� �����ϴ�.");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		FruitService service = FruitService.getInstance();
		// 1. �˻��� ID�� �����ϴ��� Ȯ��
		FruitSeller fs = service.sellerSearch(sellerid);
		if(fs==null) {
			request.setAttribute("temp", "�Ǹ��� ID�� �ùٸ��� �Է��� �ֽñ� �ٶ��ϴ�.");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		// 2. DB���� �Ǹ��� ID�� price, count, money�� ȣ��
		// 2-1. �ش� ���� int�� ��ȯ
		int sellercount = Integer.parseInt(fs.getCount());
		System.out.println("sellercount : "+sellercount);
		int sellerprice = Integer.parseInt(fs.getPrice());
		int sellermoney = Integer.parseInt(fs.getMoney());
		// 3. DB���� �������� count, money�� ȣ��
		FruitBuyer fb = service.buyerSearch(buyerid);
		// 3-1. �ش� ���� int�� ��ȯ
		int buyercount = Integer.parseInt(fb.getCount());
		System.out.println("buyercount : "+buyercount);
		int buyermoney = Integer.parseInt(fb.getMoney());
		
		// 4. �Ǹ���
		sellercount -= wantbuy;
		if(sellercount<0) {
			request.setAttribute("temp", "��� �����մϴ�");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		sellermoney += wantbuy*sellerprice;
		
		// 5. ������
		buyercount +=wantbuy;
		buyermoney -= wantbuy*sellerprice;
		if(buyermoney<0) {
			request.setAttribute("temp", "����� �����մϴ�");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		
		// 6. ���� ��� ���� �� String ������ ��ȯ
		FruitSeller fs2 = new FruitSeller();
		fs2.setCount(String.valueOf(sellercount));
		fs2.setMoney(String.valueOf(sellermoney));
		fs2.setId(sellerid);
		FruitBuyer fb2 = new FruitBuyer();
		fb2.setCount(String.valueOf(buyercount));
		fb2.setMoney(String.valueOf(buyermoney));
		fb2.setId(buyerid);
		// 6.1 �ش� ���� ��� DB�� ������Ʈ
		service.sellerUpdate(fs2);
		service.buyerUpdate(fb2);
		// 7. ���ȭ�� ���
		request.setAttribute("temp", "Complete");
		HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
	}

}
