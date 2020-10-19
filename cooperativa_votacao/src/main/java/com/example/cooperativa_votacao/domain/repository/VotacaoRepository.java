package com.example.cooperativa_votacao.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cooperativa_votacao.domain.model.Voto;

@Repository
public interface VotacaoRepository extends JpaRepository<Voto, Long>{

}
