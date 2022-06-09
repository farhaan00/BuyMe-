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
 * Servlet implementation class typeReportServlet
 */
@WebServlet("/typeReportServlet")
public class typeReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public typeReportServlet() {
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
		
		try {
			String type = request.getParameter("type");
			String dataType = request.getParameter("data_type");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyme","root", "F-ali67890");
			Statement st = con.createStatement(); //=con.prepareStatement("select item_id from items where item_id=?", ResultSet.TYPE_SCROLL_SENSITIVE, 
                    //ResultSet.CONCUR_UPDATABLE);
			ResultSet rs;
			
			
			out.println("<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "<head>\n"
					+ "	<title>Sales Report by Item Type</title>\n"
					+ "	<style>\n"
					+ "		table {\n"
					+ "			border: 1px solid black;\n"
					+ "			border-collapse: collapse;	\n"
					+ "			width: 50%\n"
					+ "		}\n"
					+ "		table.center {\n"
					+ "				margin-left: auto; \n"
					+ "				margin-right: auto;\n"
					+ "		}\n"
					+ "		th, td {\n"
					+ "			text-align: left;\n"
					+ "			padding: 15px;\n"
					+ "		}	\n"
					+ "		tr:nth-child(even) {\n"
					+ "			background-color: #f2f2f2;\n"
					+ "		}\n"
					+ "	</style>\n"
					+ "</head>\n"
					+ "<body>");
			
			if(type==null){
				out.println("<h2> Please Choose an Item Type</h2>");
			}
			
			else if(type.equals("sedans")) {
				 rs=st.executeQuery("select sum(ss.final_price) as total from sold ss, sedans s, auctions a where buyer <> '' and ss.auction_id = a.auction_id and a.item_id = s.item_id");
				 
				 
				 out.println("<table>");
				 out.println("  <tr>\n"
				 		+ "            <th>Total Earnings For Sedans</th>\n"
				 		+ "         </tr>");
				 
				 while(rs.next()) {
					 out.println(" <tr>");
					 out.println("<td>" + rs.getString("total") + "</td>");
					 out.println("</tr>");
					 
					 
				 }
				 out.println("</table><br>");
				 
			}
			else if(type.equals("bikes")) {
				 rs=st.executeQuery("select sum(ss.final_price) as total from sold ss, bikes s, auctions a where buyer <> '' and ss.auction_id = a.auction_id and a.item_id = s.item_id");
				 
				 
				 out.println("<table>");
				 out.println("  <tr>\n"
				 		+ "            <th>Total Earnings For Bikes</th>\n"
				 		+ "         </tr>");
				 
				 while(rs.next()) {
					 out.println(" <tr>");
					 out.println("<td>" + rs.getString("total") + "</td>");
					 out.println("</tr>");
					 
					 
				 }
				 out.println("</table><br>");
				 
			}
			
			else {
				rs=st.executeQuery("select sum(ss.final_price) as total from sold ss, trucks s, auctions a where buyer <> '' and ss.auction_id = a.auction_id and a.item_id = s.item_id");
				 
				 
				 out.println("<table>");
				 out.println("  <tr>\n"
				 		+ "            <th>Total Earnings For Trucks</th>\n"
				 		+ "         </tr>");
				 
				 while(rs.next()) {
					 out.println(" <tr>");
					 out.println("<td>" + rs.getString("total") + "</td>");
					 out.println("</tr>");
					 
					 
				 }
				 out.println("</table><br>");
			}
			
			out.println("<a href='Admin/salesReport/genSalesReport.jsp'>Go back to Generate Sales Report Page</a>");
			
			
	}
		catch(Exception e) {
			e.printStackTrace();
		}

	}
}
