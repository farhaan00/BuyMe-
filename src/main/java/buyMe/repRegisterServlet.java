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
 * Servlet implementation class repRegisterServlet
 */
@WebServlet("/repRegisterServlet")
public class repRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public repRegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");
	    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
	    Statement st = con.createStatement();
	    ResultSet rs;
	    rs = st.executeQuery("select * from users where uname='" + uname + "'");
	    
	    //if rep already exists...
	    if(rs.next()) {
	    	out.println("Username already taken <div><a href='createRep.jsp'>Try again</a></div>");
	    }
	    else if (upwd.length() < 1 || uname.length() < 1 || upwd.contains(" ") || uname.contains(" ")) {
        	out.println("Invalid username or password <div><a href='createRep.jsp'>Try again</a></div>");
	    }
	    
		PreparedStatement pst = con.prepareStatement("insert into users(uname, upwd, uemail, acct_type, last_login, umobile)  values(?,?,?,?,default,?)");
		pst.setString(1, uname);
		pst.setString(2, upwd);
		pst.setString(3, uemail);
		pst.setString(4, "rep");
		pst.setString(5, umobile);
	    
	    pst.executeUpdate();
	    
	    session.setAttribute("user", uname);
	    
	    out.println("<!DOCTYPE html>");

		out.println("<html>");
		out.println("<head>");
		
		out.println("<body>");
		out.println("Account with username " + "'" + uname + "'" + " was successful");
		
		
		//fix path to AdminMain.jsp cant think rn
		out.println("<a href='Admin/adminMain.jsp'>Go back to Admin Main Page</a>");
		out.println("</body>");
		
		
		out.println("</html>");
		
	    
       
        
	    
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
