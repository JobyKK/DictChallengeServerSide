package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import dao.HibernateUtil;
import dao.StatisticsDAO;
import dao.UserDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
	
	
    private static final long serialVersionUID = 1L;
 
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
 
    	String email = request.getParameter("email");
        String pwd = request.getParameter("pwd");
        UserDAO userDAO = new UserDAO();
        if(userDAO.checkUser(email, pwd)){
        	//is session available - return descriptor 
            HttpSession session = request.getSession(true);
            User user = userDAO.user;
            user.setSession(session);
     
            session.setAttribute("user", user.getId());
            //setting session to expire in 30 mins
            session.setMaxInactiveInterval(30*60);
            /*Cookie userName = new Cookie("user", email);
            userName.setMaxAge(30*60);
            response.addCookie(userName);
	            String emails = "";
	            for(User u : UserDAO.getUsers()){
	            	emails += u.getEmail() + " ";
	            }
	            Cookie userList = new Cookie("userList", emails);
	            userList.setMaxAge(10*60);
	            response.addCookie(userList);*/
            PrintWriter out = response.getWriter();
            out.write(session.getId());
            out.flush();
            //response.sendRedirect("LoginSuccess.jsp");
        }else{
            //RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.html");
            PrintWriter out = response.getWriter();
            out.write("not found");
            out.flush();
            //rd.include(request, response);
        }
    	
        
    }
    
}