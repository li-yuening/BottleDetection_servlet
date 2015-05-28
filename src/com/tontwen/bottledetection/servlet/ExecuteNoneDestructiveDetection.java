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
import com.tontwen.bottledetection.NoneDestructiveDetectionResult;
import com.tontwen.database.UserDao;

/**
 * Servlet implementation class ExecuteNoneDestructiveDetection
 */
public class ExecuteNoneDestructiveDetection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExecuteNoneDestructiveDetection() {
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
//    	jsonString = request.getParameter("content");
		jsonString="{\"bottleDetectNumber\":\"CR15000017\",\"operatorName\":\"π‹¿Ì‘±\",\"noneDestructivePosition\":\"\",\"noneDestructiveDetail\":\"1##1##q##w##e##r##t##\",\"noneDestructiveResult\":1}";
		System.out.println(jsonString);
		
		NoneDestructiveDetectionResult nddResult= new Gson().fromJson(jsonString, new TypeToken<NoneDestructiveDetectionResult>(){}.getType());
		UserDao ud = new UserDao();
		int rc=ud.executeNoneDestructiveDetection(nddResult);
		System.out.println(nddResult.getBottleDetectNumber());
		String json;
		if(rc==1){
			json ="{\"isNoneDestructiveDetectionSuccess\":\"true\"}";
		}else{
			json ="{\"isNoneDestructiveDetectionSuccess\":\"false\"}";
		}
		OutputStream stream = response.getOutputStream();
		stream.write(json.getBytes("UTF-8"));
	}

}
