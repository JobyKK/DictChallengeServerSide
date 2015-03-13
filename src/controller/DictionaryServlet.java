package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;

import dao.DictionaryDAO;
import dao.StatisticsDAO;

@WebServlet("/DictionaryServlet")
public class DictionaryServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String JSESSIONID = request.getParameter("JSESSIONID");
		HttpSession session  = (HttpSession)getSession(JSESSIONID);
		if(null != session){
			Integer userId = ((Integer)session.getAttribute("user"));
			DictionaryDAO dictionaty = new DictionaryDAO(userId);
			PrintWriter out = response.getWriter();
	        try {
				out.write(dictionaty.getJsonDictioanries().toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        out.flush();
		}else
		{
			PrintWriter out = response.getWriter();
			out.write("nope");
	        out.flush();
		}
		/*StatisticsDAO statisticsDAO = new StatisticsDAO(user, hibernateUtil);
        PrintWriter out = response.getWriter();
        try {
			out.print(statisticsDAO.getJsonStatistics());
		} catch (JSONException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	public HttpSession getSession(String id){
		return HttpSessionCollector.find(id);
		
	}
	
}
