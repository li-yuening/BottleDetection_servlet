package com.tontwen.bottledetection;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		String operatorNumber=request.getParameter("operatorNumber");
		String operatorPwd=request.getParameter("operatorPwd");
		operatorNumber="0000001";
		operatorPwd="admin";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String jsonString = "";
//		request.setCharacterEncoding("UTF-8");
//		jsonString = request.getParameter("content");
//		LoginInfo loginInfo= new Gson().fromJson(jsonString, new TypeToken<LoginInfo>(){}.getType());
//		UserDao ud = new UserDao();
//		BottleDetectNumber_RptNo br = ud.executeChubuPanduan(cpResult);
//		System.out.println(br.getBottleDetectNumber()+" "+br.getRptNo());
		
//		String json = new Gson().toJson(br);
//		OutputStream stream = response.getOutputStream();
//		stream.write(json.getBytes("UTF-8"));
	}

}
