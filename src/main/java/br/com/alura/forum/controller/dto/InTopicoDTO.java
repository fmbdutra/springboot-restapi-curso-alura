package br.com.alura.forum.controller.dto;

import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import lombok.Getter;
import lombok.Setter;

//Usando Lombok
@Getter
@Setter
public class InTopicoDTO {
	private String titulo;
	private String mensagem;
	private String nomeCurso;
	
	public Topico converter(CursoRepository cursoRepository) {
		Curso curso = cursoRepository.findByNome(nomeCurso);
		
		return new Topico(titulo, mensagem, curso);
	}
	

}
