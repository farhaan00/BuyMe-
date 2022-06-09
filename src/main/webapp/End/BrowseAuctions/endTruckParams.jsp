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
		<h1>Set your parameters and Browse for Active Truck Listings</h1>
		<p>Remember to fill out all the fields!! </p>
		<div>
			<form action="endBrowseTrucks.jsp" method="POST">
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
			<a href="../endMain.jsp">Return to the Main Page</a>
		</div>
	</body>
</html>