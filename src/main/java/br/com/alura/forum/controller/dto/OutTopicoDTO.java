package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.modelo.Topico;
import lombok.Getter;

@Getter
public class OutTopicoDTO {
	private Long id;
	private String titulo;
	private String mensagem;
	private LocalDateTime dataCriacao;
	
	public OutTopicoDTO(Topico topico) {
		this.id = topico.getId();
		this.titulo = topico.getTitulo();
		this.mensagem = topico.getMensagem();
		this.dataCriacao = topico.getDataCriacao();
	}



	public static List<OutTopicoDTO> converter(List<Topico> topicos) {
				
		return topicos.stream().map(OutTopicoDTO::new).collect(Collectors.toList());
	}
	
}
