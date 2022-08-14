package com.fuerza.modelo;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "entrenamiento_realizado")
@Getter
@Setter
@NoArgsConstructor


public class EntrenamientoRealizado {

	// Relaciones
	@ManyToOne
	@JoinColumn(name = "dni_cliente")
	Cliente cliente;
	@ManyToOne
	@JoinColumn(name = "id_entrenamiento")
	Entrenamiento entrenamiento;

	@ManyToOne
	@JoinColumn(name = "nombre_ejercicio")
	Ejercicio ejercicios;

	// Atributos
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_entrenamiento_realizado", nullable = false, unique = true)
	private int idEntrenamientoRealizado;


	@Column(name = "fecha_entrenamiento_realizado", nullable = false)
	private LocalDate fechaEntrenamientoRealizado;


	@Column(name = "peso_entrenamiento_realizado", nullable = false)
	private double pesoEntrenamientoRealizado;


	@Column(name = "repeticiones_entrenamiento_realizado", nullable = false)
	private int repeticionesEntrenamientoRealizado;


	@Column(name = "series_entrenamiento_realizado", nullable = false)
	private int seriesEntrenamientoRealizado;


	@Column(name = "descansos_entrenamiento_realizado", nullable = false)
	private int descansosEntrenamientoRealizado;

	@Column(name = "volumen_diario_entrenamiento_realizado", nullable = false)
	private int volumenDiarioEntrenamientoRealizado;



	public EntrenamientoRealizado(Cliente personaRealizo, Entrenamiento entrenamiento, LocalDate fechaEntrenamientoRealizado, double pesoEntrenamientoRealizado, int repeticionesEntrenamientoRealizado, int seriesEntrenamientoRealizado, int descansosEntrenamientoRealizado, Ejercicio ejercicios, int volumenDiarioEntrenamientoRealizado) {
		this.cliente = personaRealizo;
		this.entrenamiento = entrenamiento;
		this.fechaEntrenamientoRealizado = fechaEntrenamientoRealizado;
		this.pesoEntrenamientoRealizado = pesoEntrenamientoRealizado;
		this.repeticionesEntrenamientoRealizado = repeticionesEntrenamientoRealizado;
		this.seriesEntrenamientoRealizado = seriesEntrenamientoRealizado;
		this.descansosEntrenamientoRealizado = descansosEntrenamientoRealizado;
		this.ejercicios = ejercicios;
		this.volumenDiarioEntrenamientoRealizado = volumenDiarioEntrenamientoRealizado;
	}

	@Override
	public String toString() {
		return "EntrenamientoRealizado{" +
				"cliente=" + cliente +
				", ejercicios=" + ejercicios +
				", fechaEntrenamientoRealizado=" + fechaEntrenamientoRealizado +
				", pesoEntrenamientoRealizado=" + pesoEntrenamientoRealizado +
				", repeticionesEntrenamientoRealizado=" + repeticionesEntrenamientoRealizado +
				", seriesEntrenamientoRealizado=" + seriesEntrenamientoRealizado +
				", descansosEntrenamientoRealizado=" + descansosEntrenamientoRealizado +
				", volumenDiarioEntrenamientoRealizado=" + volumenDiarioEntrenamientoRealizado +
				'}';
	}
}