package pl.iqsoft.plc.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.iqsoft.plc.ejb.DataCollector;
import pl.iqsoft.plc.jpa.DataSource;
import pl.iqsoft.plc.jpa.Measurement;
import pl.iqsoft.plc.jpa.Unit;

/**
 * Servlet for IQPLC instalation process
 */
@SuppressWarnings("serial")
@WebServlet("/install")
public class InstallServlet extends HttpServlet {
    
	@EJB
	private DataCollector dataCollector;
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		//NOP
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter writer = resp.getWriter();
		
		try {
			dataCollector.startCollecting();
			
			writer.write("OK");
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			if (writer != null) {
				writer.close();
			}
		}
	}	
}
