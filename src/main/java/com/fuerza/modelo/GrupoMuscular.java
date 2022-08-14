package com.fuerza.modelo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "grupo_muscular")
@Getter
@Setter
@NoArgsConstructor
public class GrupoMuscular {


	//@OneToMany
	//Collection<Ejercicio> ejercicios;
	@Id
	@Column(name = "grupo_muscular", length =50, nullable = false, unique = true)
	private String nombreGrupoMuscular;

	@Override
	public String toString() {
		return nombreGrupoMuscular;
	}
}