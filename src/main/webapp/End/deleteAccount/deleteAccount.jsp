  <!DOCTYPE html>
  <html>
  	<head>
  		<title>Account Deletion Page</title>
  	</head>
  	<link rel='stylesheet' href='button.css'>
  	<style>
  	
  		body {background: #FAEBD7}
  	
  	</style>
  	<body>
  	<h1 style="color:red;">WARNING: ONCE YOU DELETE YOUR ACCOUNT YOU WILL NOT BE ABLE TO RECOVER IT</h1>
  	<h3 style="color:red;">To delete your account, please enter your current password below.</h3>
	<form action="../../deleteAccountServlet" method="POST">
       Password: <input type="password" name="pass"/> <br/>
       <input type="submit" value="Delete Your Account"/>
     </form>
  	</body>
  </html>