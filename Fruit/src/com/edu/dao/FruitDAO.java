package com.edu.dao;

import java.sql.*;
import java.util.ArrayList;
import com.edu.vo.*;

public class FruitDAO {
	private static FruitDAO dao = new FruitDAO();
	
	private FruitDAO() {}
	
	public static FruitDAO getInstance() {
		return dao;
	}
	
	public Connection connect() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3306/market?useSSL=false&serverTimezone=Asia/Seoul";
			String user = "root";
			String password = "cs1234";
			conn = DriverManager.getConnection(url,user,password);
		}catch(Exception ex){
			System.out.println("1번오류 : "+ex);
		}return conn;
	}
	
	public void close(Connection conn, PreparedStatement ps, ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			}catch(Exception ex) {
				System.out.println("2번오류 : "+ex);
			}
		}
		close(conn,ps);
	}
	
	public void close(Connection conn, PreparedStatement ps) {
		if(ps!=null) {
			try {
				ps.close();
			}catch (Exception ex) {
				System.out.println("3번오류 : "+ex);
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			}catch(Exception ex) {
				System.out.println("4번오류 : "+ex);
			}
		}
	}
	
	public String fruitSellerLogin(String id) {
		String pw="";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = connect();
			psmt = conn.prepareStatement("select pw from seller where id=?;");
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			while(rs.next())
				pw=rs.getString(1);
		}catch(Exception e) {
			System.out.println("5번오류 : "+e);
		}finally {
			close(conn,psmt,rs);
		}
		return pw;
	}
	
	public void fruitSellerRegister(FruitSeller fs) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn=connect();
			psmt = conn.prepareStatement("update seller set count=?,price=?,money=? where id=?");
			psmt.setString(1, fs.getCount());
			psmt.setString(2, fs.getPrice());
			psmt.setString(3, fs.getMoney());
			psmt.setString(4, fs.getId());
			psmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("5번오류 : "+ex);
		}finally {
			close(conn,psmt);
		}
	}
	
	public FruitSeller fruitSellerInformation(String id) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		FruitSeller fs = null;
		
		try {
			conn = connect();
			psmt = conn.prepareStatement("select*from seller where id=?");
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				fs = new FruitSeller();
				fs.setCount(rs.getString(3));
				fs.setPrice(rs.getString(4));
				fs.setMoney(rs.getString(5));
			}
		}catch(Exception ex) {
			System.out.println("6번오류 : "+ex);
		}finally {
			close(conn,psmt,rs);
		}
		
		return fs;
	}
	
	public String fruitBuyerLogin(String id) {
		String pw="";
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		try {
			conn = connect();
			psmt = conn.prepareStatement("select pw from buyer where id=?;");
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			while(rs.next())
				pw=rs.getString(1);
		}catch(Exception e) {
			System.out.println("7번오류 : "+e);
		}finally {
			close(conn,psmt,rs);
		}
		return pw;
	}
	
	public void fruitBuyerRegister(FruitBuyer fb) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn=connect();
			psmt = conn.prepareStatement("update buyer set money=? where id=?");
			psmt.setString(1, fb.getMoney());
			psmt.setString(2, fb.getId());
			psmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("8번오류 : "+ex);
		}finally {
			close(conn,psmt);
		}
	}
	
	public FruitBuyer fruitBuyerInformation(String id) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		FruitBuyer fb = null;
		
		try {
			conn = connect();
			psmt = conn.prepareStatement("select*from buyer where id=?");
			psmt.setString(1, id);
			rs = psmt.executeQuery();
			if(rs.next()) {
				fb = new FruitBuyer();
				fb.setCount(rs.getString(3));
				fb.setMoney(rs.getString(4));
			}
		}catch(Exception ex) {
			System.out.println("9번오류 : "+ex);
		}finally {
			close(conn,psmt,rs);
		}
		
		return fb;
	}
	
	public ArrayList<FruitSeller>SellerList(){
		ArrayList<FruitSeller>list = new ArrayList<FruitSeller>();
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		FruitSeller fs = null;
		try {
			conn=connect();
			psmt = conn.prepareStatement("select*from seller");
			rs=psmt.executeQuery();
			while(rs.next()) {
				fs = new FruitSeller();
				fs.setId(rs.getString(1));
				fs.setCount(rs.getString(3));
				fs.setPrice(rs.getString(4));
				list.add(fs);
			}
		}catch(Exception ex) {
			System.out.println("10번오류 : "+ex);
		}finally {
			close(conn,psmt,rs);
		}
		return list;
	}
	
	public FruitSeller sellerSearch(String id) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		FruitSeller fs = null;
		
		try {
			conn = connect();
			psmt = conn.prepareStatement("select*from seller where id=?");
			psmt.setString(1, id);
			rs=psmt.executeQuery();
			if(rs.next()) {
				fs = new FruitSeller();
				fs.setCount(rs.getString(3));
				fs.setPrice(rs.getString(4));
				fs.setMoney(rs.getString(5));
			}
		}catch(Exception ex) {
			System.out.println("11번오류 : "+ex);
		}finally {
			close(conn,psmt,rs);
		}
		
		return fs;
	}
	
	public FruitBuyer buyerSearch(String id) {
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		FruitBuyer fb = null;
		
		try {
			conn = connect();
			psmt = conn.prepareStatement("select*from buyer where id=?");
			psmt.setString(1, id);
			rs=psmt.executeQuery();
			if(rs.next()) {
				fb = new FruitBuyer();
				fb.setCount(rs.getString(3));
				fb.setMoney(rs.getString(4));
			}
		}catch(Exception ex) {
			System.out.println("12번오류 : "+ex);
		}finally {
			close(conn,psmt,rs);
		}
		
		return fb;
	}
	
	public void sellerUpdate(FruitSeller fs) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn=connect();
			psmt = conn.prepareStatement("update seller set count=?, money=? where id=?");
			psmt.setString(1, fs.getCount());
			psmt.setString(2, fs.getMoney());
			psmt.setString(3, fs.getId());
			psmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("13번오류 : "+ex);
		}finally {
			close(conn,psmt);
		}
	}
	
	public void buyerUpdate(FruitBuyer fb) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn=connect();
			psmt = conn.prepareStatement("update buyer set count=?, money=? where id=?");
			psmt.setString(1, fb.getCount());
			psmt.setString(2, fb.getMoney());
			psmt.setString(3, fb.getId());
			psmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("14번오류 : "+ex);
		}finally {
			close(conn,psmt);
		}
	}
	
	public void sellerJoin(FruitSeller fs) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = connect();
			psmt = conn.prepareStatement("insert into seller values(?,?,'0','0','0')");
			psmt.setString(1, fs.getId());
			psmt.setString(2, fs.getPw());
			psmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("15번 오류 : "+ex);
		}finally {
			close(conn,psmt);
		}
	}
	
	public void buyerJoin(FruitBuyer fb) {
		Connection conn = null;
		PreparedStatement psmt = null;
		try {
			conn = connect();
			psmt = conn.prepareStatement("insert into buyer values(?,?,'0','0')");
			psmt.setString(1, fb.getId());
			psmt.setString(2, fb.getPw());
			psmt.executeUpdate();
		}catch(Exception ex) {
			System.out.println("16번 오류 : "+ex);
		}finally {
			close(conn,psmt);
		}
	}

}
