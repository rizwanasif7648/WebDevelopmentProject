<html>
	<head>
		<title name="title">TransactionDetails</title>
	
		<link rel="stylesheet" type="text/CSS" href="style.css"/>
	</head>
	<%@ page language = "java" import = "javax.servlet.*, javax.servlet.http.*, java.io.*, java.sql.*, java.util.Date, java.text.DateFormat, java.util.Enumeration" session="false"%>
	<body>
	<br/>
		<br/>
		<h1 style="text-align:center"><font color = "Blue">ONLINE BANKING SYSTEM </font></h1>
		<h2 style="text-align:center"	><font color = "Red">Transaction Details </font></h2>

	
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
				query = "SELECT * FROM 	transaction WHERE account = '"+account+"'";
				ResultSet rs = st.executeQuery( query );
				if(rs.next())
				{
				%>	
				<table align = "center" border = "2" bgcolor="lightblue"  cellspacing="10">
				<tr>
					<th>Account N0</th>
					<th>Date/Time</th>
					<th>Amount</th>
					<th>Status</th>
				</tr>
				<%
					rs.beforeFirst();
					while(rs.next())
					{
						String acc = rs.getString("saccount");
						String t = rs.getString("time");
						int amo = rs.getInt("amount");
						String typ = rs.getString("type");
				%>
						
						<tr>
						<td><%= acc %></td>
						<td><%= t %></td>
						<td><%= amo %></td>
						<td><%= typ %></td>
						</tr>
						
				<%	
					}
				%>
				
				</table>
				
				<%
				}
				else
				{
				%>	
					<h2 align = "center"><font color = "red">No record</font></h2>
					
				<%
				}
				%>
				<br/>
				<button style="margin-left:38%;"><a href = "Applicants.html">Activity</a></button>
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
			<button style="margin-left:38%;"><a href = "LogIn.html">LogIn</a></button>
			<%
		}
	%>
	</body>
</html>