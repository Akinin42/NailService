 package nailservice.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/getDayShedule")
public class FirstServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
	    response.setDateHeader("Date", System.currentTimeMillis());
	    response.addHeader("autor", "devstudy.net");
	    response.setStatus(HttpServletResponse.SC_OK);
	    response.setLocale(Locale.ITALIAN);
	    PrintWriter out = response.getWriter();
	    out.println("<html><body>");
	    out.println("<form action='/test-req-resp' method='post'>");
	    out.println("Your name: <input name='name'><br>");
	    out.println("<input type='submit'>");
	    out.println("</body></html>");
	    out.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
