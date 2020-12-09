import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;


public class LogIn extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,IOException 
  {
	//res.setContentType("text/html");
		
		
		String cnic=request.getParameter("cnic");
		String password=request.getParameter("password");
		String acct_type = request.getParameter("acctType");
  
    try
	{
		Class.forName("com.mysql.jdbc.Driver");

		String url = "jdbc:mysql://127.0.0.1/jbl";

		Connection con=DriverManager.getConnection(url,"root","root");
		String query= "";
		Statement st=con.createStatement();
		if(acct_type.equals("applicant"))
		{
			query="Select * from applicants where cnic="+cnic+" ";
			ResultSet rs = st.executeQuery( query );
			if(rs.next())
			{
				if(rs.getString("Password").equals(password))
				{
					String account = rs.getString("account");
					HttpSession sess = request.getSession();
					sess.setAttribute(account, password);
					
					response.sendRedirect("Applicants.html");
				}
				else{	
					response.sendRedirect("LogIn.html");
				}
			}
			else{
				response.sendRedirect("SignUp.html");
			}
			
		}
		else if (acct_type.equals("admin"))
		{
			query="Select * from admins where cnic="+cnic+" ";
			ResultSet rs = st.executeQuery( query );
			if(rs.next())
			{
				if(rs.getString("Password").equals(password))
				{
					HttpSession sess = request.getSession();
					sess.setAttribute(cnic, password);
					
					response.sendRedirect("Admins.html");
				}
				else{
					response.sendRedirect("LogIn.html");
				}
			}
			else{
				response.sendRedirect("SignUp.html");
			}
		}
		
	
		st.close();
		con.close();

    }
	catch(Exception e)
	{
      System.out.println(e);
    }

  }

}
