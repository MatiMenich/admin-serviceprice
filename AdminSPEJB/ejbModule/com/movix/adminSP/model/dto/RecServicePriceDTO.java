package com.movix.adminSP.model.dto;

import com.movix.shared.Operador;

public class RecServicePriceDTO extends ServicePriceDTO {

	private String LA;
	
	
	public enum TipoRec{
		SMS, MMS
	}
	
	private TipoRec tipoRec;
	
	public RecServicePriceDTO(){
		super();
	}
	
	public RecServicePriceDTO(int id, Operador operador, TipoRec tipo , String servicio, String precio, String LA){
		super();
		this.id=id;
		this.operador=operador;
		this.tipo="Recepcion";
		this.servicio=servicio;
		this.precio=precio;
		this.LA=LA;
		this.estado= Estado.TESTING;
		this.tipoRec=tipo;
		
	}

	public String getLA() {
		return this.LA;
	}

	public void setLA(String LA) {
		this.LA = LA;
	}

	public String getTipoRec() {
		return this.tipoRec.toString();
	}

	public void setTipoRec(String tipo) {
		
		if(tipo.equals("SMS")){
			this.tipoRec= TipoRec.SMS;
		}
		else if(tipo.equals("MMS")){
			this.tipoRec= TipoRec.MMS;
		}
		else{
			System.out.println("Tipo Inválido!");
			//TODO : Crear excepción
			System.exit(1);
			
		}
	}
	
	
	
}
