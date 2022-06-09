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
 * Servlet implementation class addBikeServlet
 */
@WebServlet("/addBikeServlet")
public class addBikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addBikeServlet() {
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

		String bikeName = request.getParameter("name");
		String bikeCondition = request.getParameter("condition");
		String bikeMake = request.getParameter("make");
		String bikeModel = request.getParameter("model");
		String bikeColor = request.getParameter("color");
		String bikeDesign = request.getParameter("design");
		String bikeYear = request.getParameter("year");
		String bikeMileage = request.getParameter("mileage");
		String endDate = request.getParameter("endDate");
		
		
		
		String seller = (String)session.getAttribute("user");
		
		String height = request.getParameter("height");		
		
		String accidentFree = request.getParameter("accident");
		boolean accident, support;
		
		if(accidentFree.equals("false")) {
			accident = false;
		}
		else {
			accident = true;
		}
		
		
		
		String backSupport = request.getParameter("support");
		
		if(backSupport.equals("false")) {
			support = false;
		}
		else {
			support = true;
		}
		
		String transmition = request.getParameter("transmition");
		String weight = request.getParameter("weight");
		String reservePrice = request.getParameter("reservePrice");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			Statement newst = con.createStatement();
			Statement newerst = con.createStatement();
			ResultSet rs;
			

			if (endDate.equals("")) {
				
			} else {
				String dateQuery = "select now() < ? properDate";
				PreparedStatement ps = con.prepareStatement(dateQuery);
				ps.setString(1, endDate);
				rs = ps.executeQuery();
				rs.next();
				Boolean properDate = rs.getBoolean("properDate");
				
				if (bikeName.equals("") || bikeCondition.equals("") || bikeMake.equals("") || bikeColor.equals("") || bikeDesign.equals("") || reservePrice.equals("")
					||	bikeModel.equals("") || bikeYear.equals("") || bikeMileage.equals("") || reservePrice.equals("0") || endDate.equals("") || accidentFree.equals("")) {
				
					out.println("<!DOCTYPE html>");

			 		out.println("<html>");
			 		out.println("<body>");
					
			 		//fix path
					out.println("One of your entry fields is empty, please try again. <div><a href='bikeListing.jsp'>Try again</a></div>");
					out.println("</body>");
					out.println("</html>");
				}
				else if(!properDate) {
					out.println("<!DOCTYPE html>");

			 		out.println("<html>");
			 		out.println("<body>");
					
			 		//fix path
					out.println("Auction end date is invalid, please try again. <div><a href='bikeListing.jsp'>Try again</a></div>");
					out.println("</body>");
					out.println("</html>");
					
				}
				else {
					// join clause
					String bikeQuery = "select * from items natural left join bikes where item_condition = ? and make = ? and model = ? and color = ? and design = ? and vehicle_year = ? and mileage = ? and accident = ? and height = ? and back_support = ? and transmission = ? and weight = ?";
					
					ps = con.prepareStatement(bikeQuery);
					ps.setString(1, bikeCondition);
					ps.setString(2, bikeMake);
					ps.setString(3, bikeModel);
					ps.setString(4, bikeColor);
					ps.setString(5, bikeDesign);
					ps.setString(6, bikeYear);
					ps.setString(7, bikeMileage);
					ps.setBoolean(8, accident);
					ps.setString(9, height);
					ps.setBoolean(10, support);
					ps.setString(11, transmition);
					ps.setString(12, weight);

					rs= ps.executeQuery();
					
					if(rs.next()) {
						int desiredId = rs.getInt("item_id");
						ResultSet desiredItemQuery = newst.executeQuery("select uname, name from wantsAndNeeds natural join items where item_id = " + desiredId);
						
						while(desiredItemQuery.next()) {
							newerst.executeUpdate("insert into alerts values('" + desiredItemQuery.getString("uname") + "', 'Your desired item, " + desiredItemQuery.getString("name") + " is now available for bidding!', 'item', now())");
							newerst.executeUpdate("delete from wantsAndNeeds where item_id = " + desiredId + ", '" + desiredItemQuery.getString("uname") + "'");

						}
						
						// Find the highest ID number from the auctions table and create a new ID that is larger than it
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
						addAuctionStatement.setInt(3, desiredId);
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
						// Find the highest ID number from the items table and create a new ID that is larger than it
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
						addItemStatement.setString(2, bikeName);
						addItemStatement.setString(3, bikeCondition);
						addItemStatement.setString(4, bikeMake);
						addItemStatement.setString(5, bikeModel);
						addItemStatement.setString(6, bikeColor);
						addItemStatement.setString(7, bikeDesign);
						addItemStatement.setString(8, bikeYear);
						addItemStatement.setString(9, bikeMileage);
						
						
						addItemStatement.executeUpdate();
						
						String addBikesQuery = "insert into bikes values(?, ?, ?, ?, ?, ?)";
						PreparedStatement addBikeState = con.prepareStatement(addBikesQuery);
						addBikeState.setInt(1, newItemID);
						addBikeState.setBoolean(2, accident);
						addBikeState.setString(3, height);
						addBikeState.setBoolean(4, support);
						addBikeState.setString(5, transmition);
						addBikeState.setString(6, weight);
						
						addBikeState.executeUpdate();

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
