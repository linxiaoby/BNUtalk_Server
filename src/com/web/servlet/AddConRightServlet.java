package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.util.AddContactsUtil;

/**
 * Servlet implementation class CheckLikeServlet
 */
@WebServlet("/AddConRightServlet")
public class AddConRightServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AddConRightServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("AddConRightServlet is called!");
		doPost(request, response);
		String uid=request.getParameter("uid");
		String cuid=request.getParameter("cuid");
		response.setContentType("UTF-8");
		response.setStatus(200);
		PrintWriter os=response.getWriter();
		
		int flag=new AddContactsUtil().rightOperation(uid, cuid);
		if(flag==1)
		{
			os.write("1");
		}
		else {
			os.write("0");
		}
		os.flush();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
