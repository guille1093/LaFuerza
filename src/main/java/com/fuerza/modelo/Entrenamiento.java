package com.fuerza.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "entrenamiento")
@Getter
@Setter
@NoArgsConstructor

public class Entrenamiento {


	@ManyToOne
	@JoinColumn(name = "dni_cliente")
	Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "dni_entrenador")
	Entrenador creador;

	@Id
	@Column(name = "id_entrenamiento", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idEntrenamiento;
	@Column(name = "finalizado_entrenamiento",nullable = false)
	private Boolean finalizado;
	@Column(name = "fecha_inicio_entrenamiento",nullable = false)
	private LocalDate fechaInicio;
	@Column(name = "nota_entrenamiento",nullable = false)
	private Double nota;


	public Entrenamiento(Cliente cliente, Entrenador creador, Boolean finalizado, LocalDate fechaInicio, Double nota) {
		this.cliente = cliente;
		this.creador = creador;
		this.finalizado = finalizado;
		this.fechaInicio = fechaInicio;
		this.nota = nota;
	}

	@Override
	public String toString() {
		return  fechaInicio.toString();
	}
}