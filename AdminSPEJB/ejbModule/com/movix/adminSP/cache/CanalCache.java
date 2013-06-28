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
import com.movix.adminSP.model.dao.CanalDAOFactory;
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
								
							canalDAO = CanalDAOFactory.getCanalDAO();
							return canalDAO.findAll();
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
	
	//Retorna lista de canales segun id de operador, si el operador no tiene canales retorna null
	public List<CanalDTO> getByOperador(int operador) throws ExecutionException{
		List<CanalDTO> canales = cache.get("Actual");
		List<CanalDTO> opChannels = new ArrayList<CanalDTO>();
		for(CanalDTO canal : canales){
			if(canal.getIdOperador()==operador)
				opChannels.add(canal);
		}
		
		if(opChannels.isEmpty()){
			return null;
		}
		
		return opChannels;
		
		
		
	}
	

}
