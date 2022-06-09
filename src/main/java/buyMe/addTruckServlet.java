package buyMe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class addTruckServlet
 */
@WebServlet("/addTruckServlet")
public class addTruckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addTruckServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String truckName = request.getParameter("name");
		String truckCondition = request.getParameter("condition");
		String truckMake = request.getParameter("make");
		String truckColor = request.getParameter("color");
		String truckDesign = request.getParameter("design");
		String truckYear = request.getParameter("year");
		String truckMileage = request.getParameter("mileage");
		String endDate = request.getParameter("endDate");
		String truckModel = request.getParameter("model");
		String truckTerrain = request.getParameter("terrain");
		String truckStorage = request.getParameter("storage");
		String truckStrength = request.getParameter("strength");
		
		String seller = (String)session.getAttribute("user");
		
		
		String accidentFree = request.getParameter("accident");
		boolean accident, terrain;
		String reservePrice = request.getParameter("reservePrice");

		
		if(accidentFree.equals("false")) {
			accident = false;
		}
		else {
			accident = true;
		}
		if(truckTerrain.equals("false")) {
			terrain = false;
		}
		else {
			terrain = true;
		}
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			Statement newst = con.createStatement();
			Statement newerst = con.createStatement();
			ResultSet rs;
			if (endDate.equals("")) {
				
			}
			else {
				String dateQuery = "select now() < ? properDate";
				PreparedStatement ps = con.prepareStatement(dateQuery);
				ps.setString(1, endDate);
				rs = ps.executeQuery();
				rs.next();
				Boolean properDate = rs.getBoolean("properDate");
				
				if (truckName.equals("") || truckCondition.equals("") || truckMake.equals("") || truckColor.equals("") || truckDesign.equals("") || reservePrice.equals("")
					||	truckModel.equals("") || truckYear.equals("") || truckMileage.equals("") || reservePrice.equals("0") || endDate.equals("") || accidentFree.equals("") || truckTerrain.equals("") || truckStorage.equals("") ) {
				
					out.println("<!DOCTYPE html>");

			 		out.println("<html>");
			 		out.println("<body>");
					
			 		//fix path
					out.println("One of your entry fields is empty, please try again. <div><a href='truckListing.jsp'>Try again</a></div>");
					out.println("</body>");
					out.println("</html>");
				}
				else if(!properDate) {
					out.println("<!DOCTYPE html>");

			 		out.println("<html>");
			 		out.println("<body>");
					
			 		//fix path
					out.println("Auction end date is invalid, please try again. <div><a href='truckListing.jsp'>Try again</a></div>");
					out.println("</body>");
					out.println("</html>");
					
				}
				else {
					String selecttruck = "select * from items natural left join trucks where item_condition = ? and make = ? and model = ? and color = ? and design = ? and vehicle_year = ? and mileage = ? and accident = ? and terrain = ? and pull_strength = ? and truck_storage = ?";
					ps = con.prepareStatement(selecttruck);
					
					ps.setString(1, truckCondition);
					ps.setString(2, truckMake);
					ps.setString(3, truckModel);
					ps.setString(4, truckColor);
					ps.setString(5, truckDesign);
					ps.setString(6, truckYear);
					ps.setString(7, truckMileage);
					ps.setBoolean(8, accident);
					ps.setBoolean(9, terrain);
					ps.setString(10, truckStrength);
					ps.setString(11, truckStorage);
					
					rs = ps.executeQuery();
					
					
					if(rs.next()) {
						// check if someone wants the item that is listed 
						int desiredID = rs.getInt("item_id");
						//need to implement
						ResultSet simAlert = newst.executeQuery("select uname, name from wantsAndNeeds natural join items where item_id = " + desiredID);

						while(simAlert.next()) {
							newerst.executeUpdate("insert into alerts values('" + simAlert.getString("uname") + "', 'Your desired item, " + simAlert.getString("name") + " is now available for bidding!', 'item', now())");
							newerst.executeUpdate("delete from wantsAndNeeds where item_id = " + desiredID + ", '" + simAlert.getString("uname") + "'");

						}
						ResultSet lastID = st.executeQuery("select max(auction_id) highest from auctions");
						lastID.next();
						Integer newAuctionID = lastID.getInt("highest");
						if (newAuctionID == null) {
							newAuctionID = 0;
						}
						newAuctionID += 1;
						
						String addAuctionQuery = "insert into auctions values(?, ?, ?, ?, 0, now(), convert(?, datetime))";
						PreparedStatement addAuctionStatement = con.prepareStatement(addAuctionQuery);
						addAuctionStatement.setString(1, seller);
						addAuctionStatement.setInt(2, newAuctionID);
						addAuctionStatement.setInt(3, desiredID);
						addAuctionStatement.setString(4, reservePrice);
						addAuctionStatement.setString(5, endDate);
						
						addAuctionStatement.executeUpdate();
						
						out.println("<!DOCTYPE html>");

				 		out.println("<html>");
				 		out.println("<body>");
						
				 		//fix path
						out.println("Your item has been listed. <a href='End/endMain.jsp'> Return to the main page.");
						out.println("</body>");
						out.println("</html>");
						
						
					}
					else {
						Integer newItemID, newAuctionID;
						ResultSet lastID = st.executeQuery("select max(item_id) highest from items");
						lastID.next();
						
						newItemID = lastID.getInt("highest");
						if (newItemID == null) {
							newItemID = 0;
						}
						newItemID += 1;
						
						
						String addItemQuery = "insert into items values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
						PreparedStatement addItemStatement = con.prepareStatement(addItemQuery);
						addItemStatement.setInt(1, newItemID);
						addItemStatement.setString(2, truckName);
						addItemStatement.setString(3, truckCondition);
						addItemStatement.setString(4, truckMake);
						addItemStatement.setString(5, truckModel);
						addItemStatement.setString(6, truckColor);
						addItemStatement.setString(7, truckDesign);
						addItemStatement.setString(8, truckYear);
						addItemStatement.setString(9, truckMileage);
					
						addItemStatement.executeUpdate();
						
						String addtruckQuery = "insert into trucks values(?, ?, ?, ?, ?)";
						PreparedStatement addtruckStatement = con.prepareStatement(addtruckQuery);
						addtruckStatement.setInt(1, newItemID);
						addtruckStatement.setBoolean(2, accident);
						addtruckStatement.setBoolean(3, terrain);
						addtruckStatement.setString(4, truckStrength);
						addtruckStatement.setString(5, truckStorage);

						
						addtruckStatement.executeUpdate();
						
						
						lastID = st.executeQuery("select max(auction_id) highest from auctions");
						lastID.next();
						newAuctionID = lastID.getInt("highest");
						if (newAuctionID == null) {
							newAuctionID = 0;
						}
						newAuctionID += 1;
						
						String addAuctionQuery = "insert into auctions values(?, ?, ?, convert(?, decimal(9,2)), 0, now(), convert(?, datetime))";
						PreparedStatement addAuctionStatement = con.prepareStatement(addAuctionQuery);
						addAuctionStatement.setString(1, seller);
						addAuctionStatement.setInt(2, newAuctionID);
						addAuctionStatement.setInt(3, newItemID);
						addAuctionStatement.setString(4, reservePrice);
						addAuctionStatement.setString(5, endDate);
						
						addAuctionStatement.executeUpdate();
						
						out.println("<!DOCTYPE html>");

				 		out.println("<html>");
				 		out.println("<body>");
						
				 		//fix path
						out.println("Your item has been listed. <a href='End/endMain.jsp'> Return to the main page.");
						out.println("</body>");
						out.println("</html>");
					
					}
					
				}
			
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
