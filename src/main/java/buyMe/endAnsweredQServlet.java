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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class endAnsweredQServlet
 */
@WebServlet("/endAnsweredQServlet")
public class endAnsweredQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public endAnsweredQServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		
		out.println("\n"
				+ "<!DOCTYPE html PUBLIC>\n"
				+ "<html>\n"
				+ "	<head>\n"
				+ "		<title>Unanswered Questions</title>\n"
				+ "		<style>\n"
				+ "			table {\n"
				+ "				border: 1px solid black;\n"
				+ "				border-collapse: collapse;	\n"
				+ "				width: 100%\n"
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
				+ " 		body{background: #FFFAF0;}\n"
				+ "		</style>\n"
				+ "	</head>\n"
				+ "	<body>");
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BuyMe","root", "F-ali67890");
			
			String username = (String)session.getAttribute("user");
			String query = "SELECT * FROM Questions WHERE cust_user = ? and answer IS NULL";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			
			
			
			out.println("<table>");
			out.println("<tr>");
			out.println("<td><b>Question</b></td>\n"
					+ "				<td><b>Answer</b></td>");
			
			out.println("</tr>");
			
			while(rs.next()) {
				out.println("<td>" + rs.getString("question") + "</td>");
				out.println("<td>" + rs.getString("answer") + "</td>");
			}
			
			
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		out.println("<a href='End/CustomerService/endCustomerService.jsp'>Go back to Customer Service Page</a>");
		out.println("</body>");
		out.println("</html>");
		
	}


}
