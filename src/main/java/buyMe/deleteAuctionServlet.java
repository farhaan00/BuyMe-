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

/**
 * Servlet implementation class deleteAuctionServlet
 */
@WebServlet("/deleteAuctionServlet")
public class deleteAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteAuctionServlet() {
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
		
		String auctionID = request.getParameter("auction_id");
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
		    
		    Statement st = con.createStatement();
		    ResultSet rs;
		    rs = st.executeQuery("select * from auctions where auction_id='" + auctionID + "'");
		    
		    
		    if(!rs.next()) {
		    	out.println("<!DOCTYPE html>");

		 		out.println("<html>");
		 		out.println("<head>");
		 		out.println("Auction does not exist <div><a href='End/Rep/RepFunctions/deleteAuction.jsp'>Try again</a></div>");
		 		out.println("</html>");
		 		out.println("</head>");
		    }
		    
		    else {
		    	PreparedStatement ps = con.prepareStatement("DELETE FROM auctions WHERE  auction_id='"+auctionID+"'");
		    	ps.executeUpdate();
		    	out.println("<html>");
		 		out.println("<head>");
				out.print("Deletion of Auction " +"'"+auctionID + "'" +" was successful");
		 		out.println("</html>");
		 		out.println("</head>");
		    	
		    }
		    

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		out.println("<!DOCTYPE html>");

 		out.println("<html>");
 		out.println("<head>");
 		out.println("		<a href='End/Rep/RepFunctions/deleteAuction.jsp'>Go back to Auction Removal Page</a><%\n");
 		out.println("</html>");
 		out.println("</head>");
		
		
	}

}
