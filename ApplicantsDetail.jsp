<html>
	<head>
		<title name="title">ApplicantsDetails</title>
	
		<link rel="stylesheet" type="text/CSS" href="style.css"/>
	</head>
	<%@ page language = "java" import = "javax.servlet.*, javax.servlet.http.*, java.io.*, java.sql.*, java.util.Date, java.text.DateFormat, java.util.Enumeration"%>
	<body>
	<br/>
		<br/>
		<h1 style="text-align:center"><font color = "Blue">ONLINE BANKING SYSTEM </font></h1>
		<h2 style="text-align:center"	><font color = "Red">Applicants Details </font></h2>

	<br/>


	
	<%
		if(session != null)
		{
			String account2=request.getParameter("account2");
			try
			{
				Class.forName("com.mysql.jdbc.Driver");

				String url = "jdbc:mysql://127.0.0.1/jbl";
				out.println(account2);
				Connection con=DriverManager.getConnection(url,"root","root");
				String query= "";
				Statement st=con.createStatement();
				query = "SELECT * FROM 	applicants WHERE account = '"+account2+"'";
				ResultSet rs = st.executeQuery( query );
				
				if(rs.next()){
				%>	
				<table align = "center" border = "2" bgcolor="lightblue"  cellspacing="20">
				<%
						String name = rs.getString("name");
						String fname = rs.getString("fname");
						int amo = rs.getInt("balance");
						String cnic2 = rs.getString("cnic");
						
						%>
						<tr>
						<td><b>Account No.</b><%= account2 %></td>
						<td><b>CNIC:</b><%= cnic2 %></td>
						</tr>
						<tr>
						<td><b>Name:</b><%= name %></td>
						<td><b>Father's name:</b><%= fname %></td>
						</tr>
						<tr>
						<td><b>Balance:</b><%= amo %></td>
						</tr>
				
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
				<button style="margin-left:48%;"><a href = "Admins.html">Activity</a></button>
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
			%>
			<%= "Your account has been expired, Please LogIn again"%>
			<button><a href = "LogIn.html">LogIn</a></button>
			<%
		}
	%>
	</body>
</html>