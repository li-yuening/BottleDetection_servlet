package com.tontwen.bottledetection.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tontwen.bottledetection.GlobalDetectionResult;
import com.tontwen.database.UserDao;

/**
 * Servlet implementation class BottleDetectionResult
 */
public class ExecuteGlobalDetection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteGlobalDetection() {
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
//		jsonString="{\"bottleDetectNumber\":\"GP15000010\",\"bottleType\":\"0\",\"operatorName\":\"π‹¿Ì‘±\",\"detectDetailResult\":\"111\",\"appearDetail\":null,\"soundDetail\":null,\"whorlDetail\":null,\"innerDetail\":null,\"globalSub5Detail\":null,\"globalSub6Detail\":null}";
		System.out.println(jsonString);
		
		GlobalDetectionResult gdResult= new Gson().fromJson(jsonString, new TypeToken<GlobalDetectionResult>(){}.getType());
		UserDao ud = new UserDao();
		int rc=ud.executeGlobalDetect(gdResult);
		System.out.println(gdResult.getBottleDetectNumber());
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



