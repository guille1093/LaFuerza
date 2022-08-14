package com.fuerza.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "entrenador")
@Getter
@Setter
@NoArgsConstructor
public class Entrenador {
	// Atributos
	@Id
	@Column(name = "dni_entrenador")
	private String dni;
	@Column(name = "nombre_entrenador", length = 50, nullable = false, unique = false)
	private String nombre;
	@Column(name = "apellido_entrenador", length = 50, nullable = false, unique = false)
	private String apellido;

	public Entrenador(String dni, String nombre, String apellido) {
		this.dni = dni;
		this.nombre = nombre;
		this.apellido = apellido;
	}

	@Override
	public String toString() {
		return dni;
	}
}