package com.fuerza.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "detalle_rutina")
@Getter
@Setter
@NoArgsConstructor
public class DetalleRutina {

	@ManyToOne
	@JoinColumn(name = "id_rutina_diaria")
	RutinaDiaria rutinaDiaria;

	@ManyToOne
	@JoinColumn(name = "nombre_ejercicio")
	Ejercicio ejercicios;

	@ManyToOne
	@JoinColumn (name = "dni_cliente", nullable = false)
	Cliente cliente;

	@Id
	@Column(name = "id_detalle_rutina", nullable = false, unique = true)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "repeticiones_detalle_rutina", nullable = false)
	private int repeticiones;

	@Column(name = "descanso_detalle_rutina", nullable = false)
	private int descanso;

	@Column(name = "series_detalle_rutina", nullable = false)
	private int series;

	@Column(name = "dia_detalle_rutina", nullable = false)
	private  String dia;

	public DetalleRutina(Ejercicio ejercicios, Cliente cliente, int repeticiones, int descanso, int series, String dia, RutinaDiaria rutinaDiaria) {
		this.ejercicios = ejercicios;
		this.cliente = cliente;
		this.repeticiones = repeticiones;
		this.descanso = descanso;
		this.series = series;
		this.dia = dia;
		this.rutinaDiaria = rutinaDiaria;
	}

	@Override
	public String toString() {
		return ejercicios.toString();
	}
}