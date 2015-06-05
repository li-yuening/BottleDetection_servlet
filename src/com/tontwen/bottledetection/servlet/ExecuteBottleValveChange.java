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
import com.tontwen.bottledetection.InnerDryResult;
import com.tontwen.database.UserDao;

/**
 * Servlet implementation class ExecuteBottleValveChange
 */
public class ExecuteBottleValveChange extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteBottleValveChange() {
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
//		jsonString="{\"bottleDetectNumber\":\"CR15000017\",\"operatorName\":\"管理员\",\"bottleValveTestNo\":\"40\","
//				+ "\"bottleValveType\":\"QF-T1\",\"bottleValveTestPressure\":\"20\",\"bottleValveTestMedium\":\"air\","
//				+ "\"bottleValveChangeResult\":\"更换新阀\"}";
		System.out.println(jsonString);
		
		BottleValveChangeResult bvcResult= new Gson().fromJson(jsonString, new TypeToken<BottleValveChangeResult>(){}.getType());
		UserDao ud = new UserDao();
		int rc=ud.executeBottleValveChange(bvcResult);
		System.out.println(bvcResult.getBottleDetectNumber());
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
