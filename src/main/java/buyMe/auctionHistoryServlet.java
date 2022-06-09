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
 * Servlet implementation class auctionHistoryServlet
 */
@WebServlet("/auctionHistoryServlet")
public class auctionHistoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public auctionHistoryServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "    <title>Past Auctions</title>\n"
				+ "    <style>\n"
				+ "		table {\n"
				+ "			border: 1px solid black;\n"
				+ "			border-collapse: collapse;	\n"
				+ "			width: 100%\n"
				+ "		}\n"
				+ "		th, td {\n"
				+ "			text-align: left;\n"
				+ "			padding: 15px;\n"
				+ "		}	\n"
				+ "		tr:nth-child(even) {\n"
				+ "			background-color: #f2f2f2;\n"
				+ "		}\n"
				+ "		body{background: #FFFAF0;}\n"
				+ "	</style>\n"
				+ "</head>\n"
				+ "<body>");
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
	        Statement st=con.createStatement();
	        int item_id = Integer.parseInt(request.getParameter("itemID"));
	        
	        ResultSet rs = st.executeQuery("select auction_id, buyer, seller, final_price, end_date from items natural join "
	            	+ "(auctions natural join sold) where end_date<now() and end_date>DATE_SUB(NOW(), INTERVAL 1 MONTH) and item_id= " + item_id);
	        
	        
	        
	        out.println(" <h1>Auction History for This Item</h1>\n"
	        		+ "        	  <table>\n"
	        		+ "                  <tr>\n"
	        		+ "                     <th>Auction ID</th>\n"
	        		+ "                        <th>Winner</th>\n"
	        		+ "                             <th>Seller</th>\n"
	        		+ "                       <th>Final Price</th>\n"
	        		+ "                           <th>End Date</th> \n"
	        		+ "                  </tr>");
	        
	        
	        
	        while(rs.next()) {
	        	out.println("<tr>");
	        	out.println("<td>" + rs.getString("auction_id") + "</td>");
	        	out.println("<td>" + rs.getString("buyer") + "</td>");
	        	out.println("<td>" + rs.getString("seller") + "</td>");
	        	out.println("<td>" + rs.getString("final_price") + "</td>");
	        	out.println("<td>" + rs.getString("end_date") + "</td>");
	        	out.println("</tr>");
	        	
	        	

	        }
	        
	        out.println("</tbody>");
        	out.println("</table><br>");
        	
        	
        	out.println(" <a href=\"End/endMain.jsp\">Go Back to BuyMe Main Page</a>");
	        
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	

}
