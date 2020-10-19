package com.example.cooperativa_votacao.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.cooperativa_votacao.domain.model.Associado;

@Repository
public interface AssociadoRepository extends JpaRepository<Associado, Long>{

	List<Associado> findByNome(String nome);
	Associado findByCpf(String cpf);
}
