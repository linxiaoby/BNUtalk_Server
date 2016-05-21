package com.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.util.AddContactsUtil;
import com.web.util.ContactUtil;

/**
 * Servlet implementation class SaveLikeServerlet
 */
@WebServlet("/SaveLikeServerlet")
public class SaveLikeServerlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveLikeServerlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uid=request.getParameter("uid");
		String cuid=request.getParameter("cuid");
		System.out.println("SaveLikeServerlet is called!");
		new AddContactsUtil().saveLikeUid(uid, cuid);
		response.setContentType("UTF-8");
		response.setStatus(200);
		PrintWriter os=response.getWriter();
		os.flush();
	}

}
