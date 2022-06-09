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
 * Servlet implementation class fetchReportServlet
 */
@WebServlet("/fetchReportServlet")
public class fetchReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fetchReportServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			PrintWriter out = response.getWriter();	
			
			out.println("<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "	<head>\n"
					+ "    	<title>Sales Report</title>\n"
					+ "    <style>\n"
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
					+ "	</style>\n"
					+ "	</head>\n"
					+ "	<body>");
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String dataType= request.getParameter("option_type");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
		        Statement st=con.createStatement();
		        ResultSet rs;
		        
		        
		        if(dataType.equals("total_earnings")) {
		        	 rs=st.executeQuery("select sum(s.final_price) as total from sold s where buyer <> ''");
		        	 
		        	 out.println(" <table>\n"
		        	 		+ "                 <tr>\n"
		        	 		+ "                    <th>Total Earnings</th>\n"
		        	 		+ "                 </tr>");
		        	 
		        	 while(rs.next()) {
		        		 out.println("<tr>");
		        		 out.println("<td>" + rs.getString("total") + "</td>");
		        		 out.println("</tr>");
		        		 
		        		 out.println(" </table><br>");
		   
		        		 
		        	 }
		        }
		        
		        else if(dataType.equals("best_items")) {
		        	 rs=st.executeQuery("select a.item_id, sum(s.final_price) as total from auctions a natural join sold s where s.buyer <> '' group by a.item_id order by total desc");
		        	 
		        	 out.println("<table border=1 style=\"text-align:center\">");
		        	 
		        	 out.println("<tr>\n"
		        	 		+ "                    <th>Item ID</th>\n"
		        	 		+ "                    <th>Earnings for Item</th>\n"
		        	 		+ "                 </tr>");
		        	 
		        	 
		        	 
		        	 while(rs.next()) {
		        		 out.println("<tr>\n"
		        		 		+ "                       <td>" + rs.getString("a.item_id") + "</td>\n"
		        		 		+ "                        <td>" + rs.getString("total") + "</td>\n"
		        		 		+ "                   </tr>");
		        		 out.println("</table><br>");
		        	 }
		        }
		        
		        else if(dataType.equals("best_sellers")) {
		        	 rs=st.executeQuery("select a.seller, sum(a.current_price) as total from auctions a natural join sold s where buyer <> '' group by seller order by total desc;");
		        	 
		        	 out.println("<table border=1 style=\"text-align:center\">");
		        	 
		        	 out.println("<tr>\n"
		        	 		+ "                    <th>Seller Username</th>\n"
		        	 		+ "                    <th>Earnings Made By User</th>\n"
		        	 		+ "                 </tr>");
		        	 
		        	 
		        	 
		        	 while(rs.next()) {
		        		 out.println("<tr>\n"
		        		 		+ "                       <td>" + rs.getString("a.seller") + "</td>\n"
		        		 		+ "                        <td>" + rs.getString("total") + "</td>\n"
		        		 		+ "                   </tr>");
		        		 out.println("</table><br>");
		        	 }
		        }
		        
		        else {
		        	
		        }
		        
		        out.println("<a href='Admin/salesReport/genSalesReport.jsp'>Go back to Generate Sales Report Page</a>");
		        out.println("</body>");
				
				
			}
			catch(Exception e) {
				e.printStackTrace();
			}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
	}

}
