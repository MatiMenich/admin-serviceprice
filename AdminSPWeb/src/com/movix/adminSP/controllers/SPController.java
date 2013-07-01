package com.movix.adminSP.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movix.adminSP.cache.SPCache;
import com.movix.adminSP.model.comparators.OperadorComparator;
import com.movix.adminSP.model.dto.BillServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO.TipoEnv;
import com.movix.adminSP.model.dto.RecServicePriceDTO;
import com.movix.adminSP.model.dto.RecServicePriceDTO.TipoRec;
import com.movix.adminSP.model.dto.ServicePriceDTO;
import com.movix.adminSP.model.dto.ServicePriceDTO.Estrategia;
import com.movix.shared.Operador;

/**
 * Servlet implementation class SPController
 */


@WebServlet("/SPController")
public class SPController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private SPCache cacheSP;   
    private static String EDIT ="/editarSP.jsp";
    private static String ADD ="/crearSP.jsp";
    private static String CLONE ="/clonarSP.jsp";
    private static String LIST_BILL_SP ="/listBillSP.jsp";
    private static String LIST_ENV_SP ="/listEnvSP.jsp";
    private static String LIST_REC_SP ="/listRecSP.jsp";
    private static String ERROR_PAGE = "/404.html";
    private static String SUCCESS = "/success.jsp";
    
    public SPController() {
        super();
        cacheSP = new SPCache();
        cacheSP.init();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String type = request.getParameter("type");
		String forward = "";
		
		
		
		//TODO: LDAP
		//LDAPResponse ldapResponse = (LDAPResponse) request.getSession().getAttribute("ldapResponse");
		//if(ldapResponse!=null&&ldapResponse.isTieneExito()){
			if(action!=null){
				if(action.equalsIgnoreCase("refresh")){
					cacheSP.invalidateCacheSP();
					
				}
				else if(action.equalsIgnoreCase("edit")){
					
					int idSP = Integer.parseInt(request.getParameter("idSP"));
					
					List<Operador> opList = new ArrayList<Operador>(Arrays.asList(Operador.values()));
					Collections.sort(opList , new OperadorComparator());
					
					request.setAttribute("opList", opList);
					
					
					ServicePriceDTO sp = cacheSP.getSP(idSP);
					request.setAttribute("sp",sp);
					
					forward = EDIT;
				}
				else if(action.equalsIgnoreCase("clone")){
					int idSP = Integer.parseInt(request.getParameter("idSP"));
					
					ServicePriceDTO sp = cacheSP.getSP(idSP);
					request.setAttribute("sp", sp);
					
					forward = CLONE;
				
				}
				else if(action.equalsIgnoreCase("add")){
					
					List<Operador> opList = new ArrayList<Operador>(Arrays.asList(Operador.values()));
					Collections.sort(opList , new OperadorComparator());
					
					request.setAttribute("opList", opList);
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
				
			}
					
			if(type!=null){	
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
		String forward = "";
		boolean edit = false;
		
		
		if(request.getParameter("clone")!=null){
			ServicePriceDTO sp = cacheSP.getSP(Integer.parseInt(idSP));
			sp.setServicio(request.getParameter("nombre"));
			cacheSP.agregarSP(sp);
			forward=SUCCESS;
		}
		else{
		
			if(idSP!=null){
				edit = true;
			}
			
			if(type.equalsIgnoreCase("RecMMS") || type.equalsIgnoreCase("RecSMS")){
				int operador = Integer.parseInt(request.getParameter("selectOperador"));
				String la = request.getParameter("la");
				String servicio = request.getParameter("servicio");
				String precio = request.getParameter("precio");
				boolean activo = true;
				
				
				TipoRec tr;
				if(type.substring(3).equals("MMS"))
					tr = TipoRec.MMS;
				else
					tr = TipoRec.SMS;
				
				if(edit){
					String radios = request.getParameter("radios");
					if(radios.equals("1"))
						activo = false;
				}
				
				
				if(edit){
					RecServicePriceDTO sp = new RecServicePriceDTO(Integer.parseInt(idSP),Operador.getOperadorPorIdBD(operador),tr,servicio,precio,la);
					if(activo)
						sp.activar();
					else
						sp.desactivar();
					cacheSP.actualizarSP(sp);
				}
				else{
					RecServicePriceDTO sp = new RecServicePriceDTO(0,Operador.getOperadorPorIdBD(operador),tr,servicio,precio,la);
					cacheSP.agregarSP(sp);
				}
				
				forward = SUCCESS;
			}
			
			else if(type.equalsIgnoreCase("EnvSMS") || type.equalsIgnoreCase("EnvMMS") || type.equalsIgnoreCase("EnvVSMS")){
				int operador = Integer.parseInt(request.getParameter("selectOperador"));
				String tipoEnv = type.substring(3);
				String canal = request.getParameter("canal");
				String servicio = request.getParameter("servicio");
				String precio = request.getParameter("precio");
				String tipoEstrategia = request.getParameter("tipoEstrategia");
				Estrategia estrategia = Estrategia.FULLPRICE;
				TipoEnv tipo = TipoEnv.SMSWP;
				Boolean cache = request.getParameter("cache")!=null;
				boolean activo = true;
				
				if(tipoEstrategia.equals("asc"))
					estrategia = Estrategia.ASCENDENTE;
				else if(tipoEstrategia.equals("dsc"))
					estrategia = Estrategia.DESCENDENTE;
				else if(tipoEstrategia.equals("fin"))
					estrategia = Estrategia.FINANCE;
				
				if(tipoEnv.equals("MMS"))
					tipo = TipoEnv.MMS;
				if(tipoEnv.equals("VSMS"))
					tipo = TipoEnv.VSMS;
				
				String[] fpSPs = request.getParameterValues("newSP");
				String args = "";
				
				if(!edit){
					if(estrategia.equals(Estrategia.ASCENDENTE) || estrategia.equals(Estrategia.DESCENDENTE)){
						for(int i = 0; i<fpSPs.length;i++){
							if(i!=0)
								args = args + "|";
							
							args = args + cacheSP.getSP(Integer.parseInt(fpSPs[i])).getServicio() + "/" + cacheSP.getSP(Integer.parseInt(fpSPs[i])).getPrecio();
							
						}
					}
					else{
						args = request.getParameter("args");
					}
						
				}
				else{
					args = request.getParameter("args");
				}
				
				
				if(edit){
					String radios = request.getParameter("radios");
					if(radios.equals("1"))
						activo = false;
				}
				
				if(edit){
					EnvServicePriceDTO sp = new EnvServicePriceDTO(Integer.parseInt(idSP),Operador.getOperadorPorIdBD(operador),tipo,servicio,precio,estrategia,canal,args,cache);
					if(activo)
						sp.activar();
					else
						sp.desactivar();
					
					cacheSP.actualizarSP(sp);
				}
				else{
					EnvServicePriceDTO sp = new EnvServicePriceDTO(0,Operador.getOperadorPorIdBD(operador),tipo,servicio,precio,estrategia,canal,args,cache);
					cacheSP.agregarSP(sp);
				}
				
				forward = SUCCESS;
				
			}
			else if(type.equalsIgnoreCase("Bill")){
				int operador = Integer.parseInt(request.getParameter("selectOperador"));
				String canal = request.getParameter("canal");
				String servicio = request.getParameter("servicio");
				String precio = request.getParameter("precio");
				String tipoEstrategia = request.getParameter("tipoEstrategia");
				Estrategia estrategia = Estrategia.FULLPRICE;
				Boolean cache = request.getParameter("cache")!=null;
				boolean activo = true;
				
				if(tipoEstrategia.equals("asc"))
					estrategia = Estrategia.ASCENDENTE;
				else if(tipoEstrategia.equals("dsc"))
					estrategia = Estrategia.DESCENDENTE;
				else if(tipoEstrategia.equals("fin"))
					estrategia = Estrategia.FINANCE;
				
				
				String[] fpSPs = request.getParameterValues("newSP");
				String args = "";
				
				if(!edit){
					if(estrategia.equals(Estrategia.ASCENDENTE) || estrategia.equals(Estrategia.DESCENDENTE)){
						for(int i = 0; i<fpSPs.length;i++){
							if(i!=0)
								args = args + "|";
							
							args = args + cacheSP.getSP(Integer.parseInt(fpSPs[i])).getServicio() + "/" + cacheSP.getSP(Integer.parseInt(fpSPs[i])).getPrecio();
							
						}
					}
					else{
						args = request.getParameter("args");
					}
						
				}
				else{
					args = request.getParameter("args");
				}
				
				
				if(edit){
					String radios = request.getParameter("radios");
					if(radios.equals("1"))
						activo = false;
				}
				
				if(edit){
					BillServicePriceDTO sp = new BillServicePriceDTO(Integer.parseInt(idSP),Operador.getOperadorPorIdBD(operador),servicio,precio,estrategia,canal,args,cache);
					if(activo)
						sp.activar();
					else
						sp.desactivar();
					
					cacheSP.actualizarSP(sp);
				}
				else{
					BillServicePriceDTO sp = new BillServicePriceDTO(0,Operador.getOperadorPorIdBD(operador),servicio,precio,estrategia,canal,args,cache);
					cacheSP.agregarSP(sp);
				}
				
				forward = SUCCESS;	
			}
			
			else{
				forward = ERROR_PAGE;
			}
		}
		
		
		
		
		RequestDispatcher view = request.getRequestDispatcher(forward);
		view.forward(request, response);
		
	}

}
