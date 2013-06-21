package com.movix.adminSP.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movix.adminSP.model.dto.BillServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO;
import com.movix.adminSP.model.dto.RecServicePriceDTO;
import com.movix.adminSP.model.dto.ServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO.TipoEnv;
import com.movix.adminSP.model.dto.RecServicePriceDTO.TipoRec;
import com.movix.adminSP.model.dto.ServicePriceDTO.Estrategia;
import com.movix.shared.Operador;
import com.movixla.ldap.LDAPResponse;

/**
 * Servlet implementation class SPController
 */

// TODO : probar cache
@WebServlet("/SPController")
public class SPController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //SPCache cache;   
    private static String EDIT ="/editarSP.jsp";
    private static String ADD ="/crearSP.jsp";
    private static String LIST_BILL_SP ="/listBillSP.jsp";
    private static String LIST_ENV_SP ="/listEnvSP.jsp";
    private static String LIST_REC_SP ="/listRecSP.jsp";
    private static String ERROR_PAGE = "/404.html";
    
    public SPController() {
        super();
        //cache = new SPCache();
        //cache.init();
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
					//TODO: Refrescar lista página
				}
				else if(action.equalsIgnoreCase("edit")){
					
					int idSP = Integer.parseInt(request.getParameter("idSP"));
					request.setAttribute("opList", Operador.values());
					
					//TODO: Buscar sp en cache segun id
					EnvServicePriceDTO sp = new EnvServicePriceDTO(1,Operador.CLARO_SALVADOR,TipoEnv.SMSWP,"sus3_ClaroSV_7333_cobro_ASC",0.18, Estrategia.ASCENDENTE,"channelClaroSV_Bill","sus3_ClaroSV_7333_cobro_ASC/0.18",true);
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
					
					//TODO: Buscar sp en cache segun id y elegir tipo de prueba
					
					forward="/pruebas/mms.jsp";
					
					
					
				}
				
			}else{
					
				
				if(type.equalsIgnoreCase("Bill")){
					forward=LIST_BILL_SP;
					List<BillServicePriceDTO> sps = new ArrayList<BillServicePriceDTO>();
					//sps = cache.getAllBill();
					sps.add(new BillServicePriceDTO(1,Operador.ENTEL, "sus3_ENTELCL_7733_cobro",0.09,ServicePriceDTO.Estrategia.FULLPRICE,"channelEntelCL_Bill","493|1|7733|Suscripciones_7733",true));
					sps.add(new BillServicePriceDTO(2,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAr_Bill","493|1|7733|Suscripciones_7733",true));
					sps.add(new BillServicePriceDTO(3,Operador.ENTEL, "sus3_EntelCL_3123_cobro",0.07,ServicePriceDTO.Estrategia.FULLPRICE,"channelEntelCL_Bill","",true));
					sps.add(new BillServicePriceDTO(4,Operador.ENTEL, "sus3_EntelCL_3123_cobro",0.06,ServicePriceDTO.Estrategia.FULLPRICE,"channelEntelCL_Bill","493|1|7733",true));
					sps.add(new BillServicePriceDTO(5,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAR_Bill","7223|Suscripciones_7223",true));
					sps.add(new BillServicePriceDTO(6,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAR_Bill","503|1|222|Cobro_222",true));
					sps.add(new BillServicePriceDTO(7,Operador.CLARO_SALVADOR, "sus3_ClaroSV_7733_cobro",0.06,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroSV_Bill","503|1|222|Cobro_222",true));
					sps.add(new BillServicePriceDTO(8,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAR_Bill","493|1|7733|Suscripciones_7733",true));
					sps.add(new BillServicePriceDTO(9,Operador.MOVISTAR_GUATEMALA, "sus3_MovistarGU_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelMovistarGU_Bill","283|1|8833|Suscripciones_8833",true));
					
					sps.get(0).activar();
					sps.get(2).desactivar();
					sps.get(4).activar();
					sps.get(6).activar();
					sps.get(7).desactivar();
					request.setAttribute("sps", sps);
					//sps.get(0).getEstado().toString();
					
				}
				else if(type.equalsIgnoreCase("Env")){
					forward=LIST_ENV_SP;
					//public EnvServicePriceDTO(int id,Operador operador,TipoEnv tipo,String servicio,double precio,Estrategia estrategia,String canal,String args,boolean cache){
					List<EnvServicePriceDTO> sps = new ArrayList<EnvServicePriceDTO>();
					sps.add(new EnvServicePriceDTO(1,Operador.CLARO_SALVADOR,TipoEnv.MMS,"sus3_ClaroSV_7333_cobro_ASC",0.18, Estrategia.ASCENDENTE,"channelClaroSV_Bill","sus3_ClaroSV_7333_cobro_ASC/0.18",true));
					sps.add(new EnvServicePriceDTO(2,Operador.CLARO_ECUADOR,TipoEnv.MMS,"sus3_ClaroEC_4422_cobro_ASC",0.09, Estrategia.ASCENDENTE,"channelClaroEC_Bill","sus3_ClaroEC_4422_cobro_ASC/0.09",true));
					sps.add(new EnvServicePriceDTO(3,Operador.CLARO_ECUADOR,TipoEnv.SMSWP,"sus3_ClaroEC_4422_cobro_ASC",0.18, Estrategia.ASCENDENTE,"channelClaroEC_Bill","sus3_ClaroEC_4422_cobro_ASC/0.18",true));
					sps.add(new EnvServicePriceDTO(4,Operador.ENTEL,TipoEnv.SMSWP,"sus3_ENTEL_258_cobro_ASC",1, Estrategia.ASCENDENTE,"channelENTEL_Bill","sus3_ENTEL_258_cobro_ASC/1",true));
					sps.add(new EnvServicePriceDTO(5,Operador.CLARO_SALVADOR,TipoEnv.SMSWP,"sus3_ClaroSV_8333_cobro_ASC",0.01, Estrategia.DESCENDENTE,"channelClaroSV_Bill","sus3_ClaroSV_8333_cobro_DSC/0.01",true));
					//sps = cache.getAllEnv();
					
					sps.get(0).activar();
					sps.get(2).desactivar();
					sps.get(4).activar();
					
					request.setAttribute("sps", sps);
				}
				else if(type.equalsIgnoreCase("Rec")){
					forward=LIST_REC_SP;
					List<RecServicePriceDTO> sps =  new ArrayList<RecServicePriceDTO>();
					//sps = cache.getAllRec();
					sps.add(new RecServicePriceDTO(1,Operador.CLARO_PERU, TipoRec.MMS, "258PEmms",0.09,258));
					sps.add(new RecServicePriceDTO(2,Operador.CLARO_PERU, TipoRec.MMS, "423PEmms",0,423));
					sps.add(new RecServicePriceDTO(3,Operador.MOVISTAR_PERU, TipoRec.MMS, "423PEmms",0,423));
					sps.add(new RecServicePriceDTO(4,Operador.CLARO_PERU, TipoRec.MMS, "258PEmms",0.01,258));
					sps.add(new RecServicePriceDTO(5,Operador.CLARO_ARGENTINA, TipoRec.MMS, "258ARmms",0.01,258));
					sps.add(new RecServicePriceDTO(6,Operador.DIGICEL_PANAMA, TipoRec.SMS, "423PAsms",0.01,423));
					sps.add(new RecServicePriceDTO(8,Operador.CLARO_PERU, TipoRec.MMS, "423PEmms",0.01,423));
					sps.add(new RecServicePriceDTO(9,Operador.CLARO_ARGENTINA, TipoRec.MMS, "258PEmms",0.01,258));
					sps.add(new RecServicePriceDTO(10,Operador.CLARO_HONDURAS, TipoRec.MMS, "258PEmms",0.01,258));
					sps.add(new RecServicePriceDTO(11,Operador.CLARO_HONDURAS, TipoRec.MMS, "258PEmms",0.01,258));
					sps.add(new RecServicePriceDTO(12,Operador.CLARO_ECUADOR, TipoRec.MMS, "258PEmms",0.01,258));
					sps.add(new RecServicePriceDTO(13,Operador.MOVISTAR_PERU, TipoRec.SMS, "258PEsms",0.01,258));
					sps.add(new RecServicePriceDTO(14,Operador.CLARO_PERU, TipoRec.SMS, "258PEsms",0.01,258));
					sps.add(new RecServicePriceDTO(15,Operador.CLARO_GUATEMALA, TipoRec.SMS, "258PEsms",0.01,258));
					
					sps.get(0).activar();
					sps.get(2).desactivar();
					sps.get(4).activar();
					sps.get(6).activar();
					sps.get(7).desactivar();
					sps.get(8).desactivar();
					sps.get(10).activar();
			
					//sps.get(0);
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
		
		RequestDispatcher view = request.getRequestDispatcher(ERROR_PAGE);
		view.forward(request, response);
		
	}

}
