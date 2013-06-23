package com.movix.adminSP.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.movix.adminSP.model.dto.BillServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO.TipoEnv;
import com.movix.adminSP.model.dto.RecServicePriceDTO;
import com.movix.adminSP.model.dto.RecServicePriceDTO.TipoRec;
import com.movix.adminSP.model.dto.ServicePriceDTO;
import com.movix.adminSP.model.dto.ServicePriceDTO.Estrategia;
import com.movix.shared.Operador;
import com.movixla.service.sp.client.SPsClient;
import com.movixla.service.sp.common.SPEntry;

public class ServicePriceDAOImpl implements ServicePriceDAO {

	
	//TODO: Pedir que upsert devuelva el id o hashcode al ingresar
	@Override
	public int agregar(ServicePriceDTO sp) {
		SPsClient spInstance = SPsClient.getInstance();
		
		if(sp.getTipo()=="Envio"){
			EnvServicePriceDTO envSP = (EnvServicePriceDTO)sp;
			spInstance.upsert(SPEntry.forSending(envSP.getOperador().getIdBD(), envSP.getTipoEnv().toString(), envSP.getServicio(), String.valueOf(envSP.getPrecio()), envSP.getCanal(), envSP.getEstrategia().toString(), envSP.getArgs(), true));
		}
		
		if(sp.getTipo()=="Recepcion"){
			RecServicePriceDTO recSP= (RecServicePriceDTO)sp;
			spInstance.upsert(SPEntry.forReception(recSP.getOperador().getIdBD(), String.valueOf(recSP.getLA()), recSP.getTipoRec(), recSP.getServicio(), String.valueOf(recSP.getPrecio()), true, true, true));
		}
		
		if(sp.getTipo()=="Billing"){
			BillServicePriceDTO billSP = (BillServicePriceDTO)sp;
			spInstance.upsert(SPEntry.forSending(billSP.getOperador().getIdBD(),"bill", billSP.getServicio(), String.valueOf(billSP.getPrecio()), billSP.getCanal(), billSP.getEstrategia().toString(), billSP.getArgs(), true));
		}
		
		return 0;
	}

	//TODO: notificar ventas default TRUE
	
	//TODO: Cache en rec y no en envio?
	@Override
	public void actualizar(ServicePriceDTO sp) {
		
		SPsClient spInstance = SPsClient.getInstance();
		
		if(sp.getTipo()=="Envio"){
			EnvServicePriceDTO envSP = (EnvServicePriceDTO)sp;
			spInstance.upsert(SPEntry.forSending(envSP.getOperador().getIdBD(), envSP.getTipoEnv().toString(), envSP.getServicio(), String.valueOf(envSP.getPrecio()), envSP.getCanal(), envSP.getEstrategia().toString(), envSP.getArgs(), true));
		}
		
		if(sp.getTipo()=="Recepcion"){
			RecServicePriceDTO recSP= (RecServicePriceDTO)sp;
			spInstance.upsert(SPEntry.forReception(recSP.getOperador().getIdBD(), String.valueOf(recSP.getLA()), recSP.getTipoRec(), recSP.getServicio(), String.valueOf(recSP.getPrecio()), true, true, true));
		}
		
		if(sp.getTipo()=="Billing"){
			BillServicePriceDTO billSP = (BillServicePriceDTO)sp;
			spInstance.upsert(SPEntry.forSending(billSP.getOperador().getIdBD(),"bill", billSP.getServicio(), String.valueOf(billSP.getPrecio()), billSP.getCanal(), billSP.getEstrategia().toString(), billSP.getArgs(), true));
		}
	}
	
	@Override
	public List<ServicePriceDTO> findActive() {
		SPsClient spInstance = SPsClient.getInstance();
		List<SPEntry> spEntries = spInstance.getServicePrices();
		
		List<ServicePriceDTO> sps = new ArrayList<ServicePriceDTO>();
		
		
		for(SPEntry spEntry : spEntries){
			if(spEntry.getType().equals("rec")){
				
				String[] servicePrice = spEntry.getChannel().split("/");
				
				TipoRec tipoRec;
				if(spEntry.getPrice().equals("SMS"))
					tipoRec= TipoRec.SMS;
				else
					tipoRec=TipoRec.MMS;
				
				sps.add(new RecServicePriceDTO(spEntry.hashCode(),Operador.getOperadorPorIdBD(spEntry.getOperator_id()),tipoRec,servicePrice[0],
												Double.parseDouble(servicePrice[1]),Integer.parseInt(spEntry.getService())));
				
			}
			
			if(spEntry.getType().equals("bill")){
				
				Estrategia estrategia;
				if(spEntry.getStrategy().equals("ASCENDENTE"))
					estrategia= Estrategia.ASCENDENTE;
				if(spEntry.getStrategy().equals("DESCENDENTE"))
					estrategia= Estrategia.DESCENDENTE;
				if(spEntry.getStrategy().equals("FINANCE"))
					estrategia= Estrategia.FINANCE;
				if(spEntry.getStrategy().equals("FULLPRICE"))
					estrategia= Estrategia.FULLPRICE;
				else
					estrategia= Estrategia.FULLPRICE;
				
				
				sps.add(new BillServicePriceDTO(spEntry.hashCode(), Operador.getOperadorPorIdBD(spEntry.getOperator_id()),spEntry.getService(),
												Double.parseDouble(spEntry.getPrice()),estrategia,spEntry.getChannel(),
												spEntry.getArgs(),spEntry.isCache()));
				
			}
			
			if(spEntry.getType().equals("smswp")||spEntry.getType().equals("web-push")||spEntry.getType().equals("sms")||spEntry.getType().equals("mms")||spEntry.getType().equals("vsms")){
				
				Estrategia estrategia;
				if(spEntry.getStrategy().equals("ASCENDENTE"))
					estrategia= Estrategia.ASCENDENTE;
				if(spEntry.getStrategy().equals("DESCENDENTE"))
					estrategia= Estrategia.DESCENDENTE;
				if(spEntry.getStrategy().equals("FINANCE"))
					estrategia= Estrategia.FINANCE;
				if(spEntry.getStrategy().equals("FULLPRICE"))
					estrategia= Estrategia.FULLPRICE;
				else
					estrategia= Estrategia.FULLPRICE;
				
				TipoEnv tipoEnv;
				if(spEntry.getType().equals("smswp")||spEntry.getType().equals("web-push")||spEntry.getType().equals("sms"))
					tipoEnv = TipoEnv.SMSWP;
				if(spEntry.getType().equals("vsms"))
					tipoEnv = TipoEnv.VSMS;
				else
					tipoEnv = TipoEnv.MMS;
				
				sps.add(new EnvServicePriceDTO(spEntry.hashCode(),Operador.getOperadorPorIdBD(spEntry.getOperator_id()), tipoEnv,spEntry.getService(),
												Double.parseDouble(spEntry.getPrice()),estrategia,
												spEntry.getChannel(),spEntry.getArgs(),spEntry.isCache()));
				
			}
		}
		
		return sps;
	}

	@Override
	public List<ServicePriceDTO> findALL() {
		SPsClient spInstance = SPsClient.getInstance();
		List<SPEntry> spEntries = spInstance.getAllServicePrices();
		
		List<ServicePriceDTO> sps = new ArrayList<ServicePriceDTO>();
		
		
		for(SPEntry spEntry : spEntries){
			if(spEntry.getType().equals("rec")){
				
				String[] servicePrice = spEntry.getChannel().split("/");
				
				TipoRec tipoRec;
				if(spEntry.getPrice().equals("SMS"))
					tipoRec= TipoRec.SMS;
				else
					tipoRec=TipoRec.MMS;
				
				sps.add(new RecServicePriceDTO(spEntry.hashCode(),Operador.getOperadorPorIdBD(spEntry.getOperator_id()),tipoRec,servicePrice[0],
												Double.parseDouble(servicePrice[1]),Integer.parseInt(spEntry.getService())));
				
			}
			
			if(spEntry.getType().equals("bill")){
				
				Estrategia estrategia;
				if(spEntry.getStrategy().equals("ASCENDENTE"))
					estrategia= Estrategia.ASCENDENTE;
				if(spEntry.getStrategy().equals("DESCENDENTE"))
					estrategia= Estrategia.DESCENDENTE;
				if(spEntry.getStrategy().equals("FINANCE"))
					estrategia= Estrategia.FINANCE;
				if(spEntry.getStrategy().equals("FULLPRICE"))
					estrategia= Estrategia.FULLPRICE;
				else
					estrategia= Estrategia.FULLPRICE;
				
				
				sps.add(new BillServicePriceDTO(spEntry.hashCode(), Operador.getOperadorPorIdBD(spEntry.getOperator_id()),spEntry.getService(),
												Double.parseDouble(spEntry.getPrice()),estrategia,spEntry.getChannel(),
												spEntry.getArgs(),spEntry.isCache()));
				
			}
			
			if(spEntry.getType().equals("smswp")||spEntry.getType().equals("web-push")||spEntry.getType().equals("sms")||spEntry.getType().equals("mms")||spEntry.getType().equals("vsms")){
				
				Estrategia estrategia;
				if(spEntry.getStrategy().equals("ASCENDENTE"))
					estrategia= Estrategia.ASCENDENTE;
				if(spEntry.getStrategy().equals("DESCENDENTE"))
					estrategia= Estrategia.DESCENDENTE;
				if(spEntry.getStrategy().equals("FINANCE"))
					estrategia= Estrategia.FINANCE;
				if(spEntry.getStrategy().equals("FULLPRICE"))
					estrategia= Estrategia.FULLPRICE;
				else
					estrategia= Estrategia.FULLPRICE;
				
				TipoEnv tipoEnv;
				if(spEntry.getType().equals("smswp")||spEntry.getType().equals("web-push")||spEntry.getType().equals("sms"))
					tipoEnv = TipoEnv.SMSWP;
				if(spEntry.getType().equals("vsms"))
					tipoEnv = TipoEnv.VSMS;
				else
					tipoEnv = TipoEnv.MMS;
				
				sps.add(new EnvServicePriceDTO(spEntry.hashCode(),Operador.getOperadorPorIdBD(spEntry.getOperator_id()), tipoEnv,spEntry.getService(),
												Double.parseDouble(spEntry.getPrice()),estrategia,
												spEntry.getChannel(),spEntry.getArgs(),spEntry.isCache()));
				
			}
		}
		
		return sps;
		
	}

	

}
