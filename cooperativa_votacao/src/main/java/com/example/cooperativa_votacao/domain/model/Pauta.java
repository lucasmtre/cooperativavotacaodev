package com.example.cooperativa_votacao.domain.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.type.descriptor.java.ArrayMutabilityPlan;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "pauta")
public class Pauta {

	@Id
	@NotNull
	private Long id;
	
	@NotNull
	private String descricao;

	@OneToOne(mappedBy = "pauta")
	private Sessao sessao;
	
	@OneToMany(mappedBy = "pauta")
	private List<Voto> votos = new ArrayList<>();
	
	@JsonProperty(access = Access.READ_ONLY)
	private int votosSIM;
	
	@JsonProperty(access = Access.READ_ONLY)
	private int votosNAO;

	@Override
	public String toString() {
		return "Pauta [id=" + id + ", descricao=" + descricao + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pauta other = (Pauta) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
