package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> //Tipo de dado, tipo da variavel ID 
{
	List<Topico> findByCurso_Nome(String nomeCurso);
	//Para  setar o relacionamento, se coloca Curso_Nome. Para não haver ambiguidade
	//Caso tenha uma variavel na entidade da lista (Topico)
	
	/*
	 * Quando precisar de uma query manual e trocar o nome do método
	 * 
	 * @Query("Select t FROM Topico t WHERE t.curso.nome = :nomeCurso") List<Topico>
	 * carregarPorNomeDoCurso(@Param ("nomeCurso") String nomeCurso);
	 */
}
