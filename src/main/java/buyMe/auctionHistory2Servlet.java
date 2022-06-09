package buyMe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class auctionHistory2Servlet
 */
@WebServlet("/auctionHistory2Servlet")
public class auctionHistory2Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public auctionHistory2Servlet() {
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
		
		out.println("<!DOCTYPE html >\n"
				+ "<html>\n"
				+ "	<head>\n"
				+ "		<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n"
				+ "		<title>Auction Bid History's</title>\n"
				+ "		<style>\n"
				+ "			table {\n"
				+ "				border: 1px solid black;\n"
				+ "				border-collapse: collapse;	\n"
				+ "				width: 50%\n"
				+ "			}\n"
				+ "			table.center {\n"
				+ "  				margin-left: auto; \n"
				+ "  				margin-right: auto;\n"
				+ "			}\n"
				+ "			th, td {\n"
				+ "				text-align: left;\n"
				+ "				padding: 15px;\n"
				+ "			}	\n"
				+ "			tr:nth-child(even) {\n"
				+ "				background-color: #f2f2f2;\n"
				+ "			}\n"
				+ "			body{background: #FFFAF0;}"
				+ "		</style>\n"
				+ "	</head>\n"
				+ "	<body>");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");

			String term = request.getParameter("term");
			String query = "SELECT * FROM bidOn WHERE auction_id = ?";
			
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, term);
			
			ResultSet rs = ps.executeQuery();
			
			
			out.println("<h1>Search Results</h1>\n"
					+ "		<a href=\"End/AuctionBidHistory/searchAuctionHistory.jsp\">New search</a>\n"
					+ "		<br/>");
			
			out.println("<p>Search Query: " + term + "</p>");
			out.println("<table>\n"
					+ "		\n"
					+ "			<tr>\n"
					+ "				<td><b>User</b></td>\n"
					+ "				<td><b>Bid Amount</b></td>\n"
					+ "				<td><b>Date</b></td>\n"
					+ "			</tr>");
			
			
			while(rs.next()) {
				out.println("<tr>");
				out.println("<td>" + rs.getString("uname") + "</td>");
				out.println("<td>" + rs.getString("amount") + "</td>");
				out.println("<td>" + rs.getString("date") + "</td>");
				out.println("</tr>");
			}
			
			con.close();
			
			out.println("</table>");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		out.println("</body>\n"
				+ "</html>");
		
	}

}
