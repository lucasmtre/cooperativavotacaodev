package com.example.cooperativa_votacao.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.cooperativa_votacao.api.model.PautaRepresentationModel;
import com.example.cooperativa_votacao.domain.model.Pauta;
import com.example.cooperativa_votacao.domain.model.Sessao;
import com.example.cooperativa_votacao.domain.model.Voto;
import com.example.cooperativa_votacao.domain.repository.PautaRepository;
import com.example.cooperativa_votacao.domain.service.PautaService;

@RestController
@RequestMapping("/pautas")
public class PautaController {
	
	@Autowired
	private PautaRepository pautaRepository;
	
	@Autowired
	private PautaService pautaService; 
	
	@Autowired
	private ModelMapper modelMapper;
	
	@GetMapping
	public List<PautaRepresentationModel> listar(){
		return toCollectionModel((pautaRepository.findAll()));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PautaRepresentationModel adicionar(@Valid @RequestBody Pauta pauta) {
		return toModel(pautaService.salvar(pauta));
	}
	
	@PutMapping("/{pautaId}/sessao")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public PautaRepresentationModel abrirSessao(@PathVariable Long pautaId, @Valid @RequestBody Sessao sessao) {
		return toModel(pautaService.abrirSessao(pautaId, sessao));
	}
	
	@PutMapping("/{pautaId}/voto")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void inserirVoto(@PathVariable Long pautaId, @Valid @RequestBody Voto voto) {
		pautaService.inserirVoto(pautaId, voto);
	}
	
	@PutMapping("/{pautaId}/contabilizarVotos")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public PautaRepresentationModel contabilizarVotos(@PathVariable Long pautaId) {
		return toModel(pautaService.contabilizaVotos(pautaId));
	}
	
	private PautaRepresentationModel toModel(Pauta pauta) {
		return modelMapper.map(pauta, PautaRepresentationModel.class);
	}
	
	private List<PautaRepresentationModel> toCollectionModel(List<Pauta> pautas){
		//Stream: retorna uma sequencia de elementos que suporta transformação/agregação
		//.map vai aplicar uma função a todos os elementos 1 a 1 do stream e retorna um novo stream
		// converto o MAP desta forma (ordemServico -> toModel(ordemServico)) o retorno do Stream nesse formato 
		
		//collect vai reduzir o stream para uma coleção(list)
		return pautas.stream().map(pauta -> toModel(pauta)).collect(Collectors.toList());
	}
}
