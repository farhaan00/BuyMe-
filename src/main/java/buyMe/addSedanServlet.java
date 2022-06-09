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
 * Servlet implementation class addSedanServlet
 */
@WebServlet("/addSedanServlet")
public class addSedanServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public addSedanServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();

		String sedanName = request.getParameter("name");
		String sedanCondition = request.getParameter("condition");
		String sedanMake = request.getParameter("make");
		String sedanColor = request.getParameter("color");
		String sedanDesign = request.getParameter("design");
		String sedanYear = request.getParameter("year");
		String sedanMileage = request.getParameter("mileage");
		String endDate = request.getParameter("endDate");
		String sedanModel = request.getParameter("model");
		String sedanDoor = request.getParameter("noOfdoors");
		
		String seller = (String)session.getAttribute("user");
		
		
		
		/*True false things*/
		
		String accidentFree = request.getParameter("accident");
		boolean accident, start;
		
		if(accidentFree.equals("false")) {
			accident = false;
		}
		else {
			accident = true;
		}
		
		String trunkSize = request.getParameter("trunkSize");
		
		String pushToStart = request.getParameter("start");
		
		if(pushToStart.equals("false")) {
			start = false;
		}
		else {
			start = true;
		}
		
		String doors = request.getParameter("noOfdoors");
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
				
				if (sedanName.equals("") || sedanCondition.equals("") || sedanMake.equals("") || sedanColor.equals("") || sedanDesign.equals("") || reservePrice.equals("")
					||	sedanModel.equals("") || sedanYear.equals("") || sedanMileage.equals("") || reservePrice.equals("0") || endDate.equals("") || accidentFree.equals("") || trunkSize.equals("") || doors.equals("") || pushToStart.equals("")) {
				
					out.println("<!DOCTYPE html>");

			 		out.println("<html>");
			 		out.println("<body>");
					
			 		//fix path
					out.println("One of your entry fields is empty, please try again. <div><a href='sedanListing.jsp'>Try again</a></div>");
					out.println("</body>");
					out.println("</html>");
				}
				else if(!properDate) {
					out.println("<!DOCTYPE html>");

			 		out.println("<html>");
			 		out.println("<body>");
					
			 		//fix path
					out.println("Auction end date is invalid, please try again. <div><a href='sedanListing.jsp'>Try again</a></div>");
					out.println("</body>");
					out.println("</html>");
					
				}
				else {
					
					//create the query
					String selectSedan = "select * from items natural left join sedans where item_condition = ? and make = ? and model = ? and color = ? and design = ? and vehicle_year = ? and mileage = ? and accident = ? and trunk_size = ? and push_start = ? and doors = ?";
							
					
						ps = con.prepareStatement(selectSedan);
						
						ps.setString(1, sedanCondition);
						ps.setString(2, sedanMake);
						ps.setString(3, sedanModel);
						ps.setString(4, sedanColor);
						ps.setString(5, sedanDesign);
						ps.setString(6, sedanYear);
						ps.setString(7, sedanMileage);
						ps.setBoolean(8, accident);
						ps.setString(9, trunkSize);
						ps.setBoolean(10, start);
						ps.setString(11, sedanDoor);
						
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
							
							newItemID += 1;
							
							
							String addItemQuery = "insert into items values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
							PreparedStatement addItemStatement = con.prepareStatement(addItemQuery);
							addItemStatement.setInt(1, newItemID);
							addItemStatement.setString(2, sedanName);
							addItemStatement.setString(3, sedanCondition);
							addItemStatement.setString(4, sedanMake);
							addItemStatement.setString(5, sedanModel);
							addItemStatement.setString(6, sedanColor);
							addItemStatement.setString(7, sedanDesign);
							addItemStatement.setString(8, sedanYear);
							addItemStatement.setString(9, sedanMileage);
						
							addItemStatement.executeUpdate();
							
							String addSedanQuery = "insert into sedans values(?, ?, ?, ?, ?)";
							PreparedStatement addSedanStatement = con.prepareStatement(addSedanQuery);
							addSedanStatement.setInt(1, newItemID);
							addSedanStatement.setBoolean(2, accident);
							addSedanStatement.setString(3, trunkSize);
							addSedanStatement.setBoolean(4, start);
							addSedanStatement.setString(5, doors);
							
							addSedanStatement.executeUpdate();
							
							
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
