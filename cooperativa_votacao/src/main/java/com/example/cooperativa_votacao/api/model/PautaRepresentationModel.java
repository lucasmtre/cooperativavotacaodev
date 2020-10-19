package com.example.cooperativa_votacao.api.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PautaRepresentationModel {
	private Long id;
	private String descricao;
	private SessaoRepresentationModel  sessao;
	private int votosSIM;
	private int votosNAO;
}
