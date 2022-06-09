package buyMe;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class obtainSpecificSalesServlet
 */
@WebServlet("/obtainSpecificSalesServlet")
public class obtainSpecificSalesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public obtainSpecificSalesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		out.println("<!DOCTYPE html>\n"
				+ "					 <html>\n"
				+ "					 <body>");
		
		 String specification = request.getParameter("specification");  
			if(specification==null){
				
				out.println("<h2> Please Choose a Specification</h2>");
			}
			
			
			
			else if(specification.equals("item_type")){
				out.println(" <h3>Choose Item Type</h3>\n"
						+ "		     <form action=\"typeReportServlet\" method=\"POST\">\n"
						+ "			<input type=\"radio\" id=\"shirt\" name=\"type\" value=\"sedans\">\n"
						+ "      		<label for=\"buyer\">Sedans</label><br>\n"
						+ "        	<input type=\"radio\" id=\"seller\" name=\"type\" value=\"bikes\">\n"
						+ "       		 <label for=\"seller\">Bikes</label><br>\n"
						+ "       		 <input type=\"radio\" id=\"buyer\" name=\"type\" value=\"trucks\">\n"
						+ "       		 <label for=\"buyer\">Trucks</label><br>\n"
						+ "			<br>\n"
						+ "		       <input type=\"submit\" value=\"Submit\"/>\n"
						+ "		     </form>");
			}
			
			else {
				out.println(" <h3>Enter Seller Username</h3>\n"
						+ "		      <form action=\"sellerReportServlet\" method=\"POST\">\n"
						+ "		       <input type=\"text\" name=\"seller\"/> <br/>\n"
						+ "		       <input type=\"submit\" value=\"Submit\"/>\n"
						+ "		     </form>");
			}
			
			out.println("<a href='Admin/salesReport/genSalesReport.jsp'>Go back to Generate Sales Report Page</a>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		
		
	}

}
