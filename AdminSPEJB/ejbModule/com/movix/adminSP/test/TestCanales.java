package com.movix.adminSP.test;

import java.util.List;

import com.movix.adminSP.model.dao.CanalDAO;
import com.movix.adminSP.model.dao.CanalDAOFactory;
import com.movix.adminSP.model.dto.CanalDTO;

public class TestCanales {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CanalDAO canalDAO = CanalDAOFactory.getCanalDAO();
		 
		 List<CanalDTO> canales = canalDAO.findAll();
		 
		 for(CanalDTO canal : canales){
			 System.out.println("CANAL Nombre: "+canal.getNombre()+" , IdOperador: "+canal.getIdOperador());
		 }

	}

}
