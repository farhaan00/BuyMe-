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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class browseQuestionsServlet
 */
@WebServlet("/browseQuestionsServlet")
public class browseQuestionsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public browseQuestionsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		out.println("\n"
				+ "<!DOCTYPE html PUBLIC >\n"
				+ "<html>\n"
				+ "	<head>\n"
				+ "		<title>Customer Service</title>\n"
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
				+ "body{background: #FFFAF0;}\n"
				+ "		</style>\n"
				+ "	</head>\n"
				+ "	<body>");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BuyMe","root", "F-ali67890");
			
			Statement sm = con.createStatement();
			
			ResultSet rs = sm.executeQuery("Select * from Questions");
			
			
			
			out.println("<h1>Customer Service</h1>");
			out.println("<form action=\"searchQuestionsServlet\" method=\"POST\">");
			out.println("Search Query: <input type=\"text\" maxlength=\"200\" name=\"term\"/><br/><br/>");
			out.println("<input type=\"submit\" value=\"Submit\"/>\n"
					+ "			</form>");
			
			
			
			out.println("<h4>All Questions</h4>\n"
					+ "		\n"
					+ "		<table>\n"
					+ "		\n"
					+ "			<tr>\n"
					+ "				<td><b>Question</b></td>\n"
					+ "				<td><b>Answer</b></td>\n"
					+ "			</tr>");
			
			
			while(rs.next()) {
				String answer = (String) rs.getString("answer");
				
				out.println("<tr>");
				out.println("<td>" + rs.getString("question") + "</td>");
				
				out.println("<td>");
				
				if(answer == null) {
					
				}
				else {
					out.println(answer);
				}
				
				out.println("</td>");
				
				out.println("</tr>");
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
