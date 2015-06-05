package com.tontwen.bottledetection.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tontwen.bottledetection.BottleValveChangeResult;
import com.tontwen.bottledetection.ValveInfo;
import com.tontwen.database.UserDao;

/**
 * Servlet implementation class AddValve
 */
public class AddValve extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddValve() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String jsonString = "";
		request.setCharacterEncoding("UTF-8");
    	jsonString = request.getParameter("content");
//		jsonString="{\"valveType\":\"CJ-280\",\"valveTestPressure\":\"20\",\"valveTestMedium\":\"air\","
//				+ "\"valveAddOperator\":\"π‹¿Ì‘±\"}";
		System.out.println(jsonString);
		
		ValveInfo vInfo= new Gson().fromJson(jsonString, new TypeToken<ValveInfo>(){}.getType());
		UserDao ud = new UserDao();
		int rc=ud.addValveInfo(vInfo);
		String json;
		if(rc==1){
			json ="{\"isSuccess\":\"true\"}";
		}else{
			json ="{\"isSuccess\":\"false\"}";
		}
		OutputStream stream = response.getOutputStream();
		stream.write(json.getBytes("UTF-8"));
	}

}
