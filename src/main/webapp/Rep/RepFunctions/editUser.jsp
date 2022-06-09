<!DOCTYPE html>
<html>
   <head>
      <title>Edit End User Account</title>
   </head>
   
   <style>
   	body{
   		background: #FFFAF0;
   	}
   		
   </style>
   
   <body>
   <h1>Edit End User Account</h1>
     <form action="../../editUserServlet" method="POST">
       Old Username:	<input type="text" name="old_user" required="required"/> <br/>
       New Username: <input type="text" name="new_user" required="required"/> <br/>
       New Password: <input type="text" name="pass" required="required"/> <br/>
       Old Email:	<input type="email" name="old_email" required="required"/> <br/>
       New Email: <input type="text" name="new_email" required="required"/> <br/>
       <input type="submit" value="Submit"/>
       <div>
       		<a href='../repMain.jsp'>Go back to Customer Representative Main Page</a>
       </div>
     </form>
   </body>
</html>