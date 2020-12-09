import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.util.Enumeration;


public class AccountDetails extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException 
	{
		
		HttpSession sess = request.getSession(false);
		if(sess != null)
		{
			Enumeration en = sess.getAttributeNames();			
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
				
				query = "SELECT * FROM applicants where account = '"+account+"'";
				ResultSet rs = st.executeQuery( query );
				if(!rs.next())
				{
					PrintWriter out = response.getWriter();	
					out.println("<html>");
					out.println("<head><title>AccountDetails</title></head>");
					out.println("<body>");
					out.println("<h1>ONLINE BANKING SYSTEM</h1>");
					out.println("<h2>ACCOUNT DETAILS</h2>");
					out.println("Account No : "+rs.getString("account")+"<br/>");
					out.println("Email : "+rs.getString("email")+"<br/>");
					out.println("Phone Number: "+rs.getString("phone")+"<br/>");
					out.println("CNIC : "+rs.getString("cnic")+"<br/>");
					out.println("Balance : "+rs.getInt("balance")+"<br/>");
					out.println("<button><a href = "+"Applicants.html"+">Go Back</a></button>");
					out.println("</body></html>");
				}
				else
				{
					sess.invalidate();
					response.sendRedirect("LogIn.html");
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
