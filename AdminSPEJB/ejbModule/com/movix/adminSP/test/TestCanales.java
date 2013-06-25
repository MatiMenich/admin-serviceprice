package com.movix.adminSP.test;

import java.util.List;

import com.movix.adminSP.model.dao.CanalDAO;
import com.movix.adminSP.model.dao.CanalDAOFactory;
import com.movix.adminSP.model.dto.CanalDTO;
import com.movix.shared.Operador;

public class TestCanales {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CanalDAO canalDAO = CanalDAOFactory.getCanalDAO();
		 
		 List<CanalDTO> canales = canalDAO.findAll();
		 
		 for(CanalDTO canal : canales){
			 System.out.println(canal.getNombre());
		 }

	}

}
