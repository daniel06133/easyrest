package com.example.proyectomoviles;

import java.io.Serializable;

public class Pedido implements Serializable{

	  private int id;
	  private String nombre;
	  private int cantidad;
	  private String estado;
	  private String precio;
	  
	    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

		public Pedido() {
	        super();
	    }

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public int getCantidad() {
			return cantidad;
		}

		public void setCantidad(int cantidad) {
			this.cantidad = cantidad;
		}

		public String getEstado() {
			return estado;
		}

		public void setEstado(String estado) {
			this.estado = estado;
		}

		public String getPrecio() {
			return precio;
		}

		public void setPrecio(String precio) {
			this.precio = precio;
		}

}
