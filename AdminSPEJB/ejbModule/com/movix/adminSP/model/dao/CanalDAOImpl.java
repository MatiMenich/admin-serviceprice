package com.movix.adminSP.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.movix.adminSP.model.dto.CanalDTO;
import com.movix.shared.Operador;
import com.movixla.service.priorities.client.PrioritiesClient;
import com.movixla.service.priorities.common.OperatorRate;

public class CanalDAOImpl implements CanalDAO {


	@Override
	public List<CanalDTO> findByOperador(Operador operador) {
		List<CanalDTO> canales = this.findAll();
		List<CanalDTO> opChannels = new ArrayList<CanalDTO>();
		
		for(CanalDTO canal : canales){
			if(canal.getOperador().getIdBD()==operador.getIdBD()){
				opChannels.add(canal);
			}
		}
		
		return opChannels;
	}

	@Override
	public List<CanalDTO> findAll() {
		
		List<OperatorRate> opRates = PrioritiesClient.getInstance().getOperatorRates();
		
		List<CanalDTO> canales = new ArrayList<CanalDTO>();
		for(OperatorRate opRate : opRates){
			canales.add(new CanalDTO(opRate.getChannel(),opRate.getOperator()));
		}
		return canales;
	}

}
