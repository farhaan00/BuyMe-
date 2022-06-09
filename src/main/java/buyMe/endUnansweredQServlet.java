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
 * Servlet implementation class endUnansweredQServlet
 */
@WebServlet("/endUnansweredQServlet")
public class endUnansweredQServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public endUnansweredQServlet() {
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
				+ "<!DOCTYPE html >\n"
				+ "<html>\n"
				+ "	<head>\n"
				+ "		<title>Unanswered Questions</title>\n"
				+ "	</head>\n"
				+ "	<body>");
		
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			String username = (String)session.getAttribute("user");
			String query = "SELECT * FROM Questions WHERE cust_user = ? and answer IS NULL";
			PreparedStatement ps = con.prepareStatement(query);
			ps.setString(1, username);

			ResultSet rs = ps.executeQuery();
			
			
			out.println("<ul>");
			
			while(rs.next()) {
				out.println("<li>" + rs.getString("question") + "</li>");
				
			}
			
			

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		out.println("<a href='End/CustomerService/endCustomerService.jsp'>Go back to Customer Service Page</a>");
		
		
		out.println("</body>");
		out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
