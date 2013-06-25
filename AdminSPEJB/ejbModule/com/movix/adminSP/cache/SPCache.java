package com.movix.adminSP.cache;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalCause;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.movix.adminSP.model.dao.ServicePriceDAO;
import com.movix.adminSP.model.dao.ServicePriceDAOFactory;
import com.movix.adminSP.model.dto.BillServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO;
import com.movix.adminSP.model.dto.RecServicePriceDTO;
import com.movix.adminSP.model.dto.ServicePriceDTO;
import com.movix.adminSP.model.dto.EnvServicePriceDTO.TipoEnv;
import com.movix.adminSP.model.dto.RecServicePriceDTO.TipoRec;
import com.movix.adminSP.model.dto.ServicePriceDTO.Estrategia;
import com.movix.shared.Operador;

public class SPCache {

	LoadingCache<String, List<ServicePriceDTO>> cache;

	private ServicePriceDAO servicepriceDAO;
  
	private Date lastUpdate;
	
	//TODO: log4j
	public void init(){
	
		cache = CacheBuilder.newBuilder().
				maximumSize(2).
				expireAfterAccess(1, TimeUnit.MINUTES).
				removalListener(
					new RemovalListener<String, List<ServicePriceDTO>>(){
	                                public void onRemoval(RemovalNotification<String, List<ServicePriceDTO>> notification) {
	                                    System.out.println("SPCache: Se removera el arreglo");
	                                    
	                                    if(notification.getCause()==RemovalCause.EXPIRED)
	                                    	System.out.println("SPCache: La siguiente entrada expiro: "+notification.getKey());
	                                    else
	                                    	System.out.println("SPCache: La siguiente entrada fue borrada intencionalmente: "+notification.getKey());
	                                    
											refreshCacheSP();
	                                }}
				).
				build(
					new CacheLoader<String, List<ServicePriceDTO>>() {
						public List<ServicePriceDTO> load(String key) { // TODO: no checked exception
								
							//servicepriceDAO = ServicePriceDAOFactory.getServicePriceDAO();
							//return servicepriceDAO.findALL();
							List<ServicePriceDTO> sps = new ArrayList<ServicePriceDTO>();
							sps.add(new BillServicePriceDTO(1,Operador.ENTEL, "sus3_ENTELCL_7733_cobro",0.09,ServicePriceDTO.Estrategia.FULLPRICE,"channelEntelCL_Bill","493|1|7733|Suscripciones_7733",true));
							sps.add(new BillServicePriceDTO(2,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAr_Bill","493|1|7733|Suscripciones_7733",true));
							sps.add(new BillServicePriceDTO(3,Operador.ENTEL, "sus3_EntelCL_3123_cobro",0.07,ServicePriceDTO.Estrategia.FULLPRICE,"channelEntelCL_Bill","",true));
							sps.add(new BillServicePriceDTO(4,Operador.ENTEL, "sus3_EntelCL_3123_cobro",0.06,ServicePriceDTO.Estrategia.FULLPRICE,"channelEntelCL_Bill","493|1|7733",true));
							sps.add(new BillServicePriceDTO(5,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAR_Bill","7223|Suscripciones_7223",true));
							sps.add(new BillServicePriceDTO(6,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAR_Bill","503|1|222|Cobro_222",true));
							sps.add(new BillServicePriceDTO(7,Operador.CLARO_SALVADOR, "sus3_ClaroSV_7733_cobro",0.06,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroSV_Bill","503|1|222|Cobro_222",true));
							sps.add(new BillServicePriceDTO(8,Operador.CLARO_ARGENTINA, "sus3_ClaroAR_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelClaroAR_Bill","493|1|7733|Suscripciones_7733",true));
							sps.add(new BillServicePriceDTO(9,Operador.MOVISTAR_GUATEMALA, "sus3_MovistarGU_7733_cobro",0.08,ServicePriceDTO.Estrategia.FULLPRICE,"channelMovistarGU_Bill","283|1|8833|Suscripciones_8833",true));
							sps.add(new EnvServicePriceDTO(10,Operador.CLARO_SALVADOR,TipoEnv.MMS,"sus3_ClaroSV_7333_cobro_ASC",0.18, Estrategia.ASCENDENTE,"channelClaroSV_Bill","sus3_ClaroSV_7333_cobro_ASC/0.18",true));
							sps.add(new EnvServicePriceDTO(11,Operador.CLARO_ECUADOR,TipoEnv.MMS,"sus3_ClaroEC_4422_cobro_ASC",0.09, Estrategia.ASCENDENTE,"channelClaroEC_Bill","sus3_ClaroEC_4422_cobro_ASC/0.09",true));
							sps.add(new EnvServicePriceDTO(12,Operador.CLARO_ECUADOR,TipoEnv.SMSWP,"sus3_ClaroEC_4422_cobro_ASC",0.18, Estrategia.ASCENDENTE,"channelClaroEC_Bill","sus3_ClaroEC_4422_cobro_ASC/0.18",true));
							sps.add(new EnvServicePriceDTO(13,Operador.ENTEL,TipoEnv.SMSWP,"sus3_ENTEL_258_cobro_ASC",1, Estrategia.ASCENDENTE,"channelENTEL_Bill","sus3_ENTEL_258_cobro_ASC/1",true));
							sps.add(new EnvServicePriceDTO(14,Operador.CLARO_SALVADOR,TipoEnv.SMSWP,"sus3_ClaroSV_8333_cobro_ASC",0.01, Estrategia.DESCENDENTE,"channelClaroSV_Bill","sus3_ClaroSV_8333_cobro_DSC/0.01",true));
							sps.add(new RecServicePriceDTO(15,Operador.CLARO_PERU, TipoRec.MMS, "258PEmms",0.09,258));
							sps.add(new RecServicePriceDTO(16,Operador.CLARO_PERU, TipoRec.MMS, "423PEmms",0,423));
							sps.add(new RecServicePriceDTO(17,Operador.MOVISTAR_PERU, TipoRec.MMS, "423PEmms",0,423));
							sps.add(new RecServicePriceDTO(18,Operador.CLARO_PERU, TipoRec.MMS, "258PEmms",0.01,258));
							sps.add(new RecServicePriceDTO(19,Operador.CLARO_ARGENTINA, TipoRec.MMS, "258ARmms",0.01,258));
							sps.add(new RecServicePriceDTO(20,Operador.DIGICEL_PANAMA, TipoRec.SMS, "423PAsms",0.01,423));
							sps.add(new RecServicePriceDTO(21,Operador.CLARO_PERU, TipoRec.MMS, "423PEmms",0.01,423));
							sps.add(new RecServicePriceDTO(22,Operador.CLARO_ARGENTINA, TipoRec.MMS, "258ARmms",0.01,258));
							sps.add(new RecServicePriceDTO(23,Operador.CLARO_HONDURAS, TipoRec.MMS, "258HOmms",0.01,258));
							sps.add(new RecServicePriceDTO(24,Operador.CLARO_HONDURAS, TipoRec.MMS, "25HOmms",0.01,258));
							sps.add(new RecServicePriceDTO(25,Operador.CLARO_ECUADOR, TipoRec.MMS, "258ECmms",0.01,258));
							sps.add(new RecServicePriceDTO(26,Operador.MOVISTAR_PERU, TipoRec.SMS, "258PEsms",0.01,258));
							sps.add(new RecServicePriceDTO(27,Operador.CLARO_PERU, TipoRec.SMS, "258PEsms",0.01,258));
							sps.add(new RecServicePriceDTO(28,Operador.CLARO_GUATEMALA, TipoRec.SMS, "258GUsms",0.01,258));
							
							sps.get(0).activar();
							sps.get(2).desactivar();
							sps.get(4).activar();
							sps.get(6).activar();
							sps.get(7).desactivar();
							sps.get(8).desactivar();
							sps.get(10).activar();
							sps.get(14).activar();
							sps.get(18).desactivar();
							sps.get(19).activar();
							sps.get(20).activar();
							sps.get(22).desactivar();
							sps.get(25).activar();
							
							return sps;
						}
				});
	}
	
	//TODO: revisar
	public void start() throws ExecutionException{
		lastUpdate = new Date();
		cache.get("Actual");
	}

	public List<ServicePriceDTO> get(String key) throws ExecutionException{
		return cache.get(key);
	}
	
	public Date getLastUpdate(){
		return lastUpdate;
	}
	
	public List<ServicePriceDTO> getAll() throws ExecutionException{
		return cache.get("Actual");
	}
	
	public List<EnvServicePriceDTO> getAllEnv() throws ExecutionException{
		List<ServicePriceDTO> sps= cache.get("Actual");
		List<EnvServicePriceDTO> envSps = new ArrayList<EnvServicePriceDTO>();
		for(ServicePriceDTO sp : sps){
			if(sp.getClass().equals(EnvServicePriceDTO.class))
				envSps.add((EnvServicePriceDTO) sp);
		}
		return envSps;
	}
	
	public List<BillServicePriceDTO> getAllBill() throws ExecutionException{
		List<ServicePriceDTO> sps= cache.get("Actual");
		List<BillServicePriceDTO> billSps = new ArrayList<BillServicePriceDTO>();
		for(ServicePriceDTO sp : sps){
			if(sp.getClass().equals(BillServicePriceDTO.class))
				billSps.add((BillServicePriceDTO) sp);
		}
		return billSps;
	}
	
	public List<RecServicePriceDTO> getAllRec() throws ExecutionException{
		List<ServicePriceDTO> sps= cache.get("Actual");
		List<RecServicePriceDTO> recSps = new ArrayList<RecServicePriceDTO>();
		for(ServicePriceDTO sp : sps){
			if(sp.getClass().equals(RecServicePriceDTO.class))
				recSps.add((RecServicePriceDTO) sp);
		}
		return recSps;
	}
	
	//Función que refresca el cache y pone el antiguo en la variable old para futura referencia.
	public void refreshCacheSP(){
		lastUpdate = new Date();
		cache.refresh("Actual");
		
	}
	
	//Retorna un sp del cache a partir del id del sp, si no lo encuentra, retorna null.
	public ServicePriceDTO getSP(int id){
		List<ServicePriceDTO> sps = new ArrayList<ServicePriceDTO>();
		try {
			sps = this.get("Actual");
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(ServicePriceDTO sp : sps){
			if(sp.getId()==id)
				return sp;
		}
		
		return null;
		
	}
	
	//Retorna un sp del cache a partir de la tupla servicio/precio, si no lo encuentra, retorna null.
	public ServicePriceDTO getSP(String service,int price){
		List<ServicePriceDTO> sps = new ArrayList<ServicePriceDTO>();
		try {
			sps = this.get("Actual");
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for(ServicePriceDTO sp : sps){
			if(sp.getServicio().equals(service)&&sp.getPrecio()==price)
				return sp;
		}
		
		return null;
		
	}
	
	//Retorna la lista de sp
	public List<ServicePriceDTO> getSPByOperador(Operador operador){
		List<ServicePriceDTO> sps = new ArrayList<ServicePriceDTO>();
		List<ServicePriceDTO> spOp = new ArrayList<ServicePriceDTO>();
		try {
			sps = this.get("Actual");
			
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		
		return spOp;
	}
	


	public int agregarSP(ServicePriceDTO sp){
		servicepriceDAO = ServicePriceDAOFactory.getServicePriceDAO();
		int idsp =servicepriceDAO.agregar(sp);
		this.refreshCacheSP();
		return idsp;
		
	}
	
	public void actualizarSP(ServicePriceDTO sp){
		servicepriceDAO = ServicePriceDAOFactory.getServicePriceDAO();
		servicepriceDAO.actualizar(sp);
		
		this.refreshCacheSP();
		
	}
	
	
	
}
