package buyMe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class regBidServlet
 */
@WebServlet("/regBidServlet")
public class regBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public regBidServlet() {
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

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String bidder = (String)session.getAttribute("user");
		String bidAmount = request.getParameter("bidAmount");
		float amountFloat = Float.parseFloat(bidAmount);
		String thisAuctionString = request.getParameter("auctionID");
		int thisAuction = Integer.parseInt(thisAuctionString);
		
		
		
		
		
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			ResultSet rs;
			
			String params = "select seller, name, current_price, now() < end_date goodTime from auctions natural join items where auction_id = " + thisAuction;
			rs = st.executeQuery(params);
			rs.next();
			
			String seller = rs.getString("name");
			String itemName = rs.getString("name");
			float currentPrice = Float.parseFloat(rs.getString("current_price"));
			boolean goodTime = rs.getBoolean("goodTime");
			
			if (seller.equals((String)session.getAttribute("user"))) {
				out.println("Error: You cannot bid on your own auction. <div><a href='End/BrowseAuctions/searchItemType.jsp'>Return to the search page.</a>");
				
			// Check if the auction has expired while the user was browsing
			} else if (!goodTime) {
				out.println("Error: this auction has ended. <div><a href='End/endMain.jsp'>Return to the main page.</a></div>");
				
			// If the autobid max price is not larger than the current price of the auction, reject the bid
			} else if (amountFloat <= currentPrice) {
				out.println("Your maximum price must be larger than the current price of the auction. <div><a href='bidOnItemServlet?auctionID=" +
					String.valueOf(thisAuction) + "'Try again.</a></div>");
			}
			else {
				String addBid = "insert into bidOn values(?, ?, now(), ?)";
				
				PreparedStatement ps = con.prepareStatement(addBid);
				ps.setString(1, bidder);
				ps.setInt(2, thisAuction);
				ps.setFloat(3, amountFloat);
				ps.executeUpdate();
				
				String updatePrice = "update auctions set current_price = ? where auction_id = ?";
				ps = con.prepareStatement(updatePrice);
				ps.setFloat(1, amountFloat);
				ps.setInt(2, thisAuction);
				ps.executeUpdate();
				
				currentPrice = amountFloat;
				
				out.println("Your bid has been successfully placed. <a href='End/endMain.jsp'>Return to the main page.</a>");	
				
			}
			
			rs = st.executeQuery("select count(uname) autoBidders from autoBid b, auctions a where b.active_status = true and a.auction_id = " + thisAuction + " and a.auction_id = b.auction_id");
			rs.next();
			
			
			if(rs.getInt("autoBidders") == 1) {
				rs = st.executeQuery("select name, bid_interval, highest_price, uname "
						+ "from autoBid natural join auctions natural join items where auction_id = " + thisAuction);
					rs.next();
					
					
					float incCheck = rs.getFloat("bid_interval");
					float maxPrice = rs.getFloat("highest_price");
					String autoBidder = rs.getString("uname");
					itemName = rs.getString("name");
					
					

					if (currentPrice + incCheck <= maxPrice) {
						st.executeUpdate("insert into bidOn values('" + autoBidder + "', " + thisAuction + ", now(), " + (incCheck + currentPrice) + ")");
						st.executeUpdate("update auctions set current_price = " + (currentPrice + incCheck) + " where auction_id = " + thisAuction);
					} else if (currentPrice < maxPrice) {
						st.executeUpdate("insert into bidOn values('" + autoBidder + "', " + thisAuction + ", now(), " + maxPrice + ")");
						st.executeUpdate("update auctions set current_price = " + maxPrice + " where auction_id = " + thisAuction);
					} else {
						
						// Alert the autobidder that someone has bid higher than their max bid
						st.executeUpdate("insert into alerts values('" + autoBidder + "', 'Someone has bid higher than your maximum on " + itemName + "!', 'outbid', now())");
						
						// Change the active status of the autobidder
						st.executeUpdate("update autoBid set active_status = false where username = '" + autoBidder + "' and auction_id = " + thisAuction);
					}
					
					
					
			}
			
			else if(rs.getInt("autoBidders") > 1) {
				String secondHighest = "select username, highest_price from autoBid where auction_id = "
						+ thisAuction + " and highest_price = (select max(highest_price) from autoBid where auction_id = "
						+ thisAuction + " and highest_price < (select max(highest_price) from autoBid where auction_id = " + thisAuction + "))";
					rs = st.executeQuery(secondHighest);
					rs.next();
					String secondHighestUsername = rs.getString("username");
					float secondHighestMaxPrice = rs.getFloat("highest_price");
					
					// Insert the second highest autobidder's bid
					st.executeUpdate("insert into bidOn values('" + secondHighestUsername + "', " + thisAuction + ", now(), " + secondHighestMaxPrice + ")");
					
					
					String highest = "select uname, highest_price, bid_interval from autoBid where auction_id = " + thisAuction + " and highest_price = (select max(highest_price) from autoBid)";
					rs = st.executeQuery(highest);
					rs.next();
					String highestAutoBidder = rs.getString("uname");
					float thisMax, thisIncrement;
					thisMax = rs.getFloat("highest_price");
					thisIncrement = rs.getFloat("bid_interval");
					
					if (secondHighestMaxPrice + thisIncrement <= thisMax) {
						st.executeUpdate("insert into bidOn values('" + highestAutoBidder + "', " + thisAuction + ", now(), " + (secondHighestMaxPrice + thisIncrement) + ")");
						st.executeUpdate("update auctions set current_price = " + (secondHighestMaxPrice + thisIncrement) + " where auction_id = " + thisAuction);
					} else {
						st.executeUpdate("insert into bidOn values('" + highestAutoBidder + "', " + thisAuction + ", now(), " + thisMax + ")");
						st.executeUpdate("update auctions set current_price = " + thisMax + " where auction_id = " + thisAuction);
					}
					
					// Alert the second highest bidder that he has been outbid
					st.executeUpdate("insert into alerts values('" + secondHighestUsername + "', 'Someone has bid higher than your maximum on " + itemName + "!', 'outbid', now())");
					
					// Change the active status of the second highest autobidder
					st.executeUpdate("update autoBid set active_status = false where auction_id = " + thisAuction + " and uname = " + secondHighestUsername);
					
			}
			
			String outBidUser, outBidQuery;
			outBidQuery = "select distinct b.uname, i.name from bidOn b, auctions a, items i where a.item_id = i.item_id and a.auction_id = b.auction_id and b.auction_id = "
				+ thisAuction + " and b.uname not in (select uname from bidOn where amount = (select max(amount) from bidOn where auction_id = "
				+ thisAuction + ")) and b.uname not in (select uname from autoBid where auction_id = " + thisAuction + ")";
			rs = st.executeQuery(outBidQuery);

			//Make an alert for all of the users that have been outbid
			ArrayList<String> bidders = new ArrayList<String>();
			ArrayList<String> items = new ArrayList<String>();
			while (rs.next()) {
				outBidUser = rs.getString("uname");
				itemName = rs.getString("name");
				bidders.add(outBidUser);
				items.add(itemName);
			}

			for (int i = 0; i < bidders.size(); i++) {
				st.executeUpdate("update autoBid set active_status = false where uname = '" + bidders.get(i) + "' and auction_id = " + thisAuction);
				String outBidString = "insert into alerts values('" + bidders.get(i) + "', 'You have been outbid on " + items.get(i) + "!', 'outbid', now())";
				st.executeUpdate(outBidString);
			}
			
			
			String inactive = "delete from autoBid where active_status = false";
			st.executeUpdate(inactive);

			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
	}

}
