import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.util.Enumeration;


public class Transaction extends HttpServlet {
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException 
	{
		
		HttpSession sess = request.getSession(false);
		if(sess != null)
		{
			Enumeration en = sess.getAttributeNames();
			String account2=request.getParameter("account2");
			int amount = Integer.parseInt(request.getParameter("balance"));
			String password=request.getParameter("password");
			String account = "";
			String cpassword = "";
			
			response.setContentType("text/html");
			
			PrintWriter out = response.getWriter();
			
			
			while(en.hasMoreElements())
			{
				account = (String)en.nextElement();
				cpassword = (String)sess.getAttribute(account);
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
					if(account2.equals(account))
					{
						out.println("<html>");
						out.println("<head><title>error</title></head>");
						out.println("<body>");
						out.println("<h1>"+account2+"</h1> <br/> <h2>Invalid Account no</h2>");
						out.println("</body></html>");
					}
					else{
						query = "SELECT * from applicants where account = '"+account2+"';";
						ResultSet record = st.executeQuery(query);
						if(record.next())
						{		
							query = "Update applicants SET balance = balance - "+ amount +" where account = '"+ account +"' and balance >= "+ amount +" and "+amount+" > 0;";
							int rs = st.executeUpdate( query );
							if(rs == 1)
							{
								Date currentDate = new Date();   
								String time = DateFormat.getInstance().format(currentDate);
								query = "INSERT INTO transaction(type, account, amount, time, saccount) VALUES('sent', '"+account+"', "+amount+", '"+time+"', '"+account2+"');";
								int ps = st.executeUpdate(query);
								out.println(query);
								if(ps == 1)
								{
									query = "Update applicants SET balance = balance + "+ amount +" where account = '"+ account2 +"';";								
									rs = st.executeUpdate( query );
									out.println(query);
									if(rs == 1)
									{
										query = "INSERT INTO transaction(type, account, amount, time, saccount) VALUES('received', '"+account2+"', "+amount+", '"+time+"', '"+account+"');";
										ps = st.executeUpdate(query);
										out.println(query);
										if(ps == 1)
										{
											response.sendRedirect("Applicants.html");
										}
										else{
											response.sendRedirect("Transaction.html");
										}
										
									}
									else{
									response.sendRedirect("Transaction.html");					
									}							
								}
								else
								{
									response.sendRedirect("Transaction.html");
								}
								
							}
							else
							{
								
								//PrintWriter out = response.getWriter();
								
								out.println("<html>");
								out.println("<head><title>error</title></head>");
								out.println("<body>");
								out.println("<h1>Your amount is not valid</h1>");
								out.println("</body></html>");
								
							}
						}
						else{
							out.println("<html>");
							out.println("<head><title>error</title></head>");
							out.println("<body>");
							out.println("<h1>"+account2+"</h1> <br/> <h2>Invalid Account no</h2>");
							out.println("</body></html>");
						}	
						
						st.close();
						con.close();
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
		else
		{
			response.sendRedirect("LogIn.html");
		}
	}
}
