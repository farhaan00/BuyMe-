package buyMe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class loginServlet
 */
@WebServlet("/login")
public class loginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public loginServlet() {
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
//		doGet(request, response);
//		
//		PrintWriter out = response.getWriter();
//		
//		out.print("heffely");
		
		String uname = request.getParameter("username");
		String upwd = request.getParameter("password");
		
		Connection con = null;
		HttpSession session = request.getSession();
		RequestDispatcher dispatcher = null;
		PrintWriter out = response.getWriter();
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
			Statement st = con.createStatement();
			Statement st2 = con.createStatement();
			ResultSet rs;
			
			
			
			rs = st.executeQuery("select * from users where uname='" + uname + "' and upwd='" + upwd + "'");
			
			if(rs.next()) {
				session.setAttribute("user", uname);
				
				//check the type of account
				String type = rs.getString("acct_type");
				
				if(type.equals("end")) {
					response.sendRedirect("End/endMain.jsp");
				}
				else if(type.equals("rep")) {
					response.sendRedirect("Rep/repMain.jsp");
				}
				else {
					response.sendRedirect("Admin/adminMain.jsp");
				}
				
			}
			
			else {
				
				out.println("Invalid username or password. <div><a href='login.jsp'>Try again</a></div>");
				
			}
			
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
	}

}
