<!DOCTYPE html>
<html>
	<head>
		<title>Set Search Parameters</title>
	</head>
	<style>
		body{
			background: #FFFAF0;
		}
		
	
	</style>
	<body>
		<h1>Set your parameters and Browse for Active Sedan Listings</h1>
		<p>Remember to fill out all the fields!! </p>
		<div>
			<form action="endBrowseSedans.jsp" method="POST">
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
				Trunk size (in cubic feet): <input type="text" name="trunksize"><br>
				Push to Start?: <select name="start" id="start">
					<option value="False">No</option>
					<option value="True">Yes</option>
				</select><br>
				Number of Doors: <input type="text" name="doors"><br>
				Max price: <input type="number" step="5" name="maxPrice"><br>
				Sort by: <select name="sort" id="sort">
					<option value="priceHighToLow">Price: High to Low</option>
					<option value="priceLowToHigh">Price: Low to High</option>
				</select><br>
				<input type="submit" name="Submit">
			</form>
			<a href="../endMain.jsp">Return to the Main Page</a>
		</div>
	</body>
</html>