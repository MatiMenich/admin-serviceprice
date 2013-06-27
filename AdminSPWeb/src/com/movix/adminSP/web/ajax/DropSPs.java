package com.movix.adminSP.web.ajax;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movix.adminSP.cache.SPCache;
import com.movix.adminSP.model.comparators.SPComparator;
import com.movix.adminSP.model.dto.ServicePriceDTO;
import com.movix.shared.Operador;

/**
 * Servlet implementation class DropSPs
 */
@WebServlet("/DropSPs")
public class DropSPs extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SPCache cacheSP;   
  
    
    public DropSPs() {
        super();
        cacheSP = new SPCache();
        cacheSP.init();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println("**************DROPDOWN AJAX SP***************");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		 
		 try{
			 int operador = Integer.parseInt(request.getParameter("opValue"));
			 System.out.println("Operador="+operador);
			 
			 List<ServicePriceDTO> sps = cacheSP.getSPByOperador(Operador.getOperadorPorIdBD(operador));
			 
			 if(sps!=null){
				 Collections.sort(sps, new SPComparator());
				 for(ServicePriceDTO sp : sps){
					 out.print("<option value='"+sp.getId()+"'>"+sp.getServicio()+"/"+sp.getPrecio()+"</option>");
				 }
			 }else{
				 out.print("Operador no posee SP!");
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
