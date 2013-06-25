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
import com.movix.adminSP.model.dao.CanalDAO;
import com.movix.adminSP.model.dto.CanalDTO;

public class CanalCache {

	LoadingCache<String, List<CanalDTO>> cache;
	
	private CanalDAO canalDAO;
	private Date lastUpdate;

	//TODO: log4j
	public void init(){
		
		cache = CacheBuilder.newBuilder().
				maximumSize(2).
				expireAfterAccess(1, TimeUnit.MINUTES).
				removalListener(
					new RemovalListener<String, List<CanalDTO>>(){
	                                public void onRemoval(RemovalNotification<String, List<CanalDTO>> notification) {
	                                    System.out.println("CanalCache: Se removera el arreglo");
	                                    
	                                    if(notification.getCause()==RemovalCause.EXPIRED)
	                                    	System.out.println("CanalCache: La siguiente entrada expiro: "+notification.getKey());
	                                    else
	                                    	System.out.println("CanalCache: La siguiente entrada fue borrada intencionalmente: "+notification.getKey());
	                                    
											refreshCacheCanal();
	                                }}
				).
				build(
					new CacheLoader<String, List<CanalDTO>>() {
						public List<CanalDTO> load(String key) { // TODO: no checked exception
								
							//canalDAO = CanalDAOFactory.getCanalDAO();
							//return canalDAO.findAll();
							List<CanalDTO> canales = new ArrayList<CanalDTO>();
						
							canales.add(new CanalDTO("entelChannel1SV",1));
							canales.add(new CanalDTO("claroSVChannelSV",2));
							canales.add(new CanalDTO("claroARChannelSV",3));
							canales.add(new CanalDTO("claroECChannelSV",4));
							canales.add(new CanalDTO("claroCLChannelSV",5));
							canales.add(new CanalDTO("portaChannelSV",6));
							canales.add(new CanalDTO("digicomChannelSV",7));
							canales.add(new CanalDTO("etcChannelSV",8));
							canales.add(new CanalDTO("blablaChannelSV",9));
							canales.add(new CanalDTO("asdfChannelSV",10));
							canales.add(new CanalDTO("entelChannel2SV",1));
							canales.add(new CanalDTO("entelChannel3SV",1));
							canales.add(new CanalDTO("entelChannelSV",13));
							canales.add(new CanalDTO("entelChannelSV",14));
							canales.add(new CanalDTO("entelChannelSV",15));
							canales.add(new CanalDTO("entelChannelSV",16));
							canales.add(new CanalDTO("entelChannelSV",17));
							canales.add(new CanalDTO("entelChannelSV",18));
							canales.add(new CanalDTO("entelChannelSV",19));
							canales.add(new CanalDTO("entelChannelSV",20));
							canales.add(new CanalDTO("entelChannelSV",21));
							
							
							
							return canales;
						}
				});
	}
	
	public void start() throws ExecutionException{
		lastUpdate = new Date();
		cache.get("Actual");
	}

	public List<CanalDTO> get(String key) throws ExecutionException{
		return cache.get(key);
	}
	
	public Date getLastUpdate(){
		return lastUpdate;
	}
	
	public void refreshCacheCanal(){
		lastUpdate = new Date();
		cache.refresh("Actual");
		
	}
	
	public List<CanalDTO> getAll() throws ExecutionException{
		return cache.get("Actual");
	}
		
	public List<CanalDTO> getByOperador(int operador) throws ExecutionException{
		List<CanalDTO> canales = cache.get("Actual");
		List<CanalDTO> opChannels = new ArrayList<CanalDTO>();
		for(CanalDTO canal : canales){
			if(canal.getIdOperador()==operador)
				opChannels.add(canal);
		}
		
		return opChannels;
		
		
		
	}
	

}
