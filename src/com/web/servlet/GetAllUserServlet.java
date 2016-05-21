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
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;
import com.web.util.User;
import com.web.util.UserEntity;
<<<<<<< HEAD
import com.web.util.UserUtil;
=======
import com.web.util.getAllUser;
>>>>>>> origin/master

import java.util.ArrayList;
/**
 * Servlet implementation class GetAllUserServlet
 */
@WebServlet("/GetAllUserServlet")
public class GetAllUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetAllUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GetAllUserServlet is called!");
		
		java.util.List<UserEntity> list = new ArrayList<UserEntity>();
		String uid=request.getParameter("uid");
		
<<<<<<< HEAD
		new UserUtil().getAlllUserData(uid, list);
=======
		new getAllUser().getAlllUserData(uid, list);
>>>>>>> origin/master
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
