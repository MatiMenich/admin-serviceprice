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

	
	@Override
	public int agregar(ServicePriceDTO sp) {
		SPsClient spInstance = SPsClient.getInstance();
		
		if(sp.getTipo()=="Envio"){
			EnvServicePriceDTO envSP = (EnvServicePriceDTO)sp;
			String estrategia = "";
			if(!envSP.getEstrategia().equals(Estrategia.FULLPRICE)){
				estrategia=envSP.getEstrategia().toString();
			}			
			SPEntry envSPEntry = SPEntry.forSending(envSP.getOperador().getIdBD(), envSP.getTipoEnv().toString().toLowerCase(), envSP.getServicio(), envSP.getPrecio(), envSP.getCanal(), estrategia, envSP.getArgs(), true);
			envSPEntry.setCache(envSP.hasCache());
			spInstance.upsert(envSPEntry);
		}
		
		if(sp.getTipo()=="Recepcion"){
			RecServicePriceDTO recSP= (RecServicePriceDTO)sp;
			spInstance.upsert(SPEntry.forReception(recSP.getOperador().getIdBD(), recSP.getLA(), recSP.getTipoRec(), recSP.getServicio(), recSP.getPrecio(), true, false, true));
		}
		
		if(sp.getTipo()=="Billing"){
			BillServicePriceDTO billSP = (BillServicePriceDTO)sp;
			String estrategia = "";
			if(!billSP.getEstrategia().equals(Estrategia.FULLPRICE)){
				estrategia=billSP.getEstrategia().toString();
			}	
			SPEntry billSPEntry = SPEntry.forSending(billSP.getOperador().getIdBD(),"bill", billSP.getServicio(), billSP.getPrecio(), billSP.getCanal(), estrategia, billSP.getArgs(), true);
			billSPEntry.setCache(billSP.hasCache());
			spInstance.upsert(billSPEntry);
		}
		
		return 0;
	}

	//TODO: notificar ventas default TRUE
	
	@Override
	public void actualizar(ServicePriceDTO sp) {
		
		SPsClient spInstance = SPsClient.getInstance();
		
		int idSP = sp.getId();
		
		List<SPEntry> spEntries = spInstance.getAllServicePrices();
		SPEntry editSP = new SPEntry();
		
		for(SPEntry spEntry : spEntries){
			if(spEntry.hashCode()==idSP)
				editSP=spEntry;
		}
		
				
		if(sp.getTipo()=="Envio"){
			
			EnvServicePriceDTO envSP = (EnvServicePriceDTO)sp;
			
			editSP.setOperator_id(envSP.getOperador().getIdBD());
			editSP.setService(envSP.getServicio());
			editSP.setType(envSP.getTipoEnv().toString().toLowerCase());
			editSP.setPrice(envSP.getPrecio());
			editSP.setChannel(envSP.getCanal());
			editSP.setStrategy(envSP.getEstrategia().toString());
			editSP.setArgs(envSP.getArgs());
			editSP.setCache(envSP.hasCache());
			editSP.setActive(envSP.getEstado().equals(ServicePriceDTO.Estado.ACTIVO));
			
			spInstance.upsert(editSP);
		}
		
		if(sp.getTipo()=="Recepcion"){
			
			RecServicePriceDTO recSP= (RecServicePriceDTO)sp;
			
			editSP.setOperator_id(recSP.getOperador().getIdBD());
			editSP.setChannel(recSP.getServicio()+"/"+recSP.getPrecio());
			editSP.setPrice(recSP.getTipoRec().toString());
			editSP.setService(recSP.getLA());
			editSP.setType("rec");
			editSP.setCache(false);
			editSP.setActive(recSP.getEstado().equals(ServicePriceDTO.Estado.ACTIVO));
			
			spInstance.upsert(editSP);
		}
		
		if(sp.getTipo()=="Billing"){
			BillServicePriceDTO billSP = (BillServicePriceDTO)sp;
			
			editSP.setOperator_id(billSP.getOperador().getIdBD());
			editSP.setService(billSP.getServicio());
			editSP.setType("bill");
			editSP.setPrice(billSP.getPrecio());
			editSP.setChannel(billSP.getCanal());
			editSP.setStrategy(billSP.getEstrategia().toString());
			editSP.setArgs(billSP.getArgs());
			editSP.setCache(billSP.hasCache());
			editSP.setActive(billSP.getEstado().equals(ServicePriceDTO.Estado.ACTIVO));
			
			spInstance.upsert(editSP);
		}
	}
	
	@Override
	public List<ServicePriceDTO> findActive() {
		
		List<ServicePriceDTO> sps = this.findALL();
		List<ServicePriceDTO> activeSPs = new ArrayList<ServicePriceDTO>();
		for(ServicePriceDTO sp : sps){
			if(sp.getEstado().equals(ServicePriceDTO.Estado.ACTIVO))
					activeSPs.add(sp);
		}
		
		return activeSPs;
	}

	@Override
	public List<ServicePriceDTO> findALL() {
		SPsClient spInstance = SPsClient.getInstance();
		List<SPEntry> spEntries = spInstance.getAllServicePrices();
		
		List<ServicePriceDTO> sps = new ArrayList<ServicePriceDTO>();
		
		
		for(SPEntry spEntry : spEntries){
			if(spEntry.getType().equals("rec")){
				
				String delimiter="/";
				String[] servicePrice = spEntry.getChannel().split(delimiter);
				
				TipoRec tipoRec;
				if(spEntry.getPrice().equals("SMS"))
					tipoRec= TipoRec.SMS;
				else
					tipoRec=TipoRec.MMS;
				
				String price;
				if(servicePrice.length==1){
					price="0";
				}
				else{
					price=servicePrice[1];
				}
				
				RecServicePriceDTO recSp = new RecServicePriceDTO(spEntry.hashCode(),Operador.getOperadorPorIdBD(spEntry.getOperator_id()),
																tipoRec,servicePrice[0], price, spEntry.getService());
				//TODO: Ver en appconfig los en testing
				if(spEntry.isActive())
					recSp.activar();
				else
					recSp.desactivar();
				
				sps.add(recSp);
				
			}
			
			else if(spEntry.getType().equals("bill")){
				
				Estrategia estrategia;
				if(spEntry.getStrategy()!=null){
					if(spEntry.getStrategy().equals("ASCENDENTE"))
						estrategia= Estrategia.ASCENDENTE;
					else if(spEntry.getStrategy().equals("DESCENDENTE"))
						estrategia= Estrategia.DESCENDENTE;
					else if(spEntry.getStrategy().equals("FINANCE"))
						estrategia= Estrategia.FINANCE;
					else if(spEntry.getStrategy().equals("FULLPRICE"))
						estrategia= Estrategia.FULLPRICE;
					else
						estrategia= Estrategia.FULLPRICE;
				}
				else
					estrategia= Estrategia.FULLPRICE;
				
				String price = spEntry.getPrice();
				
				
				BillServicePriceDTO billSp = new BillServicePriceDTO(spEntry.hashCode(), Operador.getOperadorPorIdBD(spEntry.getOperator_id()),spEntry.getService(),
																		price,estrategia,spEntry.getChannel(),
																		spEntry.getArgs(),spEntry.isCache());
				
				//TODO: Ver en appconfig los en testing
				if(spEntry.isActive())
					billSp.activar();
				else
					billSp.desactivar();
				
				sps.add(billSp);
				
				
				
				
			}
			
			
			else if(spEntry.getType().equals("smswp")||spEntry.getType().equals("web-push")||spEntry.getType().equals("sms")||spEntry.getType().equals("mms")||spEntry.getType().equals("vsms")){
				
				Estrategia estrategia;
				if(spEntry.getStrategy()!=null){
					if(spEntry.getStrategy().equals("ASCENDENTE"))
						estrategia= Estrategia.ASCENDENTE;
					else if(spEntry.getStrategy().equals("DESCENDENTE"))
						estrategia= Estrategia.DESCENDENTE;
					else if(spEntry.getStrategy().equals("FINANCE"))
						estrategia= Estrategia.FINANCE;
					else if(spEntry.getStrategy().equals("FULLPRICE"))
						estrategia= Estrategia.FULLPRICE;
					else
						estrategia= Estrategia.FULLPRICE;
				}
				else
					estrategia= Estrategia.FULLPRICE;
				
				String price = spEntry.getPrice();
				
				TipoEnv tipoEnv;
				if(spEntry.getType().equals("smswp")||spEntry.getType().equals("web-push")||spEntry.getType().equals("sms"))
					tipoEnv = TipoEnv.SMSWP;
				else if(spEntry.getType().equals("vsms"))
					tipoEnv = TipoEnv.VSMS;
				else
					tipoEnv = TipoEnv.MMS;
				
				
				EnvServicePriceDTO envSp = new EnvServicePriceDTO(spEntry.hashCode(),Operador.getOperadorPorIdBD(spEntry.getOperator_id()), tipoEnv,spEntry.getService(),
																	price,estrategia,
																	spEntry.getChannel(),spEntry.getArgs(),spEntry.isCache());
				//TODO: Ver en appconfig los en testing
				if(spEntry.isActive())
					envSp.activar();
				else
					envSp.desactivar();
				
				sps.add(envSp);
				
			}
		}
		
		return sps;
		
	}

	

}
