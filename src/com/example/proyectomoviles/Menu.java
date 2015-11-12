package com.example.proyectomoviles;

import java.io.Serializable;

public class Menu implements Serializable{

	private int idMenu;
	private String nombreMenu;
	private String precioMenu;
	private String urlImagenLowQ;
	private String urlImagenHighQ;
	private String descripcionMenu;
	
	
	public int getIdMenu() {
		return idMenu;
	}
	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}
	public String getNombreMenu() {
		return nombreMenu;
	}
	public void setNombreMenu(String nombreMenu) {
		this.nombreMenu = nombreMenu;
	}
	public String getPrecioMenu() {
		return precioMenu;
	}
	public void setPrecioMenu(String precioMenu) {
		this.precioMenu = precioMenu;
	}
	public String getUrlImagenLowQ() {
		return urlImagenLowQ;
	}
	public void setUrlImagenLowQ(String urlImagenLowQ) {
		this.urlImagenLowQ = urlImagenLowQ;
	}
	public String getUrlImagenHighQ() {
		return urlImagenHighQ;
	}
	public void setUrlImagenHighQ(String urlImagenHighQ) {
		this.urlImagenHighQ = urlImagenHighQ;
	}
	public String getDescripcionMenu() {
		return descripcionMenu;
	}
	public void setDescripcionMenu(String descripcionMenu) {
		this.descripcionMenu = descripcionMenu;
	}
	
	
}
