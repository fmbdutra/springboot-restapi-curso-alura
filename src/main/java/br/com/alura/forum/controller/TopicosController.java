package br.com.alura.forum.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.forum.controller.dto.TopicoDTO;
import br.com.alura.forum.modelo.Curso;
import br.com.alura.forum.modelo.Topico;

@RestController
public class TopicosController {
	@RequestMapping("/topicos")
	//@ResponseBody // Para não buscar uma view e retornar o retorno "puro" - raw
	
	public List<TopicoDTO> lista() {
		Topico topico = new Topico("Duvida", "Duvida com Spring", new Curso("Spring", "Programacao"));

		return TopicoDTO.converter(Arrays.asList(topico));
	}
}
