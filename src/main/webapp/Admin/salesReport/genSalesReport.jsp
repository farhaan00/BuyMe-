<!DOCTYPE html>
<html>
	<head>
		<title>Generate Sales Report</title>
	</head>
	<style>
	 	
	 	body{background: #FFFAF0;}
	 	</style>
	<body>
		<h1>Generate Sales Report</h1>
		<div>
		<form action="../../fetchReportServlet" method="POST">
Choose Data Type: <select size="1" id="data_type" title="" name="option_type">
				<option value="total_earnings">Total Earnings</option>
				<option value="best_items">Best Items</option>
				<option value="best_sellers">Best Sellers</option>
			</select><br>
			 <input type="submit" value="Submit"/><br><br><br>
			 </form>
Or Search by Item or User: 
		<form action="../../obtainSpecificSalesServlet" method="POST">
   <p> Choose a specification:</p>
         <input type="radio" id="item_type" name="specification" value="item_type">
         <label for="seller">By Item Type</label><br>
         <input type="radio" id="seller" name="specification" value="seller">
         <label for="seller">By Seller Username</label><br><br>
       <input type="submit" value="Submit"/>
     </form><br><br>
			 <a href='../adminMain.jsp'>Go back to Admin Main Page</a>
			 
			</div>
			
	</body>
</html>