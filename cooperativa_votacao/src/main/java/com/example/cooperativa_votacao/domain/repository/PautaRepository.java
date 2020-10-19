package com.example.cooperativa_votacao.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cooperativa_votacao.domain.model.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {

}
