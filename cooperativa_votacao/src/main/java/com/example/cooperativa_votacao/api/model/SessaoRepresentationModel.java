package com.example.cooperativa_votacao.api.model;

import com.example.cooperativa_votacao.domain.model.StatusSessao;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SessaoRepresentationModel {
	private int time;
	private StatusSessao statusSessao;
}
