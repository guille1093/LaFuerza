package com.fuerza.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "rutina_diaria")
@Getter
@Setter
@NoArgsConstructor
public class RutinaDiaria {


	@ManyToOne
	@JoinColumn(name = "id_entrenamiento")
	Entrenamiento unEntrenamiento;

	//------------------------------------atributos----------------------
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_rutina_diaria",nullable = false, unique = true)
	private int idRutina;

	//------------------------------------constructores----------------------

	public RutinaDiaria( Entrenamiento unEntrenamiento) {
		this.unEntrenamiento = unEntrenamiento;
	}
}