package carpool;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SaveAddressServlet
 */
public class SaveAddressServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SaveAddressServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CarpoolRequest carpoolReq = getCarpoolRequest(request);
		
		AddressDao dao = new AddressDao();
		dao.saveAddress(carpoolReq);
		
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet</title>");  
            out.println("</head>");
            out.println("<body>");

            String from = request.getParameter("from");
            String to = request.getParameter("to");
            String country = request.getParameter("country");
            String contactName = request.getParameter("contactName");
            String email = request.getParameter("email");
            String phone = request.getParameter("phone");

            out.println("<h1> You are going from: " + from + "</h1>");
            out.println("<h1> You are going to: " + to + "</h1>");
            out.println("<h1> Your country is: " + country + "</h1>");
            out.println("<h1> Contact name is: " + contactName + "</h1>");
            out.println("<h1> Your email is: " + email + "</h1>");
            out.println("<h1> Your phone number is: " + phone + "</h1>");
            out.println("</body>");
            out.println("</html>");
         
        } finally { 
            out.close();
        }
	}
	
	private CarpoolRequest getCarpoolRequest(HttpServletRequest request){
		CarpoolRequest carpoolReq = new CarpoolRequest();
		carpoolReq.setContactName(request.getParameter("contactName"));
		carpoolReq.setCountry(request.getParameter("country"));
		carpoolReq.setEmail(request.getParameter("email"));
		carpoolReq.setFrom(request.getParameter("from"));
		carpoolReq.setPhone(request.getParameter("phone"));
		carpoolReq.setTo(request.getParameter("to"));
		return carpoolReq;
	}

}
