package pl.iqsoft.plc.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.iqsoft.plc.jpa.DataSource;
import pl.iqsoft.plc.jpa.Measurement;
import pl.iqsoft.plc.jpa.Unit;

/**
 * Servlet for IQPLC instalation process
 */
@WebServlet("/install")
public class InstallServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
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
			Measurement measurement = new Measurement();
			Unit unit = new Unit();
			DataSource dataSource = new DataSource();
			
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
