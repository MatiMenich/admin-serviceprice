package com.movix.adminSP.test;

import java.util.ArrayList;
import java.util.List;

import com.movix.adminSP.model.dao.ServicePriceDAO;
import com.movix.adminSP.model.dao.ServicePriceDAOFactory;
import com.movix.adminSP.model.dto.BillServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO;
import com.movix.adminSP.model.dto.ServicePriceDTO;
import com.movix.shared.Operador;

public class TestSPs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		
		 ServicePriceDAO spDAO = ServicePriceDAOFactory.getServicePriceDAO();
		 
		 /*
		 RecServicePriceDTO recSp = new RecServicePriceDTO(0,Operador.CLARO_ARGENTINA,TipoRec.MMS,"PruebaClaroARService1",0,2342);
		 spDAO.agregar(recSp);
		 */
		 List<ServicePriceDTO> sps = spDAO.findALL();
		 List<ServicePriceDTO> spOp= new ArrayList<ServicePriceDTO>();
		 
		 Operador operador = Operador.CLARO_PERU;
		 
		 for(ServicePriceDTO sp : sps){
				if(sp.getOperador().getIdBD()==operador.getIdBD()){
					if(sp.getTipo().equals("Envio")){
						EnvServicePriceDTO envSP = (EnvServicePriceDTO)sp;
						if(envSP.getEstrategia().toString().equals("FULLPRICE")){
							spOp.add(envSP);
						}
					}
					if(sp.getTipo().equals("Billing")){
						BillServicePriceDTO billSP = (BillServicePriceDTO)sp;
						if(billSP.getEstrategia().toString().equals("FULLPRICE")){
							spOp.add(billSP);
						}
					}
				}
					
			}
		 
		 
		 for(ServicePriceDTO sp : spOp){
			 if(sp.getTipo().equals("Billing")){
				 BillServicePriceDTO billSP = (BillServicePriceDTO)sp;
				 System.out.println("Tipo: "+billSP.getTipo()+" , Estrategia: "+billSP.getEstrategia().toString()+" , Servicio: "+billSP.getServicio()+" , Precio: "+billSP.getPrecio());
			 }
			 else{
				 EnvServicePriceDTO envSP = (EnvServicePriceDTO)sp;
				 System.out.println("Tipo: "+envSP.getTipo()+" , Estrategia: "+envSP.getEstrategia().toString()+" , Servicio: "+envSP.getServicio()+" , Precio: "+envSP.getPrecio());
			 }
		 }
		 
		 /*
		 for(ServicePriceDTO sp : sps){
			 if(sp.getServicio().equals("PruebaClaroARService1"))
				 System.out.println("Tipo: "+sp.getTipo()+" , Servicio: "+sp.getServicio()+" , Precio: "+sp.getPrecio());
		 }
		*/
	}

}
