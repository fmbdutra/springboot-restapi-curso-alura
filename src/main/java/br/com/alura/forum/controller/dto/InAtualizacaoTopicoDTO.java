package br.com.alura.forum.controller.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InAtualizacaoTopicoDTO {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String titulo;

	@NotNull
	@NotEmpty
	@Length(min = 10)
	private String mensagem;

	public Topico atualizar(Long id, TopicoRepository topicoRepository) {
		Topico topico = topicoRepository.getById(id);
		
		topico.setTitulo(this.titulo);
		topico.setMensagem(this.mensagem);
	
		return topico;
	}

}
