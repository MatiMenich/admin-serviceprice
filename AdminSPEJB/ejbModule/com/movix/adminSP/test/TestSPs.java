package com.movix.adminSP.test;

import java.util.List;

import com.movix.adminSP.model.dao.ServicePriceDAO;
import com.movix.adminSP.model.dao.ServicePriceDAOFactory;
import com.movix.adminSP.model.dto.ServicePriceDTO;

public class TestSPs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServicePriceDAO spDAO = ServicePriceDAOFactory.getServicePriceDAO();
		 
		 List<ServicePriceDTO> sps = spDAO.findALL();
		 
		 for(ServicePriceDTO sp : sps){
			 System.out.println("Servicio:"+sp.getServicio());
		 }

	}

}
