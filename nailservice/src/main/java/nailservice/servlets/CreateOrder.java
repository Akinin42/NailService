 package nailservice.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import nailservice.ApplicationContextInjector;
import nailservice.dao.NailServiceDao;
import nailservice.dao.ScriptExecutor;
import nailservice.domain.Administrator;
import nailservice.entity.NailService;

@WebServlet("/createOrder")
public class CreateOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String SQL_SCRIPT_FILE = "schema.sql";
	
	@Override 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String name = request.getParameter("name");
	    String phone = request.getParameter("phone");
	    String date = request.getParameter("date");
	    String time = request.getParameter("time");
	    String nailservice = request.getParameter("nailservice");
	    
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println();
		doGet(request, response);
	}

}
