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
 * Servlet implementation class deleteUserServlet
 */
@WebServlet("/deleteUserServlet")
public class deleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public deleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uname = request.getParameter("username");
		PrintWriter out = response.getWriter();
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BuyMe","root", "F-ali67890");
		    Statement st = con.createStatement();

		    ResultSet rs;
		    
		    rs = st.executeQuery("select * from users where uname='" + uname + "'and acct_type='end'");
		    
		    if(!rs.next()) {
		    	out.println("<!DOCTYPE html>");

		 		out.println("<html>");
		 		out.println("<head>");
		 		
		 		out.println("<body>");
		 		
		 		//fix path
		    	out.println("User does not exist <div><a href='deleteUser.jsp'>Try again</a></div>");
		 		
		 		
		 		
		 		out.println("</body>");
		 		out.println("</html>");
		 		
		 		
		 		
		    }
		    else {
		    	PreparedStatement pst = con.prepareStatement("DELETE FROM users WHERE uname='"+uname+"'");
		    	pst.executeUpdate();
		    	out.println("<!DOCTYPE html>");

		 		out.println("<html>");
		 		out.println("<head>");
		 		
		 		out.println("<body>");
		 		
		 		//fix path
				out.print("Deletion of User " +"'"+uname+ "'" +" was successful");
		 		
		 		
		 		
		 		out.println("</body>");
		 		out.println("</html>");
		    	
		    }
		    
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

}
