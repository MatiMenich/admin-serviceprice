package com.movix.adminSP.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movix.adminSP.cache.CanalCache;
import com.movix.adminSP.model.dao.CanalDAO;
import com.movix.adminSP.model.dao.CanalDAOFactory;
import com.movix.adminSP.model.dto.CanalDTO;
import com.movix.shared.Operador;

/**
 * Servlet implementation class DropCanales
 */
@WebServlet("/DropCanales")
public class DropCanales extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private CanalCache cacheCanal;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropCanales() {
        super();

        cacheCanal = new CanalCache();
        cacheCanal.init();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("**************DROPDOWN AJAX CANALES***************");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		 
		 try{
			 int operador = Integer.parseInt(request.getParameter("opValue"));
			 
			 List<CanalDTO> canales = cacheCanal.getByOperador(operador);
			 
			 for(CanalDTO canal : canales){
				 out.print("<option value='"+canal.getNombre()+"'>"+canal.getNombre()+"</option>");
				 System.out.println("<option value='"+canal.getNombre()+"'>"+canal.getNombre()+"</option>");
			 }
			 
		 }
		 catch (Exception e){
			 e.printStackTrace();
		 }
		 finally{
			 out.close();
		 }
		 
	}

}
