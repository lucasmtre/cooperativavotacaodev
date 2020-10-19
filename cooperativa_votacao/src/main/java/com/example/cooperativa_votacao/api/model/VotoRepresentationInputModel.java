package com.example.cooperativa_votacao.api.model;

import com.example.cooperativa_votacao.domain.model.ValorVoto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class VotoRepresentationInputModel {
	private AssociadoRepresentationModel associado;
	private ValorVoto valorVoto;
}
