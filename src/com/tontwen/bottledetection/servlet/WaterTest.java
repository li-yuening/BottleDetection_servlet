package com.tontwen.bottledetection.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.tontwen.bottledetection.TestWaited;
import com.tontwen.database.UserDao;

/**
 * Servlet implementation class PressureTest
 */
public class WaterTest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WaterTest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String bottleType = request.getParameter("bottleType");
		UserDao ud=new UserDao();
		ArrayList<TestWaited> list=new ArrayList<TestWaited>();
		list=ud.executeWaterTestWaitedBottleQuery();
//		System.out.println(list.get(0).getBottleNumber()+" "+list.get(0).getCarNumber());
		String json = new Gson().toJson(list);
		System.out.println(json);
		OutputStream stream = response.getOutputStream();
		stream.write(json.getBytes("UTF-8"));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request,response);
	}

}
