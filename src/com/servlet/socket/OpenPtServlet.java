package com.servlet.socket;

import java.io.IOException;
import java.net.ServerSocket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.servlet.socket.ServSocket;

/**
 * Servlet implementation class OpenServPort 用于即时通信，app启动时，服务端自动打开一个断口，监听客户端的连接
 */
@WebServlet("/OpenServPort")
public class OpenPtServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OpenPtServlet() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		ServSocket servSocket = new ServSocket();
//		servSocket.openport();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
