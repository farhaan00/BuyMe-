<%@ page import ="java.sql.*" %>
<%@ page import ="java.util.*" %>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Representative Answer Question Page</title>
	</head>
	<style>
	
		body{
		
			background: #FFFAF0;
		}
	</style>
	<body>
		<% try {
	
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BuyMe","root", "F-ali67890");

			Statement stmt = con.createStatement();
			String query = "SELECT * from Questions WHERE answer IS NULL";

			ResultSet rs = stmt.executeQuery(query);
			
		%>
		
		<h1>Customer Service</h1>
		<div>
			<a href="repAnsweredQuestions.jsp">My Answered Questions</a><br/>
		</div>
		
		
		<ul>
		
			<% while (rs.next()) { %>
			
				<% String question = rs.getString("question"); %>

				<li>
				
					<div>
						
						<% out.print(question); %>
						<form action="../../answerQuestionServlet" method="POST">
							Answer:	<input type="text" maxlength="200" size="130" name="answer"/><br/><br/>
						
						<input name="question" type="hidden" value='<%= question %>'>
						<input type="submit" value="Submit"/>
			</form>
					</div>
				
				
				</li>
				
			<% }
			//close the connection.
			con.close();
			%>
		
		</ul>
			
		<%} catch (Exception e) {
			out.print(e);
		}%>
	
		<a href='../repMain.jsp'>Go back to Customer Representative Page</a>
	</body>
</html>