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
			request.setAttribute("temp", "모든 항목을 입력해 주시기 바랍니다");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		
		int wantbuy = Integer.parseInt(buycount);
		
		if(wantbuy<0) {
			request.setAttribute("temp", "구매 갯수가 0보다 작을 수는 없습니다.");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		FruitService service = FruitService.getInstance();
		// 1. 검색한 ID가 실재하는지 확인
		FruitSeller fs = service.sellerSearch(sellerid);
		if(fs==null) {
			request.setAttribute("temp", "판매자 ID를 올바르게 입력해 주시기 바랍니다.");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		// 2. DB에서 판매자 ID의 price, count, money값 호출
		// 2-1. 해당 값들 int로 변환
		int sellercount = Integer.parseInt(fs.getCount());
		System.out.println("sellercount : "+sellercount);
		int sellerprice = Integer.parseInt(fs.getPrice());
		int sellermoney = Integer.parseInt(fs.getMoney());
		// 3. DB에서 구매자의 count, money값 호출
		FruitBuyer fb = service.buyerSearch(buyerid);
		// 3-1. 해당 값을 int로 변환
		int buyercount = Integer.parseInt(fb.getCount());
		System.out.println("buyercount : "+buyercount);
		int buyermoney = Integer.parseInt(fb.getMoney());
		
		// 4. 판매자
		sellercount -= wantbuy;
		if(sellercount<0) {
			request.setAttribute("temp", "재고가 부족합니다");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		sellermoney += wantbuy*sellerprice;
		
		// 5. 구매자
		buyercount +=wantbuy;
		buyermoney -= wantbuy*sellerprice;
		if(buyermoney<0) {
			request.setAttribute("temp", "비용이 부족합니다");
			HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
			return;
		}
		
		// 6. 최종 결과 수집 후 String 값으로 변환
		FruitSeller fs2 = new FruitSeller();
		fs2.setCount(String.valueOf(sellercount));
		fs2.setMoney(String.valueOf(sellermoney));
		fs2.setId(sellerid);
		FruitBuyer fb2 = new FruitBuyer();
		fb2.setCount(String.valueOf(buyercount));
		fb2.setMoney(String.valueOf(buyermoney));
		fb2.setId(buyerid);
		// 6.1 해당 값들 모두 DB상에 업데이트
		service.sellerUpdate(fs2);
		service.buyerUpdate(fb2);
		// 7. 결과화면 출력
		request.setAttribute("temp", "Complete");
		HttpUtil.forward(request, response, "/buyer/fruitBuyOutput.jsp");
	}

}
