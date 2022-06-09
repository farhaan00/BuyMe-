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
 * Servlet implementation class autoServlet
 */
@WebServlet("/autoBidServlet")
public class autoBidServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public autoBidServlet() {
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
		float increment = Float.parseFloat(request.getParameter("bidIncrement"));
		float maxPrice = Float.parseFloat(request.getParameter("maxPrice"));
		int thisAuction = Integer.parseInt(request.getParameter("auctionID"));

		
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			ResultSet rs;
			
			
			String params = "select seller, name, current_price, now() < end_date validTime from auctions natural join items where auction_id = " + thisAuction;
			rs = st.executeQuery(params);
			rs.next();
			
			
			
			String seller = rs.getString("seller");
			String itemName = rs.getString("name");
			float currentPrice = Float.parseFloat(rs.getString("current_price"));
			boolean validTime = rs.getBoolean("validTime");

			
			if (seller.equals((String)session.getAttribute("user"))) {
				out.println("Error: You cannot bid on your own auction. <div><a href='End/BrowseAuctions/searchItemType.jsp'>Return to the search page.</a>");
				
			
			} else if (!validTime) {
				out.println("Error: this auction has ended. <div><a href='End/endMain.jsp'>Return to the main page.</a></div>");
				
			} else if (maxPrice <= currentPrice) {
				out.println("Your maximum price must be larger than the current price of the auction. <div><a href='bidOnItemServlet?auctionID=" +
					String.valueOf(thisAuction) + "'Try again.</a></div>");
			}
			
			else {
				
				String addAutobid = "insert into auto values(?, ?, true, ?, ?)";
				PreparedStatement ps = con.prepareStatement(addAutobid);
				ps.setString(1, bidder);
				ps.setInt(2, thisAuction);
				ps.setFloat(3, maxPrice);
				ps.setFloat(4, increment);
				ps.executeUpdate();
				
				rs = st.executeQuery("select count(uname) autoBidders from auto b, auctions a where b.active_status = true and a.auction_id = " + thisAuction + " and a.auction_id = b.auction_id");
				rs.next();

				if (rs.getInt("autoBidders") == 1) {
					
					if (increment + currentPrice <= maxPrice) {
						String updatePrice = "update auctions set current_price = ? where auction_id = ?";
						ps = con.prepareStatement(updatePrice);
						ps.setFloat(1, currentPrice + increment);
						ps.setInt(2, thisAuction);
						ps.executeUpdate();
						
						String addBid = "insert into bidOn values(?, ?, now(), ?)";
						ps = con.prepareStatement(addBid);
						ps.setString(1, bidder);
						ps.setInt(2, thisAuction);
						ps.setFloat(3, currentPrice + increment);
						ps.executeUpdate();
						
						currentPrice += increment;
					} else {
						String updatePrice = "update auctions set current_price = ? where auction_id = ?";
						ps = con.prepareStatement(updatePrice);
						ps.setFloat(1, maxPrice);
						ps.setInt(2, thisAuction);
						ps.executeUpdate();
						
						String addBid = "insert into bidOn values(?, ?, now(), ?)";
						ps = con.prepareStatement(addBid);
						ps.setString(1, bidder);
						ps.setInt(2, thisAuction);
						ps.setFloat(3, currentPrice + increment);
						ps.executeUpdate();
						
						currentPrice = maxPrice;
					}
					
				} else {
					// Get the second highest autobidder
					String secondHighest = "select uname, highest_price from auto where auction_id = "
						+ thisAuction + " and highest_price = (select max(highest_price) from auto where auction_id = "
						+ thisAuction + " and highest_price < (select max(highest_price) from auto where auction_id = " + thisAuction + "))";
					rs = st.executeQuery(secondHighest);
					rs.next();
					String secondHighestuname = rs.getString("uname");
					float sHighestMaxPrice = rs.getFloat("highest_price");
					
					// Insert the second highest autobidder's bid
					st.executeUpdate("insert into bidOn values('" + secondHighestuname + "', " + thisAuction + ", now(), " + sHighestMaxPrice + ")");
					
					
					// Get the highest autobidder
					String highest = "select uname, highest_price, bid_interval from auto where auction_id = " + thisAuction + " and highest_price = (select max(highest_price) from auto)";
					rs = st.executeQuery(highest);
					rs.next();
					String highestAutoBidder = rs.getString("uname");
					float thisMax, thisIncrement;
					thisMax = rs.getFloat("highest_price");
					thisIncrement = rs.getFloat("bid_interval");
					
					// Compute the proper bid of the highest autobidder
					if (sHighestMaxPrice + thisIncrement <= thisMax) {
						st.executeUpdate("insert into bidOn values('" + highestAutoBidder + "', " + thisAuction + ", now(), " + (sHighestMaxPrice + thisIncrement) + ")");
						st.executeUpdate("update auctions set current_price = " + (sHighestMaxPrice + thisIncrement) + " where auction_id = " + thisAuction);
					} else {
						st.executeUpdate("insert into bidOn values('" + highestAutoBidder + "', " + thisAuction + ", now(), " + thisMax + ")");
						st.executeUpdate("update auctions set current_price = " + thisMax + " where auction_id = " + thisAuction);
					}
					
					// Alert the second highest bidder that he has been outbid
					st.executeUpdate("insert into alerts values('" + secondHighestuname + "', 'Someone has bid higher than your maximum on " + itemName + "!', 'outbid', now())");
					
					// Change the active status of the second highest autobidder
					st.executeUpdate("update auto set active_status = false where auction_id = " + thisAuction + " and uname = '" + secondHighestuname + "'");
					
				}
				
				
				Statement newst = con.createStatement();
				ResultSet newRS = st.executeQuery("select uname, highest_price, bid_interval from auto where auction_id = "
					+ thisAuction + " and active_status = 1 and highest_price < (select max(highest_price) from auto)");
				String user;
				float highest;
				while (newRS.next()) {
					user = newRS.getString("uname");
					highest = newRS.getFloat("highest_price");
					newst.executeUpdate("insert into bidOn values('" + user + "', " + thisAuction + ", now(), " + highest + ")");
				}
				
				
				String outBidUser, outBidQuery;
				outBidQuery = "select distinct b.uname, i.name from bidOn b, auctions a, items i where a.item_id = i.item_id and a.auction_id = b.auction_id and b.auction_id = "
						+ thisAuction + " and b.uname not in (select uname from bidOn where amount = (select max(amount) from bidOn where auction_id = "
						+ thisAuction + ")) and b.uname not in (select uname from auto where auction_id = " + thisAuction + ")";
				rs = st.executeQuery(outBidQuery);

				
				ArrayList<String> bidders = new ArrayList<String>();
				ArrayList<String> items = new ArrayList<String>();
				while (rs.next()) {
					outBidUser = rs.getString("uname");
					itemName = rs.getString("name");
					bidders.add(outBidUser);
					items.add(itemName);
				}

				for (int i = 0; i < bidders.size(); i++) {
					st.executeUpdate("update auto set active_status = false where uname = '" + bidders.get(i) + "' and auction_id = " + thisAuction);
					String outBidString = "insert into alerts values('" + bidders.get(i) + "', 'You have been outbid on " + items.get(i) + "!', 'outbid', now())";
					st.executeUpdate(outBidString);
				}
				

				
				st.executeUpdate("delete from auto where active_status = false");
				
				
			}
		}
		
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
		out.println("Your bid has been successfully placed. <a href='End/endMain.jsp'>Return to the main page.</a>");

		
		
	

	}
}
