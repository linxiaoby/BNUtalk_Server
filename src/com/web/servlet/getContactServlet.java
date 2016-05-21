package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.web.util.ContactEntity;
import com.web.util.ContactUtil;
import com.web.util.UserEntity;
import com.web.util.UserUtil;

/**
 * Servlet implementation class getContacServlet
 */
@WebServlet("/getContactServlet")
public class getContactServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public getContactServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("getContactServlet is called!");
		
		java.util.List<ContactEntity> list = new ArrayList<ContactEntity>();
		String uid=request.getParameter("uid");
		new ContactUtil().getContact(uid, list);
		
		Gson json=new Gson();
		String strJson=json.toJson(list);
		
		response.setContentType("UTF-8");
		response.setStatus(200);
		PrintWriter os=response.getWriter();
		os.println(strJson);
		os.write(strJson);
		os.flush();
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
