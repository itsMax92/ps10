package com.mycaptcha.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mycaptcha.utils.VerifyRecaptcha;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet(description = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.sendRedirect("login.html");
		String user = request.getParameter("user");
		String pass = request.getParameter("pwd");
		
		System.out.print("WOking ");
		
		String recaptchaResponse = request.getParameter("g-recaptcha-response");
		System.out.println(recaptchaResponse);
		boolean verify = VerifyRecaptcha.verify(recaptchaResponse);
		
		String userId = getServletConfig().getInitParameter("user");
		String password = getServletConfig().getInitParameter("password");
		
		System.out.println(":: Captcha Verify ::" +verify);
		
		if(verify) {
			response.sendRedirect("loginsuccess.jsp");
		}else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
			PrintWriter out = response.getWriter();
			if(verify) {
				out.println("<font color=red>Either username or password is incorrect.</font>");
			}else {
				out.println("<font color=red>You missed the Captcha.</font>");
			}
			rd.include(request, response);
		}
	}

}
