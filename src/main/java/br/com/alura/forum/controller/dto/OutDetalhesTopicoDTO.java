package br.com.alura.forum.controller.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.controller.dto.OutTopicoDTO;
import br.com.alura.forum.modelo.StatusTopico;
import br.com.alura.forum.modelo.Topico;
import lombok.Getter;

@Getter
//Diferente do curso, aplicado herança
public class OutDetalhesTopicoDTO extends OutTopicoDTO{
	
	private String nomeAutor;
	private StatusTopico status;
	private List<OutRespostaDTO> respostas;
	

	public OutDetalhesTopicoDTO(Topico topico) {
		super(topico); //Aplicando herneça, fica menos código
	
		this.nomeAutor = topico.getAutor().getNome();
		this.status = topico.getStatus();
		this.respostas = new ArrayList<>();
		this.respostas.addAll(topico.getRespostas().stream().map(OutRespostaDTO::new).collect(Collectors.toList()));
	}
	
}
