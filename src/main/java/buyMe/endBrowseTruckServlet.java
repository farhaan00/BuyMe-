package buyMe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class endBrowseTruckServlet
 */
@WebServlet("/endBrowseTruckServlet")
public class endBrowseTruckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public endBrowseTruckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println("<head>");
		out.println("<title>List of Bikes</title>");
		out.println("<style>\n"
				+ "			table {\n"
				+ "				border: 1px solid black;\n"
				+ "				border-collapse: collapse;	\n"
				+ "				width: 100%\n"
				+ "			}\n"
				+ "			th, td {\n"
				+ "				text-align: left;\n"
				+ "				padding: 15px;\n"
				+ "			}	\n"
				+ "			tr:nth-child(even) {\n"
				+ "				background-color: #f2f2f2;\n"
				+ "			}\n"
				+ "			body{background: #FFFAF0;}\n"
				+ "		</style>");
		out.println("</head>");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			ResultSet rs;
			
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
			if(accidentFree.equals("false")){
				accident = false;
			}
			else{
				accident = true;
			}

			String terrain = request.getParameter("terrain");
			if(terrain.equals("")){
				terrain = "terrain";
			}
			else{
				terrain = "'" + terrain + "'";
			}


			String pullStrength = request.getParameter("pullStrength");

			//boolean push;
			if(pullStrength.equals("")){
				pullStrength = "pull_strength";
			}
			else{
				pullStrength = "'" + pullStrength + "'";
			}

			String storage = request.getParameter("storage");
			if(storage.equals("")){
				storage = "truck_storage";
			}
			else{
				storage = "'" + storage + "'";
			}
			
			


			//String maxPrice = request.getParameter("maxPrice");

			// Create the strings to fill the query
			if (itemCondition.equals("any")) {
				itemCondition = "item_condition";
			} else {
				itemCondition = "'"+ itemCondition + "'";
			}



			
			
			String query = "select * from items natural join trucks where item_condition = " + itemCondition + " and make = " + make + " and model = " + model;
			rs = st.executeQuery(query);
			
			
			if(rs.next()) {
				out.println("<h1>List of Bikes</h1>\n"
						+ "		<table>\n"
						+ "			<tr>\n"
						+ "				<td><b>Name</b></td>\n"
						+ "				<td><b>Condition</b></td>\n"
						+ "				<td><b>Make</b></td>\n"
						+ "				<td><b>Model</b></td>\n"
						+ "				<td><b>Color</b></td>\n"
						+ "				<td><b>Design</b></td>\n"
						+ "				<td><b>Mileage</b></td>\n"
						+ "				<td><b>Accident Free</b></td>\n"
						+ "     		<td><b>All Terrain</b></td>\n"
						+ "				<td><b>Pull Strength</b></td>\n"
						+ "				<td><b>Truck Storage</b></td>\n"
						
						+ "				<td><b>Add Alert</b></td>\n"
						+ "				<td><b>Auction History</b></td>\n"
						+ "			</tr>\n"
						+ "			<tr>");
				
				out.println("<td>" + rs.getString("name") + "</td>");
				
				if(rs.getString("item_condition").equals("brandnew")) {
					out.println("<td>" + "Brand New" + "</td>");
				}
				else if(rs.getString("item_condition").equals("good")) {
					out.println("<td>" + "Good" + "</td>");
				}
				else {
					out.println("<td>" + "Fair" + "</td>");
				}
			}
			
			out.println("<td>" + rs.getString("make") + "</td>");
			out.println("<td>" + rs.getString("model") + "</td>");
			out.println("<td>" + rs.getString("color") + "</td>");
			out.println("<td>" + rs.getString("design") + "</td>");
			out.println("<td>" + rs.getString("mileage") + "</td>");
			out.println("<td>" + rs.getBoolean("accident") + "</td>");
			out.println("<td>" + rs.getBoolean("terrain") + "</td>");
			out.println("<td>" + rs.getString("pull_strength") + "</td>");
			out.println("<td>" + rs.getString("truck_storage") + "</td>");


			out.println("<td>\n"
					+ "					<form action=\"addAlertServlet\" method=\"POST\">\n"
					+ "						<input type=\"hidden\" name=\"itemID\" value=\"" +  rs.getString("item_id") + "\"" +  ">\n"
					+ "						<input type=\"submit\" value=\"Add Alert\">\n"
					+ "					</form>\n"
					+ "				</td>");
			
			
			out.println("<td>\n"
					+ "					<form action=\"auctionHistoryServlet\" method=\"POST\">\n"
					+ "						<input type=\"hidden\" name=\"itemID\" value=\"" +  rs.getString("item_id") + "\"" +  ">\n"
					+ "						<input type=\"submit\" value=\"Auction History\">\n"
					+ "					</form>\n"
					+ "				</td>");
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
