package com.fuerza.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "ejercicio")
@Getter
@Setter
@NoArgsConstructor
public class Ejercicio {

	//------------------------------------atributos----------------------
	@ManyToOne
	GrupoMuscular grupoMusculares;

	@Id
	@Column(name = "nombre_ejercicio", length = 10, nullable = false, unique = true)
	private String nombre;
	@Column(name = "descripcion_ejercicio", length = 130, nullable = false)
	private String descripcion;


	//-----------------------------------constructor---------------------------------------------
	public Ejercicio(String nombre, String descripcion, GrupoMuscular grupoMusculares ) {
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.grupoMusculares = grupoMusculares;
	}

	//------------------------------------Metodo tostring---------------------------------------
	@Override
	public String toString() {
		return nombre;
	}

}