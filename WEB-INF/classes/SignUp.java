import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import javax.swing.*;


public class SignUp extends HttpServlet {
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
  { 
	response.setContentType("text/html");
	PrintWriter out = response.getWriter();

    String name=request.getParameter("name");
    String fname=request.getParameter("fname");
	String acct_type = request.getParameter("acctType");
    String email=request.getParameter("email");
    String phone=request.getParameter("phone");
    String city=request.getParameter("city");
    String province=request.getParameter("province");
    String account=request.getParameter("account_no");
    String cnic=request.getParameter("cnic");
    String password=request.getParameter("password");

    out.println("<html>");
    out.println("<head><title>Response</title></head>");


    try
	{

		Class.forName("com.mysql.jdbc.Driver");
		String url = "jdbc:mysql://127.0.0.1/jbl";


		Connection con=DriverManager.getConnection(url, "root", "root");
		String query = "";
		Statement st=con.createStatement();
		int check = 0;
		if(acct_type.equals("applicant"))
		{
			query = "SELECT * FROM applicants WHERE cnic = '"+cnic+"' or account = '"+account+"';";
			ResultSet rs = st.executeQuery( query );
			if(!rs.next())
			{
				query = "INSERT INTO applicants(name,fname,password,account,cnic, phone, email, city, province, balance) VALUES('"+ name + "','" + fname+ "', '" + password + "', '" + account + "', '" + cnic +"', '"+phone+"', '"+email+"', '" + city+"', '"+province+"', "+ 0 +");";
				int ps = st.executeUpdate( query );
				if(ps == 1)
				{
					check = 1;
				}
			}
			else
			{
				out.println("<h1>This record is already exist.</h1>"); ; 
			}
		}
		else if (acct_type.equals("admin"))
		{
			query = "SELECT * FROM admins WHERE cnic = '"+cnic+"';";
			ResultSet rs = st.executeQuery( query );
			if(!rs.next())
			{
				query = "INSERT INTO admins(name,fname,password,account,cnic, phone, email, city, province) VALUES('"+ name + "','" + fname+ "', '" + password + "', '" + account + "', '" + cnic +"', '"+phone+"', '"+email+"', '" + city+"', '"+province+"');";
				int ps = st.executeUpdate( query );
				if(ps == 1)
				{
					check = 1;
				}
			}
			else
			{
				out.println("<h1>This record is already exist.</h1>"); ; 
			}
		}
		System.out.println(query);

		

		if(check==1)
		{	
			response.sendRedirect("LogIn.html");		
		}
		else
		{
			out.println("<h1>Record could not be inserted.</h1>"); 		
		}

		out.println("</body></html>");

        st.close();
        con.close();
    }
	catch(Exception e)
	{
      out.println(e);
    }
	
  }
}
