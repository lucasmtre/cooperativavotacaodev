package com.example.cooperativa_votacao.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.cooperativa_votacao.domain.model.Associado;
import com.example.cooperativa_votacao.domain.repository.AssociadoRepository;
import com.example.cooperativa_votacao.domain.service.CadastroAssociadoService;

@RestController
@RequestMapping("/associados")
public class AssociadoController {
	
	@Autowired
	private AssociadoRepository associadoRepository;
	
	@Autowired
	private CadastroAssociadoService cadastroAssociadoService;
	
	@GetMapping
	private List<Associado> listar(){
		return associadoRepository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Associado adicionar(@Valid @RequestBody Associado associado) {
		return cadastroAssociadoService.salvar(associado);
	}
}
