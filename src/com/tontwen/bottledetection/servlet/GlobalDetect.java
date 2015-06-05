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
 * Servlet implementation class GlobalDetect
 */
public class GlobalDetect extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GlobalDetect() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String bottleType = request.getParameter("bottleType");
		bottleType="1";
		UserDao ud=new UserDao();
		ArrayList<TestWaited> list=new ArrayList<TestWaited>();
		list=ud.executeAllGlobalDetectWaitedBottleQuery(bottleType);
		//System.out.println(list.get(0).getBottleNumber()+" "+list.get(0).getCarNumber());
		String json = new Gson().toJson(list);
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
