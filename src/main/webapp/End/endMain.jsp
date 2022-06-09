<%@ page import ="java.sql.*" %>
<%@ page import ="java.util.*" %>
<%

Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
Statement st = con.createStatement();
Statement st2 = con.createStatement();
ResultSet rs, rs2;

String user = (String)session.getAttribute("user");
rs = st.executeQuery("select a.alert_message from alerts a where a.uname = '" + user + "'");
rs2 = st2.executeQuery("select name, current_price, end_date from auctions natural join items "
	+ "where end_date > now() and seller = '" + user + "'");

%>
<!DOCTYPE html>
<html>
	<head>
	<link rel='stylesheet' href='../CSS/main.css'>
	 <link rel='stylesheet' href='../CSS/navbar.css'>
	 <link rel='stylesheet' href='end.css'>
	 
	 	<style>
	 	
	 		/* body{background: #FFFAF0;} */
	 		
	 	</style>
	  
		<title>Welcome to the Buy Me Website</title>
	</head>
	<body>
		<div class = 'navbar'>
			<a href='endMain.jsp' class='active'>Home</a>
			<div class = 'dropdown'>
				<button class='dropbtn'>Create a new listing</button>
					<div class='dropdown-content'>
						<a href='CreateAuction/sedanListing.jsp'>Sedans</a>
						<a href='CreateAuction/bikeListing.jsp'>Bike</a>
						<a href='CreateAuction/truckListing.jsp'>Trucks</a>
					</div>
			</div>
			<div class='dropdown'>
				<button class='dropbtn'>Browse active listings</button>
				<div class='dropdown-content'>
					<a href='BrowseAuctions/endSedanParams.jsp'>Sedans</a>
					<a href='BrowseAuctions/endBikeParams.jsp'>Bike</a>
					<a href='BrowseAuctions/endTruckParams.jsp'>Trucks</a>
				</div>
			</div>
			<div class='dropdown'>
				<button class='dropbtn'>Browse Items</button>
				<div class='dropdown-content'>
					<a href='BrowseVehicles/endSedanParams.jsp'>Sedans</a>
					<a href='BrowseVehicles/endBikeParams.jsp'>Bike</a>
					<a href='BrowseVehicles/endTruckParams.jsp'>Trucks</a>
				</div>
			</div>
			<a href='AuctionBidHistory/searchAuctionHistory.jsp'>Browse Auction Bid Histories</a>
			<a href='UserHistory/searchUserHistory.jsp'>Browse User Histories</a>
			<a href='CustomerService/endCustomerService.jsp'>Customer Service</a>
			<a href='deleteAccount/deleteAccount.jsp'>Delete Account</a>
			<a href='../logout.jsp' style='float:right'>Log out</a>
		</div>
		<h1>Welcome to BuyMe.</h1>
		<p>This version of Buy Me will allow you to sell and buy Vehicles such as Sedans, Trucks and Bikes</p>
		<h2>Welcome, <% out.print(session.getAttribute("user").toString());%></h2>
		<div class='center-collapse'>
			<button class='collapsible'>Your Alerts</button>
			<div class='collapsible-content'>
			<% if (!rs.isBeforeFirst() ) { %>
				<p>You have no alerts at this time.</p>
			<% } else { %>
				<table>
					<% while (rs.next()) { %>
					<tr>
						<td><%= rs.getString("alert_message") %></td>
					</tr>
					<% } %>
				</table>
			<% } %>
			</div>
			<button class='collapsible' class='text-center'>Your Auctions</button>
			<div class='collapsible-content'>
			<% if (!rs2.isBeforeFirst() ) { %>
				<p>You currently have no live auctions.</p>
			<% } else { %>
				<table>
					<tr>
						<th>Item</th>
						<th>Current Bid</th>
						<th>End Date</th>
					<tr>
					<% while (rs2.next()) { %>
					<tr>
						<td><%= rs2.getString("name") %></td>
						<td>$<%= rs2.getString("current_price") %></td>
						<td><%= rs2.getString("end_date") %></td>
					</tr>
					<% } %>
				</table>
			<% } %>
			</div>
		</div>
		
		
		<script src='end.js'></script>
		
		
		
	</body>
</html>