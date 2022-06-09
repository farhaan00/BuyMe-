<!DOCTYPE html>
<html>
   <head>
      <title>Auction Deletion</title>
     <link rel='stylesheet' href='forms.css'>
      
      
      <style>
   	
   		

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
      
   </head>
   
 
   
   <body>
   <h1>Auction Deletion</h1>
     <form action="../../deleteAuctionServlet" method="POST">
      Auction ID:	<input type="text" name="auction_id"/> <br/>
      <h3>This removes all bids and can not be undone</h3>
       <input type="submit" value="Submit"/>
       <div>
       		<a href='../repMain.jsp'>Go back to Customer Representative Main Page</a>
       </div>
     </form>
   </body>
</html>