package com.movix.adminSP.model.dto;

import com.movix.shared.Operador;

public class CanalDTO {
	
	private String nombre;
	private Operador operador;
	
	public CanalDTO(){
		
	}
	
	public CanalDTO(String nombre, int operador){
		this.nombre=nombre;
		this.operador = Operador.getOperadorPorIdBD(operador);
	}

	public String getNombre() {
		return nombre;
	}

	public Operador getOperador() {
		return operador;
	}


	

}
