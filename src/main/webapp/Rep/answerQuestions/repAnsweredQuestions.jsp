<%@ page import ="java.sql.*" %>
<%@ page import ="java.util.*" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Your Answered Questions</title>
		<style>
			table {
				border: 1px solid black;
				border-collapse: collapse;	
				width: 100%
			}
			table.center {
  				margin-left: auto; 
  				margin-right: auto;
			}
			th, td {
				text-align: left;
				padding: 15px;
			}	
			tr:nth-child(even) {
				background-color: #f2f2f2;
			}
			
		body{
			background: #FFFAF0;
		}
	
	
		</style>
	</head>
	<body>
		<% try {
	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BuyMe","root", "F-ali67890");

			String username = (String)session.getAttribute("user");
			PreparedStatement ps = con.prepareStatement("SELECT * FROM Questions WHERE rep_user = ? and answer IS NOT NULL");
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			
		%>
		<h1>Your Answered Questions</h1>
		<table>
		
			<tr>
				<th>Question</th>
				<th>Answer</th>
			</tr>
		
			<% while (rs.next()) { %>
			
				<tr>
					<td><%= rs.getString("question") %></td>
					<td><%= rs.getString("answer") %></td>
				</tr>
				
			<% }
			//close the connection.
			con.close();
			%>
		
		</table>
			
		<%} catch (Exception e) {
			out.print(e);
		}%>
		<a href='repCustService.jsp'>Go back to Customer Service Page</a>

	</body>
</html>