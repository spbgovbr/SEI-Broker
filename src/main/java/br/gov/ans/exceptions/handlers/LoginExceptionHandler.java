package br.gov.ans.exceptions.handlers;

import static br.gov.ans.utils.HttpHeadersUtil.getAcceptType;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXB;

import org.jboss.logging.Logger;

import br.gov.ans.exceptions.ErrorMessage;

import com.google.gson.Gson;

@WebServlet("/loginExceptionHandler")
public class LoginExceptionHandler extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Inject
	private Logger logger;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		String acceptType = getAcceptType(req);
		
		resp.setContentType(acceptType);
		resp.setCharacterEncoding("utf-8");

		CODE code = CODE.valueOf("_"+req.getParameter("code"));
		
		ErrorMessage error = new ErrorMessage(code.message, code.status.getStatusCode());
		
		logger.error(error.getError());

		PrintWriter out = resp.getWriter();
		
		if(acceptType.equals(MediaType.APPLICATION_JSON)){
			out.write(new Gson().toJson(error));			
		}else{
			StringWriter sw = new StringWriter();
			JAXB.marshal(error, sw);
			
			out.write(sw.toString());
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	private enum CODE{		
		_401(Status.UNAUTHORIZED, "Cliente não autenticado."), 
		_403(Status.FORBIDDEN, "Cliente não autorizado.");
		
		Status status;
		String message;
		
		private CODE(Status restStatus, String message) {
			this.status = restStatus;
			this.message = message;
		}
	}
}
