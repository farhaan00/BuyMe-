<!DOCTYPE html>
<html>
	<head>
	<link rel='stylesheet' href='../../CSS/main.css'>
	 <link rel='stylesheet' href='../../CSS/navbar.css'> 
		<title>Welcome to the Buy Me Website</title>
	</head>
	<style>
		body{
			background: #FFFAF0;
		}
	
	</style>
	<body>
		<div class = 'navbar'>
			<a href='../endMain.jsp' class='active'>Home</a>
			<div class = 'dropdown'>
				<button class='dropbtn'>Create a new listing</button>
					<div class='dropdown-content'>
						<a href='../CreateAuction/sedanListing.jsp'>Sedans</a>
						<a href='../CreateAuction/bikeListing.jsp'>Bike</a>
						<a href='../CreateAuction/truckListing.jsp'>Trucks</a>
					</div>
			</div>
			<div class='dropdown'>
				<button class='dropbtn'>Browse active listings</button>
				<div class='dropdown-content'>
					<a href='../BrowseAuctions/endSedanParams.jsp'>Sedans</a>
					<a href='../BrowseAuctions/endBikeParams.jsp'>Bike</a>
					<a href='../BrowseAuctions/endTruckParams.jsp'>Trucks</a>
				</div>
			</div>
			<div class='dropdown'>
				<button class='dropbtn'>Browse Items</button>
				<div class='dropdown-content'>
					<a href='endSedanParams.jsp'>Sedans</a>
					<a href='endBikeParams.jsp'>Bike</a>
					<a href='endTruckParams.jsp'>Trucks</a>
				</div>
			</div>
			<a href='../AuctionBidHistory/searchAuctionHistory.jsp'>Browse Auction Bid Histories</a>
			<a href='../UserHistory/searchUserHistory.jsp'>Browse User Histories</a>
			<a href='../CustomerService/endCustomerService.jsp'>Customer Service</a>
			<!-- <a href='../Profile/endProfile.jsp'>Profile</a> -->
			<a href='../../logout.jsp' style='float:right'>Log out</a>
		</div>
		<div class='back'>
		<h1>Browse for Trucks</h1>
		<p>Fill out all fields</p>
		
		<div>
			<form action="../../endBrowseTruckServlet" method="POST">
				Condition: <select name="condition" id="condition">
					<option value="any">Any</option>
					<option value="brandnew">Brand New</option>
					<option value="good">Used: Good</option>
					<option value="fair">Used: Fair</option>
				</select><br>
				Make: <input type="text" name="make"><br>
				Model: <input type="text" name="model"><br>
				Color: <input type="text" name="color"><br>
				Design: <input type="text" name="design"><br>
				Year: <input type="text" name="year"><br>
				Mileage: <input type="text" name="mileage"><br>
				Accident Free?: <select name="accident" id="accident">
					<option value="False">No</option>
					<option value="True">Yes</option>
				</select><br>
				All Terrain?: <select name="terrain" id="terrain">
					<option value="False">No</option>
					<option value="True">Yes</option>
				</select><br>
				Pull Strength: <input type="text" name="pullStrength"><br>
				Truck Storage: <input type="text" name="storage"><br>
				Max price: <input type="number" step="5" name="maxPrice"><br>
				Sort by: <select name="sort" id="sort">
					<option value="priceHighToLow">Price: High to Low</option>
					<option value="priceLowToHigh">Price: Low to High</option>
				</select><br>
				<input type="submit" name="Submit">
			</form>
			<a href="../../endMain.jsp">Return to the Main Page</a>
		</div>