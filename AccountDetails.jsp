<html>
	<head>
		<title name="title">AccountDetails</title>
	
		<link rel="stylesheet" type="text/CSS" href="style.css"/>
	</head>
	<%@ page language = "java" import = "javax.servlet.*, javax.servlet.http.*, java.io.*, java.sql.*, java.util.Date, java.text.DateFormat, java.util.Enumeration" session="false"%>
	<body>
	<br/>
		<br/>
		<h1 style="text-align:center"><font color = "Blue">ONLINE BANKING SYSTEM </font></h1>
		<h2 style="text-align:center"	><font color = "Red">Account Details </font></h2>

	<br/>


	
	<%
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
				query = "SELECT * FROM 	applicants WHERE account = '"+account+"'";
				ResultSet rs = st.executeQuery( query );
				if(rs.next()){
				%>	
				<table align = "center" border = "2" bgcolor="lightblue"  cellspacing="20">
				<%
				rs.beforeFirst();
					while(rs.next())
					{
						String name = rs.getString("name");
						String fname = rs.getString("fname");
						int amo = rs.getInt("balance");
						String acc = rs.getString("account");
						String cnic = rs.getString("cnic");
						
						%>
						<tr>
						<td><b>Account No.</b><%= acc %></td>
						<td><b>CNIC:</b><%= cnic %></td>
						</tr>
						<tr>
						<td><b>Name:</b><%= name %></td>
						<td><b>Father's name:</b><%= fname %></td>
						</tr>
						<tr>
						<td><b>Balance:</b><%= amo %></td>
						</tr>
						
						<%	
					}
				%>
				
				</table>
				<%
				}
				else{
				%>
				<p>No record </p>
				<%
				}
				%>
				<br/>
				<button style="margin-left:48%;"><a href = "Applicants.html">Activity</a></button>
				<%
				
				st.close();
				con.close();

			}
			catch(Exception e)
			{
				out.println(e);
			}
		}
		else
		{
			sess.invalidate();
			%>
			<%= "Your account has been expired, Please LogIn again"%>
			<button><a href = "LogIn.html">LogIn</a></button>
			<%
		}
	%>
	</body>
</html>