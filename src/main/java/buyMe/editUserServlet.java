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

/**
 * Servlet implementation class editUserServlet
 */
@WebServlet("/editUserServlet")
public class editUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public editUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String old_username = request.getParameter("old_user");
		String new_username = request.getParameter("new_user");  
		String new_password = request.getParameter("pass");
		String old_email = request.getParameter("old_email");
		String new_email = request.getParameter("new_email");
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/buyMe","root", "F-ali67890");
		    Statement st = con.createStatement();
		    ResultSet rs;
		    rs = st.executeQuery("select * from Users where uname='" + old_username + "'" +"and acct_type='end'");
		    
		    
		    if(!rs.next()) {
		    	out.println("<!DOCTYPE html>");

		 		out.println("<html>");
		 		out.println("<head>");
		 		
		 		out.println("<body>");
		 		//fix path
		 		out.println("User does not exist <div><a href='editUser.jsp'>Try again</a></div>");
		 		
		 		out.println("</body>");
		 		
		 		
		 		out.println("</html>");
		 		
		    }
		    
		    //setting changes 
		    else {
		    	String query = "UPDATE users SET uname='" + new_username+ "', upwd='" +new_password+"', uemail='"+new_email+"' WHERE uname='"+old_username+"'";
		    	PreparedStatement ps = con.prepareStatement(query);
				ps.executeUpdate();
				
				
				out.println("<!DOCTYPE html>");

		 		out.println("<html>");
		 		out.println("<head>");
		 		
		 		out.println("<body>");
		 		//fix path
		 		out.println("The old username: '"+old_username+ "' has been updated to: '"+new_username+"'");
				out.println("The new password is: '" +new_password+"'");
		 		
		 		out.println("</body>");
		 		
		 		
		 		out.println("</html>");
		    }
		    
		}
		
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
