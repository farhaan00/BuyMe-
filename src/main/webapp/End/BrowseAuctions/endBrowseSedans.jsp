<%@ page import ="java.sql.*" %>
<%@ page import ="java.util.*" %>
<!DOCTYPE html>
<html>
	<head>
		<title>Live Auctions</title>
		<style>
			table {
				border: 1px solid black;
				border-collapse: collapse;	
				width: 100%
			}
			th, td {
				text-align: left;
				padding: 15px;
			}	
			tr:nth-child(even) {
				background-color: #f2f2f2;
			}
			body{background: #FFFAF0;}
		</style>
	</head>

<%

Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
Statement st = con.createStatement();
ResultSet rs;

// Get all of the parameters that the user entered
String itemCondition = request.getParameter("condition");

String color = request.getParameter("color");
if(color.equals("")){
	color = "color";
}
else{
	color = "'" + color + "'";
}

String make = request.getParameter("make");
if(make.equals("")){
	make = "make";
}
else{
	make = "'" + make + "'";
}

String model = request.getParameter("model");


if(model.equals("")){
	model = "model";
}
else{
	model = "'" + model + "'";
}


String design = request.getParameter("design");
if(design.equals("")){
	design = "design";
}
else{
	design = "'" + design + "'";
}


String year = request.getParameter("year");
if(year.equals("")){
	year = "vehicle_year";
}
else{
	year = "'" + year + "'";
}

String mileage = request.getParameter("mileage");
if(mileage.equals("")){
	mileage = "mileage";
}
else{
	mileage = "'" + mileage + "'";
}


String accidentFree = request.getParameter("accident");

boolean accident;
if(accidentFree.equals("False")){
	accident = false;
}
else{
	accident = true;
}

String trunkSize = request.getParameter("trunksize");
if(trunkSize.equals("")){
	trunkSize = "trunk_size";
}
else{
	trunkSize = "'" + trunkSize + "'";
}


String pushToStart = request.getParameter("start");

boolean push;
if(pushToStart.equals("False")){
	push = false;
}
else{
	push = true;
}

String numberDoors = request.getParameter("doors");
if(numberDoors.equals("")){
	numberDoors = "doors";
}
else{
	numberDoors = "'" + numberDoors + "'";
}


String maxPrice = request.getParameter("maxPrice");

// Create the strings to fill the query
if (itemCondition.equals("any")) {
	itemCondition = "item_condition";
} else {
	itemCondition = "'"+ itemCondition + "'";
}



if (maxPrice.equals("")) {
	maxPrice = "current_price";
}

// Create the query and insert the proper values afterwards
String similarQuery;
if (request.getParameter("sort").equals("priceHighToLow")) {
	similarQuery = "select * from auctions a natural join (select * from items i natural join sedans s" + " where i.item_id = s.item_id) s where a.item_id = s.item_id and now() < end_date and item_condition = " + itemCondition + " and make = " + make + " and model = " + model + " and color = " + color + " and design = " + design + " and vehicle_year = " + year + " and current_price <= " + maxPrice + " and mileage = " + mileage + " and accident = " + accident + " order by current_price desc"; //+ " and trunk_size = " + trunkSize + " and push_start = " + push + " and doors = " + numberDoors + " order by current_price desc";
} else {
	similarQuery = "select * from auctions a natural join (select * from items i natural join sedans s" + " where i.item_id = s.item_id) s where a.item_id = s.item_id and now() < end_date and item_condition = " + itemCondition + " and make = " + make + " and model = " + model + " and color = " + color + " and design = " + design + " and vehicle_year = " + year + " and current_price <= " + maxPrice + " and mileage = " + mileage + " and accident = " + accident + " order by current_price desc"; //+ " and trunk_size = " + trunkSize + " and push_start = " + push + " and doors = " + numberDoors + " order by current_price desc";
}


// Execute the prepared query
rs = st.executeQuery(similarQuery);
	
// If there exists a similar item, update its quantity and create a new auction with that item
if (rs.next()) { %>
	<h1>List of Sedan auctions</h1>
		<form action="../../bidOnItemServlet" method="POST">
		<table>
			<tr>
				<td><b>Auction ID</b></td>
				<td><b>Name</b></td>
				<td><b>Condition</b></td>
				<td><b>Make</b></td>
				<td><b>Model</b></td>
				<td><b>Color</b></td>
				<td><b>Current Price</b></td>
				<td><b>Design</b>
				<td><b>Year</b></td>
				<td><b>Mileage</b></td>
				<td><b>Accident Free</b></td>
				<td><b>Trunk Size</b></td>
				<td><b>Push to Start</b></td>
				<td><b>Number of Doors</b></td>
				<td><b>End Date and Time</b>
			</tr>
			<tr>
				<td><%= rs.getString("auction_id") %></td>
				<td><%= rs.getString("name") %></td>
				
					<!-- Make text user friendly for item condition -->
					<% if (rs.getString("item_condition").equals("brandnew")) {
						%> <td>Brand New</td> <% 
					} else if (rs.getString("item_condition").equals("good")) {
						%> <td>Good</td> <%
					} else {
						%> <td>Fair</td> <%
					} %>
					
				<td><%= rs.getString("make") %></td>
				<td><%= rs.getString("model") %></td>
				<td><%= rs.getString("color") %></td>
				<td>$<%= rs.getString("current_price") %></td>
				<td><%= rs.getString("design") %></td>
				<td><%= rs.getString("vehicle_year") %></td>
				<td><%= rs.getString("mileage") %></td>
				<td><%= rs.getBoolean("accident") %></td>
				<td><%= rs.getString("trunk_size") %></td>
				<td><%= rs.getBoolean("push_start") %></td>
				<td><%= rs.getString("doors") %></td>
				<td><%= rs.getString("end_date") %></td>
				<td><input type="hidden" name="auctionID" value="<%= rs.getString("auction_id") %>">
					<input type="submit" value="Bid">
				</td>
			</tr>
			<% while (rs.next()) { %>
				<tr>
					<td><%= rs.getString("auction_id") %></td>
					<td><%= rs.getString("name") %></td>
				
						<!-- Make text user friendly for item condition -->
						<% if (rs.getString("item_condition").equals("brandnew")) {
							%> <td>Brand New</td> <% 
						} else if (rs.getString("item_condition").equals("good")) {
							%> <td>Good</td> <%
						} else {
							%> <td>Fair</td> <%
						} %>
					
				<td><%= rs.getString("make") %></td>
				<td><%= rs.getString("model") %></td>
				<td><%= rs.getString("color") %></td>
				<td>$<%= rs.getString("current_price") %></td>
				<td><%= rs.getString("design") %></td>
				<td><%= rs.getString("vehicle_year") %></td>
				<td><%= rs.getString("mileage") %></td>
				<td><%= rs.getBoolean("accident") %></td>
				<td><%= rs.getString("trunk_size") %></td>
				<td><%= rs.getBoolean("push_start") %></td>
				<td><%= rs.getString("doors") %></td>
				<td><%= rs.getString("end_date") %></td>
					<td><input type="hidden" name="auctionID" value="<%= rs.getString("auction_id") %>">
						<input type="submit" value="Bid">
					</td>
				</tr>
			<% } %>
		</table>
		</form>
		<a href="endSedanParams.jsp">Return to the search parameters page</a>
	</html>
	
<% } else {
	out.println("We could not find an auction with your specifications, please come back later." + 
		"<div><a href='endSedanParams.jsp'>New Search</a></div>");
}
%>