<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<style>
	 	
	 	body{background: #FFFAF0;}
	 	</style>
	<head>
		<link rel="stylesheet" href="main.css"/>
		<title>BuyMe Admin Page</title>
	</head>
	<body>
		<h1>BuyMe Admin Page</h1>
		
		
		
			<div>
				<!-- <form action="../viewRepsServlet">
					<input type="submit" value="View Reps"/><br>
				</form>
				
				<form action="../createRepsServlet">
					<input type="submit" value="Create Reps"/><br>
				</form>
				
				<form action="../deleteRepsServlet">
					<input type="submit" value="Delete Reps"/><br>
				</form>
				
				<form action="../viewEndUsersServlet">
					<input type="submit" value="View End Users"/><br>
				</form>
				
				<form action="../generateSaleReportServlet">
					<input type="submit" value="Generate Report"/><br>
				</form> -->
				
				
			 	<a href='controlCustReps/viewReps.jsp'>View Customer Representatives</a><br/>
			 	<br/>
			 	
				<a href='controlCustReps/createRep.jsp'>Add Customer Representative</a><br/>
					<br/>
				<a href='controlCustReps/deleteRep.jsp'>Remove Customer Representative</a><br/>
					<br/>
				<a href='viewEndUsers.jsp'>View End Users</a><br/>
					<br/>
				<a href='salesReport/genSalesReport.jsp'>Create Sales Report</a><br/> 
					<br/>
				
				<a href='../logout.jsp'>Log out</a>
			</div>
		
		
		
			
		
		
		
		
		
	</body>
</html>