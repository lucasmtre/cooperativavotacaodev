package com.example.cooperativa_votacao.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.validation.constraints.NotNull;
import java.util.Timer;
import java.util.TimerTask;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;

@Entity @Getter @Setter
@Table(name="sessao")
public class Sessao {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "tempo")
	@NotNull
	private int time = 1;
	
	@OneToOne
	private Pauta pauta;

	@JsonProperty(access = Access.READ_ONLY)
	@Enumerated(EnumType.STRING)
	private StatusSessao statusSessao;
	
	public void startTimer() {
		int delay = 10000; // delay de 5 seg.
		int interval = 1000; // intervalo de 1 seg.
		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				setStatusSessao(StatusSessao.FINALIZADA);
				System.out.println("finalizou");
				timer.cancel();
			}
		}, delay, interval);
	}
	
	

}
