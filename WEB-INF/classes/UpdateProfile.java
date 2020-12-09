import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.util.Enumeration;


public class UpdateProfile extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		HttpSession sess = request.getSession(false);
		out.println(sess);
		if(sess != null)
		{
			response.setContentType("text/html");
		
			Enumeration en = sess.getAttributeNames();
			String email=request.getParameter("email");
			String phone = request.getParameter("phone");
			String city = request.getParameter("city");
			String province = request.getParameter("province");
			String current_password=request.getParameter("password");
			String new_password=request.getParameter("newpassword");
			String c_password=request.getParameter("new_confirm_password");
			
			String account = "";
			String password = "";
			while(en.hasMoreElements())
			{
				account = (String)en.nextElement();
				password = (String)sess.getAttribute(account);
			}
			
			
			try
			{
				Class.forName("com.mysql.jdbc.Driver");

				String url = "jdbc:mysql://127.0.0.1/jbl";

				Connection con=DriverManager.getConnection(url,"root","root");
				String query= "";
				Statement st=con.createStatement();
				if(password.equals(current_password))
				{
					query = "Update applicants SET email = '"+email+"', phone = '"+phone+"', city = '"+city+"', province = '"+province+"', password = '"+new_password+"' where account = '"+ account +"';";
					int rs = st.executeUpdate( query );
					if(rs == 1)
					{
						sess.invalidate();
						response.sendRedirect("LogIn.html");
					}
					else
					{
						sess.invalidate();
						response.sendRedirect("LogIn.html");
					}
				}
				else 
				{
					response.sendRedirect("UpdateProfile.html");
				}

			}
			catch(Exception e)
			{
			  System.out.println(e);
			}
			
		}
		else
		{
			sess.invalidate();
			response.sendRedirect("LogIn.html");
		}
	}
}
