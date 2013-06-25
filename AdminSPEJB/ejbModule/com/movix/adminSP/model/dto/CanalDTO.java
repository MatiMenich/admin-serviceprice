package com.movix.adminSP.model.dto;

public class CanalDTO {
	
	private String nombre;
	private int idOperador;
	
	public CanalDTO(){
		
	}
	
	public CanalDTO(String nombre, int idOperador){
		this.nombre=nombre;
		this.idOperador = idOperador;
	}

	public String getNombre() {
		return this.nombre;
	}

	public int getIdOperador() {
		return this.idOperador;
	}


	

}
