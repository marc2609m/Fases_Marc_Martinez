package com.marc_martinez.backend.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TAPAS")
public class Tapa implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	private String nombre;
	private String descripcion;
	private double precio;
	private String tipo;
	private boolean encarta;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isEnCarta() {
		return encarta;
	}
	public void setEnCarta(boolean enCarta) {
		this.encarta = enCarta;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tapa other = (Tapa) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		return "Tapa [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", precio=" + precio
				+ ", tipo=" + tipo + ", enCarta=" + encarta + "]";
	}
	
	

}
