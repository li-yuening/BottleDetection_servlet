package com.tontwen.bottledetection.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tontwen.bottledetection.LoginInfo;
import com.tontwen.bottledetection.OperatorInfo;
import com.tontwen.database.UserDao;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String jsonString = request.getParameter("content");
		//System.out.println(jsonString);
		LoginInfo li = new Gson().fromJson(jsonString, new TypeToken<LoginInfo>(){}.getType());

		String operatorNumber = li.getOperatorNumber();
		String operatorPwd = li.getOperatorPwd();
		System.out.println(operatorNumber + operatorPwd);
		//System.out.println(operatorNumber);
		UserDao ud=new UserDao();
		OperatorInfo oi=ud.checkLogin(operatorNumber, operatorPwd);
		String json;
		if(oi!=null){
			json=new Gson().toJson(oi);
			//			System.out.println(json);
			String checkLogin="{\"isLoginSuccess\":\"true\",\"OperatorInfo\":";
			json=checkLogin+json+"}";
			//			System.out.println(json);
		}else{
			json="{\"isLoginSuccess\":\"false\"}";
			//			System.out.println(json);
		}
		OutputStream stream = response.getOutputStream();
		stream.write(json.getBytes("UTF-8"));
	}

}
