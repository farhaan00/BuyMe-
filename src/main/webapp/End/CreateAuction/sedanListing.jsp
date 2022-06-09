<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
	<link rel='stylesheet' href='../../CSS/navbar.css'>
	<link rel='stylesheet' href='../../CSS/params.css'>
		<title>List A Sedan</title>
	</head>
	<style>
		body{
		
		background-color: #e9bcb7;
		background-image: linear-gradient(315deg, #e9bcb7 0%, #29524a 74%);
	}
	
	h1, p, h2,label{
	color: white;
}
	</style>
	<body>
		<div class = 'navbar'>
			<a href='../endMain.jsp' class='active'>Home</a>
			<div class = 'dropdown'>
				<button class='dropbtn'>Create a new listing</button>
					<div class='dropdown-content'>
						<a href='sedanListing.jsp'>Sedans</a>
						<a href='bikeListing.jsp'>Bikes</a>
						<a href='truckListing.jsp'>Trucks</a>
					</div>
			</div>
			<div class='dropdown'>
				<button class='dropbtn'>Browse active listings</button>
				<div class='dropdown-content'>
					<a href='../BrowseAuctions/endSedanParams.jsp'>Sedans</a>
					<a href='../BrowseAuctions/endBikeParams.jsp'>Bikes</a>
					<a href='../BrowseAuctions/endTruckParams.jsp'>Trucks</a>
				</div>
			</div>
			<div class='dropdown'>
				<button class='dropbtn'>Browse Items</button>
				<div class='dropdown-content'>
					<a href='../BrowseItems/endSedanParams.jsp.jsp'>Sedans</a>
					<a href='../BrowseItems/endBikeParams.jsp.jsp'>Bikes</a>
					<a href='../BrowseItems/endTruckParams.jsp'>Trucks</a>
				</div>
			</div>
			<a href='../BidHistory/searchAuctionHistory.jsp'>Browse Auction Bid Histories</a>
			<a href='../UserHistory/searchUserHistory.jsp'>Browse User Histories</a>
			<a href='../CustomerService/endCustomerService.jsp'>Customer Service</a>
			<!-- <a href='../Profile/endProfile.jsp'>Profile</a> -->
			<a href='../../logout.jsp' style='float:right'>Log out</a>
		</div>
		<div class='back'>
			<h1>Create a new Sedan listing</h1>
			<!-- idk about this yet -->
			<form action="../../addSedanServlet" method="POST">
				<div class='lcolumn'>
					<label>Name:</label>
					<input type="text" name="name"/>
					<label>Condition:</label>
					<select name="condition" id="condition">
						<option value="brandnew">Brand New</option>
						<option value="good">Used: Good</option>
						<option value="fair">Used: Fair</option>
					</select>
					
					<label>Make:</label>
					<input type="text" name="make"/>
					<label>Model:</label>
					<input type="text" name="model"/>
					<label>Color:</label>
					<input type="text" name="color"/>
					<label>Design:</label>
					<input type="text" name="design"/>
					<label>Year:</label>
					<input type="text" name="year"/>
					<label>Mileage:</label>
					<input type="text" name="mileage"/>
					<label>Auction end date and time:</label>
					<input type="datetime-local" name="endDate"/>
					<input type="submit" value="List Item">
				</div>
				<div class='rcolumn'>
					<label>Accident Free?</label>
					<select name="accident" id="accident">
						<option value=false>No</option>
						<option value=true>Yes</option>
					</select>
					<label>Trunk Size (cubic feet):</label>
					<input type="number" step="0.5" name="trunkSize">
					<label>Push to Start</label>
					<select name="start" id="start">
						<option value=false>No</option>
						<option value=true>Yes</option>
					</select>
					<label>Number of Doors</label>
					<input type="text" name="noOfdoors"/>
					
					
					<label>Minimum price (reserve):</label>
					<input type="number" step="0.01" name="reservePrice"/>
				</div>
			</form>
		</div>
	</body>
</html>