import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.util.Enumeration;


public class TransactionDetails extends HttpServlet {
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
				query = "SELECT * FROM 	transaction WHERE account = '"+account+"'";
				ResultSet rs = st.executeQuery( query );
				PrintWriter out = response.getWriter();
					
				out.println("<html>");
				out.println("<head><title>TransactionDetails</title></head>");
				out.println("<body>");
				out.println("<h1>ONLINE BANKING SYSTEM</h1>");
				out.println("<h2>TRANSACTION DETAILS</h2>");
				
				while(!rs.next())
				{
					out.println(""+rs.getString("account")+"\t"+rs.getString("time")+"\t"+rs.getInt("amount")+"\t"+rs.getString("type")+"");
				}
				
				out.println("</body></html>");
				
				
				st.close();
				con.close();

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
