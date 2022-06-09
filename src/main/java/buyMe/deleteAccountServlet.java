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
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class deleteAccountServlet
 */
@WebServlet("/deleteAccountServlet")
public class deleteAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteAccountServlet() {
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
		HttpSession session = request.getSession();

		
		try {
			
			out.println("<html>");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs;
			

			String username = (String)session.getAttribute("user");
			String thisPassword = request.getParameter("pass");
			
			rs = st.executeQuery("select * from users where uname = '" + username + "' and upwd = '" + thisPassword + "'");

			if(rs.next()) {
				st2.executeUpdate("delete from users where uname = '" + username + "'");
				
				
				out.println("Your account has been deleted. <div><a href='login.jsp'>Return to login page.</a></div>");
			} else {
				out.println("The password you entered is incorrect. <div><a href='End/deleteAccount/deleteAccount.jsp'>Try again</a></div>");
			}
			out.println("</html>");
		}
		catch(Exception e) {
			e.printStackTrace();
		}

	}

}
