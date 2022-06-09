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
 * Servlet implementation class sellerReportServlet
 */
@WebServlet("/sellerReportServlet")
public class sellerReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public sellerReportServlet() {
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
			String seller = request.getParameter("seller");
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyme","root", "F-ali67890");
			PreparedStatement st=con.prepareStatement("select item_id from items where uname=?", ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE);
			ResultSet rs;
			st.setString(1, seller);
			rs = st.executeQuery();
			
			out.println("<!DOCTYPE html>\n"
					+ "<html>\n"
					+ "<head>\n"
					+ "	<title>Sales Report by Seller</title>\n"
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
			
			if(!rs.first()) {
				out.println("<a href='Admin/salesReport/genSalesReport.jsp'>User does not exist in database</a>\n");		
			}
			
			else {
				PreparedStatement st2 = con.prepareStatement("select max(final_price) as total from sold where seller=? and buyer <> ''", ResultSet.TYPE_SCROLL_SENSITIVE, 
	                    ResultSet.CONCUR_UPDATABLE);
				ResultSet rs2;
				st2.setString(1, seller);
				rs2 = st2.executeQuery();
				
				rs2.first();
				
				out.println(" <table>\n"
						+ "	             <tr>\n"
						+ "	                <th>Total Earnings For User"  +  seller + "</th>\n"
						+ "	             </tr>\n"
						+ "	               <tr>\n"
						+ "	                   <td>" + rs.getString("total") + "</td>\n"
						+ "	               </tr>\n"
						+ "	              </tbody>\n"
						+ "	           </table><br>");
				
				
				
			}
			
			out.println("<a href='Admin/salesReport/genSalesReport.jsp'>Go back to Admin main page</a>\n");		
			
			
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
