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
 * Servlet implementation class bidOnItemServlet
 */
@WebServlet("/bidOnItemServlet")
public class bidOnItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public bidOnItemServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		
		PrintWriter out = response.getWriter();	
		
		//out.print("jokeing");
		
		out.println("<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "	<head>\n"
				+ "		<title>Bid on item</title>\n"
				+ "		<style>\n"
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
				+ "		</style>\n"
				+ "	</head>\n"
				+ "	<h1>Bidding</h1>\n"
				+ "	<div>\n"
				+ "		<p>You can place a bid on this item directly. You will be notified if you are outbid or\n"
				+ "			if you win this item. You can set your bid increment\n"
				+ "			and the maximum price you are willing to pay.\n"
				+ "		</p>\n"
				+ "	</div>\n"
				+ "	<div>\n"
				+ "		<p> If you do not wish to set up autobidding, please leave the autobid fields blank.<p>");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			ResultSet rs;
			
			String auctionID = request.getParameter("auctionID");
			
			String isSedan = "select * from auctions a natural join (select * from items i natural join sedans s" + 
					" where i.item_id = s.item_id) s where a.auction_id = '" + auctionID + "' and a.item_id = s.item_id";
			
			rs = st.executeQuery(isSedan);
			
			if(rs.next()) {
				out.println("<table>\n"
						+ "			<tr>\n"
						+ "				<td><b>Name</b></td>\n"
						+ "				<td><b>Condition</b></td>\n"
						+ "				<td><b>Make</b></td>\n"
						+ "				<td><b>Model</b></td>\n"
						+ "				<td><b>Color</b></td>\n"
						+ "				<td><b>Current Price</b></td>\n"
						+ "				<td><b>Design</b>\n"
						+ "				<td><b>Year</b></td>\n"
						+ "				<td><b>Mileage</b></td>\n"
						+ "				<td><b>Accident Free</b></td>\n"
						+ "				<td><b>Trunk Size</b></td>\n"
						+ "				<td><b>Push to Start</b></td>\n"
						+ "				<td><b>Number of Doors</b></td>\n"
						+ "			</tr>");
				
				out.println("<tr>");
				out.println("<td>" + rs.getString("name") + "</td>");
				
				if(rs.getString("item_condition").equals("brandnew")) {
					out.println("<td> Brand New </td>");
				}
				else if(rs.getString("item_condition").equals("good")){
					out.println("<td> Good </td>");
				}
				else {
					out.println("<td> Fair </td>");
				}
				
				out.println("<td>" + rs.getString("make") + "</td>");
				out.println("<td>" + rs.getString("model") + "</td>");
				out.println("<td>" + rs.getString("color") + "</td>");
				out.println("<td>" + rs.getString("current_price") + "</td>");
				out.println("<td>" + rs.getString("design") + "</td>");
				out.println("<td>" + rs.getString("vehicle_year") + "</td>");
				out.println("<td>" + rs.getString("mileage") + "</td>");
				out.println("<td>" + rs.getString("accident") + "</td>");
				out.println("<td>" + rs.getString("trunk_size") + "</td>");
				out.println("<td>" + rs.getString("push_start") + "</td>");
				out.println("<td>" + rs.getString("doors") + "</td>");

				out.println("</tr>");
				out.println("</table>");
				
			}
				
			
			
			
			String isTruck = "select * from auctions a natural join (select * from items i natural join trucks t" + 
					" where i.item_id = t.item_id) t where a.auction_id = '" + auctionID + "' and a.item_id = t.item_id";
			
			
				
				rs = st.executeQuery(isTruck);
				
				
				if(rs.next()) {
					
				
				out.println("<table>\n"
						+ "			<tr>\n"
						+ "				<td><b>Name</b></td>\n"
						+ "				<td><b>Condition</b></td>\n"
						+ "				<td><b>Make</b></td>\n"
						+ "				<td><b>Model</b></td>\n"
						+ "				<td><b>Color</b></td>\n"
						+ "				<td><b>Current Price</b></td>\n"
						+ "				<td><b>Design</b>\n"
						+ "				<td><b>Year</b></td>\n"
						+ "				<td><b>Mileage</b></td>\n"
						+ "				<td><b>Accident Free?</b></td>\n"
	
						+ "				<td><b>All Terrain</b></td>\n"
						+ "				<td><b>Pull Strength</b></td>\n"
						+ "				<td><b>Truck Storage</b></td>\n"
						+ "			</tr>");
				
				
				
				out.println("<tr>");
				out.println("<td>" + rs.getString("name") + "</td>");
				
				
				if(rs.getString("item_condition").equals("brandnew")) {
					out.println("<td> Brand New </td>");
				}
				else if(rs.getString("item_condition").equals("good")){
					out.println("<td> Good </td>");
				}
				else {
					out.println("<td> Fair </td>");
				}
				
				
				out.println("<td>" + rs.getString("make") + "</td>");
				out.println("<td>" + rs.getString("model") + "</td>");
				out.println("<td>" + rs.getString("color") + "</td>");
				out.println("<td>" + rs.getString("current_price") + "</td>");
				out.println("<td>" + rs.getString("design") + "</td>");
				out.println("<td>" + rs.getString("vehicle_year") + "</td>");
				out.println("<td>" + rs.getString("mileage") + "</td>");
				out.println("<td>" + rs.getString("accident") + "</td>");
				out.println("<td>" + rs.getString("terrain") + "</td>");
				out.println("<td>" + rs.getString("pull_strength") + "</td>");
				out.println("<td>" + rs.getString("truck_storage") + "</td>");

				out.println("</tr>");
				out.println("</table>");
				
			}
				
				
				
				String isBike = "select * from auctions a natural join (select * from items i natural join bikes b" + 
						" where i.item_id = b.item_id) b where a.auction_id = '" + auctionID + "' and a.item_id = b.item_id";
				
				
					
				rs = st.executeQuery(isBike);
				
				
				if(rs.next()) {
					out.println("<table>\n"
							+ "			<tr>\n"
							+ "				<td><b>Name</b></td>\n"
							+ "				<td><b>Condition</b></td>\n"
							+ "				<td><b>Make</b></td>\n"
							+ "				<td><b>Model</b></td>\n"
							+ "				<td><b>Color</b></td>\n"
							+ "				<td><b>Current Price</b></td>\n"
							+ "				<td><b>Design</b>\n"
							+ "				<td><b>Year</b></td>\n"
							+ "				<td><b>Mileage</b></td>\n"
							+ "				<td><b>Accident Free?</b></td>\n"
		
							+ "				<td><b>Height</b></td>\n"
							+ "				<td><b>Transmission Type</b></td>\n"
							+ "				<td><b>Back Support</b></td>\n"
							+ "				<td><b>Weight</b></td>\n"
							+ "			</tr>");
				
				
				
				out.println("<tr>");
				out.println("<td>" + rs.getString("name") + "</td>");
				
				
				if(rs.getString("item_condition").equals("brandnew")) {
					out.println("<td> Brand New </td>");
				}
				else if(rs.getString("item_condition").equals("good")){
					out.println("<td> Good </td>");
				}
				else {
					out.println("<td> Fair </td>");
				}
				
				out.println("<td>" + rs.getString("make") + "</td>");
				out.println("<td>" + rs.getString("model") + "</td>");
				out.println("<td>" + rs.getString("color") + "</td>");
				out.println("<td>" + rs.getString("current_price") + "</td>");
				out.println("<td>" + rs.getString("design") + "</td>");
				out.println("<td>" + rs.getString("vehicle_year") + "</td>");
				out.println("<td>" + rs.getString("mileage") + "</td>");
				out.println("<td>" + rs.getString("accident") + "</td>");
				out.println("<td>" + rs.getString("height") + "</td>");
				out.println("<td>" + rs.getString("transmission") + "</td>");
				out.println("<td>" + rs.getString("back_support") + "</td>");
				out.println("<td>" + rs.getString("weight") + "</td>");

				out.println("</tr>");
				out.println("</table>");
				
				
				}
				
				
		out.println("<h2>Normal bid</h2>\n"
				+ "		<p>This will submit a normal bid. You will be alerted if you are outbid.</p>\n"
				+ "		<form action=\"regBidServlet\" method=\"POST\">\n"
				+ "			<input type=\"number\" step=\"0.01\" name=\"bidAmount\"><br>\n"
				+ "			<input type=\"hidden\" name=\"auctionID\" value=\"" +  auctionID  + "\">\n"
				+ "			<input type=\"submit\" value=\"Submit Bid\">\n"
				+ "		</form>\n"
				+ "		<h2>Autobid</h2>\n"
				+ "		<form action=\"autoBidServlet\" method=\"POST\">\n"
				+ "			<p>This field determines how much your bids will increment by</p>\n"
				+ "			Bid increment: <input type=\"number\" step=\"0.01\" name=\"bidIncrement\"><br>\n"
				+ "			<p>This field determines the maximum price you are willing to pay </p>\n"
				+ "			Maximum price: <input type=\"number\" step=\"0.01\" name=\"maxPrice\"><br>\n"
				+ "			<input type=\"hidden\" name=\"auctionID\" value=\"" +  auctionID  + "\">\n"
				+ "			<input type=\"submit\" value=\"Create Autobid\">\n"
				+ "		</form>\n"
				+ "		<a href=\"End/endMain.jsp\">Return to the main page</a>\n"
				+ "	</div>");		
			
			
		}					

		
		
	
		
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
