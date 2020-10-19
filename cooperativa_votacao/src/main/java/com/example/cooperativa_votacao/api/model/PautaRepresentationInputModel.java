package com.example.cooperativa_votacao.api.model;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PautaRepresentationInputModel {
	private Long id;
	private String descricao;
	private SessaoRepresentationModel sessao;
	private List<VotoRepresentationModel> voto = new ArrayList<>();
}
