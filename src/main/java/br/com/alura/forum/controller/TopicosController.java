package br.com.alura.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
public class TopicosController {
	
	@Autowired
	private TopicoRepository  topicoRepository;
	
	@RequestMapping("/topicos")
	//@ResponseBody // Para não buscar uma view e retornar o retorno "puro" - raw
	
	public List<TopicoDTO> lista(String nomeCurso) {
		
		if (nomeCurso == null) {			
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDTO.converter(topicos);	
		} else  {
			List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
			return TopicoDTO.converter(topicos);			
		}
	}
}
