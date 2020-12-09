import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;


public class LogOut extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException 
	{
		HttpSession sess = request.getSession(false);
		if(sess != null)
		{
			sess.invalidate();
		}
		response.sendRedirect("LogIn.html");
	}
}
