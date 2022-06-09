<!DOCTYPE html>
<html>
	<head>
		<title>Customer Service</title>
	</head>
	<style>
		body{
			background: #FFFAF0;
		}
		a:link, a:visited {
  background-color: black;
  color: white;
  padding: 14px 25px;
  text-align: center; 
  text-decoration: none;
  display: inline-block;
}

a:hover, a:active {
  background-color: red;
}
	
	</style>
	<body>
		<h1>Customer Service Page</h1>
		<div>
			<br/>
			<a href="../../browseQuestionsServlet">Browse Questions</a><br>
			<br/>
			<a href="../../searchQuestionsServlet">Search Questions</a>
			<br/>
		</div>
		<br/>
		<div>
		<form action="../../addQuestionServlet" method="POST">
			Ask a question:	<input type="text" maxlength="200" size="130" name="question"/><br/><br/>
			<input type="submit" value="Submit"/>
		</form>
			<a href='../endMain.jsp'>Go back to Main Page</a>
		</div>
	</body>
</html>