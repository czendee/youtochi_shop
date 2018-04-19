package com.tochi.RobotJUEGO;


import java.io.Serializable;


public class Edificio implements Serializable {
	private String id;
	private String name;
	private String estado;
	private String pais;
	private String foto;
	public Edificio(String id, String name, String estado, String pais, String foto) {
		super();
		this.id = id;
		this.name = name;
		this.estado = estado;
		this.pais = pais;
		this.foto = foto;
	}
	// get and set methods

	public void setId(String id){
		this.id=id;
	}
	public void setName(String name){
		this.name=name;
	}
	public void setEstado(String estado){
		this.estado=estado;
	}
	public void setPais(String pais){
		this.pais=pais;
	}
	public void setFoto(String foto){
		this.foto=foto;
	}

	public String getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public String getEstado(){
		return  this.estado;
	}
	public String getPais(){
		return this.pais;
	}
	public String getFoto(){
		return this.foto;
	}

}