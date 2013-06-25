package com.movix.adminSP.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movix.adminSP.cache.SPCache;
import com.movix.adminSP.model.dto.BillServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO;
import com.movix.adminSP.model.dto.RecServicePriceDTO;
import com.movix.adminSP.model.dto.RecServicePriceDTO.TipoRec;
import com.movix.adminSP.model.dto.ServicePriceDTO;
import com.movix.shared.Operador;

/**
 * Servlet implementation class SPController
 */

// TODO : probar cache
@WebServlet("/SPController")
public class SPController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SPCache cacheSP;   
    private static String EDIT ="/editarSP.jsp";
    private static String ADD ="/crearSP.jsp";
    private static String LIST_BILL_SP ="/listBillSP.jsp";
    private static String LIST_ENV_SP ="/listEnvSP.jsp";
    private static String LIST_REC_SP ="/listRecSP.jsp";
    private static String ERROR_PAGE = "/404.html";
    
    public SPController() {
        super();
        cacheSP = new SPCache();
        cacheSP.init();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String type = request.getParameter("type");
		String forward = "";
		
		
		//LDAPResponse ldapResponse = (LDAPResponse) request.getSession().getAttribute("ldapResponse");
		//if(ldapResponse!=null&&ldapResponse.isTieneExito()){
			if(action!=null){
				if(action.equalsIgnoreCase("refresh")){
					//cache.refreshCacheSP();
					
				}
				else if(action.equalsIgnoreCase("edit")){
					
					int idSP = Integer.parseInt(request.getParameter("idSP"));
					request.setAttribute("opList", Operador.values());
					
					ServicePriceDTO sp = cacheSP.getSP(idSP);
					request.setAttribute("sp",sp);
					
					forward = EDIT;
				}
				else if(action.equalsIgnoreCase("delete")){
					//TODO: accion eliminar
				
				}
				else if(action.equalsIgnoreCase("add")){
					
					request.setAttribute("opList", Operador.values());
					forward= ADD;
				}
				
				else if(action.equalsIgnoreCase("test")){
					
					int idSP = Integer.parseInt(request.getParameter("idSP"));
					
					ServicePriceDTO sp = cacheSP.getSP(idSP);
					
					if(sp.getTipo().equals("Billing")){
						forward="/pruebas/bill.jsp";
					}
					else{
						EnvServicePriceDTO envSp = (EnvServicePriceDTO)sp;
						if(envSp.getTipoEnv().toString().equals("VSMS"))
							forward="/pruebas/vsms.jsp";
						else if(envSp.getTipoEnv().toString().equals("MMS"))
							forward="/pruebas/mms.jsp";
						else
							forward="/pruebas/smswp.jsp";
					}
									
					request.setAttribute("idSP", idSP);
					
				}
				
			}else{
					
				
				if(type.equalsIgnoreCase("Bill")){
					forward=LIST_BILL_SP;
					List<BillServicePriceDTO> sps = new ArrayList<BillServicePriceDTO>();
					try {
						sps = cacheSP.getAllBill();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					request.setAttribute("sps", sps);
					
				}
				else if(type.equalsIgnoreCase("Env")){
					forward=LIST_ENV_SP;
					
					List<EnvServicePriceDTO> sps = new ArrayList<EnvServicePriceDTO>();
					
					
					try {
						sps = cacheSP.getAllEnv();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					sps.get(0).activar();
					sps.get(2).desactivar();
					sps.get(4).activar();
					
					request.setAttribute("sps", sps);
				}
				else if(type.equalsIgnoreCase("Rec")){
					forward=LIST_REC_SP;
					List<RecServicePriceDTO> sps =  new ArrayList<RecServicePriceDTO>();
					try {
						sps = cacheSP.getAllRec();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				
					request.setAttribute("sps", sps);
				}
						
			}
		/*}
		else{
			forward=ERROR_PAGE;
		}*/
		
	
		
	
		
		
		
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String type = request.getParameter("tipoSP");
		String idSP = request.getParameter("idSP");
		
		if(idSP!=null){
			//TODO: accion editar
		}
		
		if(type.equalsIgnoreCase("RecMMS") || type.equalsIgnoreCase("RecSMS")){
			int operador = Integer.parseInt(request.getParameter("selectOperador"));
			int la = Integer.parseInt(request.getParameter("la"));
			String servicio = request.getParameter("servicio");
			Double precio = Double.parseDouble(request.getParameter("precio"));
			
			TipoRec tr;
			if(type.substring(3).equals("MMS"))
				tr = TipoRec.MMS;
			else
				tr = TipoRec.SMS;
			
			RecServicePriceDTO sp = new RecServicePriceDTO(0,Operador.getOperadorPorIdBD(operador),tr,servicio,precio,la);
			
			//TODO: Insert via cache o dao?
		}
		
		//TODO: Otros tipos
		
		RequestDispatcher view = request.getRequestDispatcher(ERROR_PAGE);
		view.forward(request, response);
		
	}

}
