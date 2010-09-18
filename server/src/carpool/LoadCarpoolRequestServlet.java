package carpool;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class LoadCarpoolRequestServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    
		AddressDao dao = new AddressDao();
		List<CarpoolRequest> carpoolRequestList = dao.listCarpoolRequests();
		
		
		Gson gson = new Gson();
		String json = gson.toJson(carpoolRequestList);
		
		response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write(json);
        
	}
}
