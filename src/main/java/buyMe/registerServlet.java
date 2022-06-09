package buyMe;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class registerServlet
 */
@WebServlet("/register")
public class registerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public registerServlet() {
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
		String uname = request.getParameter("username");
		String upwd = request.getParameter("password");
		String uemail = request.getParameter("email");
		String umobile = request.getParameter("mobile");
		RequestDispatcher dispatcher = null;
		Connection con = null;
		ResultSet rs;
		
		
		PrintWriter out = response.getWriter();
//		
//		out.println(uname);
//		out.println(pwd);
//		out.println(email);
//		out.println(mobile);
		

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe?useSSL=false", "root", "F-ali67890");
			Statement st = con.createStatement();
			
			//checks to see if user exists
			rs = st.executeQuery("select * from Users where uname='" + uname + "'");
			
			if(rs.next()) {
				out.println("Username already taken <div><a href='register.jsp'>Try again</a></div>");
			}
			
			
			else {
				
			
			
			PreparedStatement pst = con.prepareStatement("insert into Users(uname, upwd, uemail, acct_type, last_login, umobile)  values(?,?,?,default,default,?)");
			pst.setString(1, uname);
			pst.setString(2, upwd);
			pst.setString(3, uemail);
			pst.setString(4, umobile);
			
			int rowCount = pst.executeUpdate();
			dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
			}
		}
		
		
		
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
		
		
	}

}
