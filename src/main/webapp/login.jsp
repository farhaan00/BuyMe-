<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html>
	<head>
		
		<title>Login Form</title>
	</head>
	<style>
	
	body{
	
		background-color: #2f4353;
		background-image: linear-gradient(315deg, #2f4353 0%, #d2ccc4 74%);
	
	}
	
	
input[type=text], input[type=password], input[type=email], select{
  	width: 100%;
  	padding: 12px 20px;
  	margin: 8px 0;
  	display: inline-block;
  	border: 1px solid #ccc;
  	border-radius: 4px;
  	box-sizing: border-box;
}


input[type=submit] {
 	width: 50%;
  	background-color: #33B5FF;
  	color: white;
  	padding: 14px 20px;
  	margin: 8px 0;
  	border: none;
  	border-radius: 4px;
  	cursor: pointer;
}

input[type=submit]:hover {
  	background-color: #9BDBFF;
}

.link {
	text-align: center;
}

.back {
  	margin: 50px auto;
	width: 25%;
	border-radius: 5px;
	background-color: #f2f2f2;
	padding: 20px;
}

	
	
	
	</style>
	<body>
		<div class='back'>
			<h1>BuyMe Login</h1>
			<form action="login" method="POST">
				Username: <input type="text" name="username">
				Password: <input type="password" name="password">
				<div class='link'>
					<input type="submit" value="Login"/><br>
					<a href='register.jsp'>New user? Register here</a>
				</div>
			</form>
		</div>
	</body>
</html>