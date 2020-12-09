import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.util.Enumeration;


public class DepositMoney extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException 
	{
	
		String account2=request.getParameter("account2");
		int amount = Integer.parseInt(request.getParameter("balance"));
		String password=request.getParameter("password");
		String category = request.getParameter("category");
		HttpSession sess = request.getSession(false);
		if(sess != null)
		{
			Enumeration en = sess.getAttributeNames();

			String cnic = "";
			String cpassword = "";
			while(en.hasMoreElements())
			{
				cnic = (String)en.nextElement();
				cpassword = (String)sess.getAttribute(cnic);
			}
			if(password.equals(cpassword))
			{
				try
				{
					Class.forName("com.mysql.jdbc.Driver");

					String url = "jdbc:mysql://127.0.0.1/jbl";

					Connection con=DriverManager.getConnection(url,"root","root");
					String query= "";
					Statement st=con.createStatement();
					query = "SELECT * from applicants where account = '"+account2+"';";
					ResultSet record = st.executeQuery(query);
					if(record.next())
					{
						if(category.equals("deposit"))
						{
							query = "Update applicants SET balance = balance + "+ amount +" where account = '"+ account2 +"';";
							
							int rs = st.executeUpdate( query );

							if(rs == 1)
							{	
								response.sendRedirect("ApplicantsDetail.html");							
							}
							else
							{	
								sess.invalidate();
								response.sendRedirect("LogIn.html");	
							}	
						}
						else if(category.equals("withdraw"))
						{
							query = "Update applicants SET balance = balance - "+ amount +" where account = '"+ account2 +"';";
							
							int rs = st.executeUpdate( query );

							if(rs == 1)
							{
									
								response.sendRedirect("ApplicantsDetail.html");							
							}
							else
							{
								sess.invalidate();
								response.sendRedirect("LogIn.html");
							}
							
						}
					}
					else
					{
						PrintWriter out = response.getWriter();
								
						out.println("<html>");
						out.println("<head><title>error</title></head>");
						out.println("<body>");
						out.println("<h1>Invalid Account no</h2>");
						out.println("</body></html>");
					}
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
		else
		{
			sess.invalidate();
			response.sendRedirect("LogIn.html");
		}
	}
}
