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
 * Servlet implementation class answerQuestionServlet
 */
@WebServlet("/answerQuestionServlet")
public class answerQuestionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public answerQuestionServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BuyMe","root", "F-ali67890");
			
			Statement st = con.createStatement();
			
			ResultSet r;
			
			String question = request.getParameter("question");
			String answer = request.getParameter("answer");
			
			
			if(answer.equals("")) {
				out.println("<DOCTYPE html>");
				out.println("<html>");
				out.println("<body>");
				out.println("Your answer was blank. <div><a href='Rep/answerQuestions/repCustService.jsp'>Try again</a></div>");
				out.println("</html>");
				out.println("</body>");
			}
			
			else {
				String rep_user = (String) session.getAttribute("user");
				
				String q = "update Questions set rep_user = ?, answer = ? where question = ?";
				PreparedStatement p = con.prepareStatement("update Questions set rep_user = ?, answer = ? where question = ?");
				
				p.setString(1, rep_user);
				p.setString(2, answer);
				p.setString(3, question);
				
				p.executeUpdate();
				
				con.close();
				
				out.println("Question Answered");
				response.sendRedirect("Rep/answerQuestions/repCustService.jsp");
				
				out.println("<a href='../repCustomerService.jsp'>Go back to Customer Service Page</a>");
				
			
			}
			
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
		
		
		//out.print("hey bith");
	}

}
