<%@ page import ="java.sql.*" %>
<%@ page import ="java.util.*" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Search User History</title>
	</head>
	<style>
		body{
			background: #FFFAF0;
		}
	
	</style>
	<body>
	
		<% try {
	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");

			Statement stmt = con.createStatement();
			String query = "SELECT uname FROM users WHERE acct_type = 'end'";

			ResultSet rs = stmt.executeQuery(query);
			
		%>
	
		<h1>User History Search</h1>
		<div>
		<form action="userHistory.jsp" method="POST">
			Username:	<input type="text" name="user"/><br/><br/>
			Show auctions where user is a: <input type="radio" id="buyer" name="role" value="buyer" checked/>
			<label for="buyer">Buyer</label>
			<input type="radio" id="seller" name="role" value="seller"/>
			<label for="buyer">Seller</label><br/>
			<input type="submit" value="Submit"/>
			</form>
			<br/>
			<a href='../endMain.jsp'>Go back to BuyMe Main Page</a>
		</div>
		
		<h4>User List</h4>
		<ul>
		
			<% while (rs.next()) { %>
				
				<li><%= rs.getString("uname") %></li>
				
			<% }
			//close the connection.
			con.close();
			%>
		
		</ul>
		
		<%} catch (Exception e) {
			out.print(e);
		}%>
		
	</body>
</html>