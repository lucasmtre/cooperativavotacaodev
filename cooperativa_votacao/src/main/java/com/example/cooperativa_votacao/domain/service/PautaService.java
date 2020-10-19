package com.example.cooperativa_votacao.domain.service;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cooperativa_votacao.domain.exception.EntidadeNaoEncontradaException;
import com.example.cooperativa_votacao.domain.exception.NegocioException;
import com.example.cooperativa_votacao.domain.model.Associado;
import com.example.cooperativa_votacao.domain.model.Pauta;
import com.example.cooperativa_votacao.domain.model.Sessao;
import com.example.cooperativa_votacao.domain.model.StatusSessao;
import com.example.cooperativa_votacao.domain.model.Voto;
import com.example.cooperativa_votacao.domain.repository.PautaRepository;
import com.example.cooperativa_votacao.domain.repository.SessaoRepository;
import com.example.cooperativa_votacao.domain.repository.VotacaoRepository;

@Service
public class PautaService {

	@Autowired
	SessaoRepository sessaoRepository;

	@Autowired
	PautaRepository pautaRepository;
	
	@Autowired
	VotacaoRepository votacaoRepository;
	
	@Autowired
	CadastroAssociadoService cadastroAssociadoService;

	public Pauta salvar(Pauta pauta) {
		try {
			Optional<Pauta> pautaExistente = pautaRepository.findById(pauta.getId());
			if (pautaExistente.isPresent()) {
				if (pautaExistente.get().getSessao() != null
						&& !pautaExistente.get().getSessao().getStatusSessao().equals(StatusSessao.ABERTA)) {
					throw new NegocioException("Sessao da Pauta já Finalizada ");
				} 
				
				throw new NegocioException("Pauta ja Castrada");
			} 
			
			return pautaRepository.save(pauta);
		} catch (NegocioException e) {
			throw e;
		} catch (Exception e) {
			throw new NegocioException("Erro ao cadastrar pauta");
		}
	}

	public Pauta abrirSessao(Long idPauta, Sessao sessao) {
		Pauta pauta = buscaPauta(idPauta);

		if (pauta != null && pauta.getSessao() == null) {
			sessao.setStatusSessao(StatusSessao.ABERTA);
			startTimerSessao(sessao);
			sessao.setPauta(pauta);
			sessaoRepository.save(sessao);

			pauta.setSessao(sessao);
			return pautaRepository.save(pauta);
		} else if(pauta.getSessao().getStatusSessao().equals(StatusSessao.ABERTA)){
			throw new NegocioException("Sessao já foi aberta");
		} else if(pauta.getSessao().getStatusSessao().equals(StatusSessao.FINALIZADA)){
			throw new NegocioException("Sessao já foi finalizada");
		} else {
			throw new NegocioException("Erro ao abrir Sessao");
		}
	}

	private Pauta buscaPauta(Long idPauta) {
		Optional<Pauta> pauta = Optional.ofNullable(pautaRepository.findById(idPauta)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pauta não nao encontrada")));

		return pauta.get();
	}
	
	public Pauta inserirVoto(Long idPauta, Voto voto) {
		try {
			Pauta pauta = buscaPauta(idPauta);

			if (pauta != null && pauta.getSessao() != null
					&& pauta.getSessao().getStatusSessao().equals(StatusSessao.ABERTA)) {
				
				Associado associado = cadastroAssociadoService.buscaAssociado(voto.getCpfAssociado());

				if (associado != null && isAssocidoPodeVotar(pauta, voto.getCpfAssociado())) {
					if(voto.getValorVoto().equalsIgnoreCase("SIM") || voto.getValorVoto().equalsIgnoreCase("NAO")) {
						voto.setPauta(pauta);
						votacaoRepository.save(voto);
						
						pauta.getVotos().add(voto);
						return pautaRepository.save(pauta);
					} else {
						throw new NegocioException("Campo 'valorVoto' incorreto, utilizar somente valores(SIM ou NAO)");
					}
				} else {
					throw new EntidadeNaoEncontradaException("Associado Não Cadastrado");
				}
			} else {
				throw new NegocioException("Sessao esta " + (pauta.getSessao() != null ? pauta.getSessao().getStatusSessao() : pauta.getSessao()));
			}
		} catch (NegocioException e) {
			throw e;
		}catch (Throwable e) {
			throw new NegocioException("Voto não Realizado");
		}
	}

	private boolean isAssocidoPodeVotar(Pauta pauta, String cpf) {
		if (pauta.getVotos() != null) {
			
			Optional<Voto> voto = pauta.getVotos().stream().filter(votoAnalisado -> votoAnalisado.getCpfAssociado().equals(cpf)).findFirst();
			if (voto.isPresent()) {
				throw new NegocioException("Associado já votou");
			} else {
				return true;
			}
		}
		return false;
	}
	
	public Pauta contabilizaVotos(Long idPauta) {
		
		try {
			Pauta pauta = buscaPauta(idPauta);
			if (pauta != null && pauta.getSessao() != null
					&& pauta.getSessao().getStatusSessao().equals(StatusSessao.FINALIZADA)) {
				
					int votosSim = 0;
					int votosNao = 0;
					if (pauta.getVotos() != null && !pauta.getVotos().isEmpty()) {
						for (int i = 0; i < pauta.getVotos().size(); i++) {
							if(pauta.getVotos().get(i).getValorVoto().equalsIgnoreCase("SIM")) {
								votosSim++;
							}else {
								votosNao++;
							}
						}
					}
					pauta.setVotosSIM(votosSim);
					pauta.setVotosNAO(votosNao);
			
				return pautaRepository.save(pauta);
			} else {
				throw new NegocioException("Sessao esta " + (pauta.getSessao() != null ? pauta.getSessao().getStatusSessao() : pauta.getSessao()));
			}
		} catch (NegocioException e) {
			throw e;
		} catch (Throwable e) {
			throw new NegocioException("Não foi possivel Contabilizar os votos");
		}
	}
	
	private void startTimerSessao(Sessao sessao) {
		int delay = sessao.getTime() * 60000; // delay de 5 seg.
		int interval = 1000; // intervalo de 1 seg.
		Timer timer = new Timer();

		System.err.println("Iniciou a Sessão Votação");
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				sessao.setStatusSessao(StatusSessao.FINALIZADA);
				sessaoRepository.save(sessao);
				System.out.println("Finalizou a Sessao Votação");
				timer.cancel();
			}
		}, delay, interval);
	}
}
