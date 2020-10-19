package com.example.cooperativa_votacao.domain.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cooperativa_votacao.domain.exception.NegocioException;
import com.example.cooperativa_votacao.domain.model.Associado;
import com.example.cooperativa_votacao.domain.repository.AssociadoRepository;

@Service
public class CadastroAssociadoService {
	
	//Aqui defino as regras de negocio
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	public Associado salvar(Associado associado) {

		if (buscaAssociado(associado.getCpf()) != null) {
			throw new NegocioException("Associado Já cadastrado! ");
		}
		
		return associadoRepository.save(associado);
		
	}
	
	public Associado buscaAssociado(String cpf) {
		
		if (cpf != null) {
			return associadoRepository.findByCpf(cpf);
		} else {
			throw new NegocioException("Associado não cadastrado!");
		}
		
	}
}
