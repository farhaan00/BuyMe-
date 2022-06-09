<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel='stylesheet' href='CSS/forms.css'>
		<title>Registration Form</title>
	</head>
	<body>
		<div class='back'>
			<h1>BuyMe Registration</h1>
			<form action="register" method="POST">
				Username: <input type="text" class='login' name="username">
				Password: <input type="password" name="password">
				Email: <input type="email" name="email">
				Mobile: <input type="text" name="mobile">
				<div class='link'>
					<input type="submit" value="Register"/><br>
					<a href='login.jsp'>Go back to login page</a>
				</div>
			</form>
		</div>
	</body>
</html>