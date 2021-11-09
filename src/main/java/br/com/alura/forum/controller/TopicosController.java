package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.InAtualizacaoTopicoDTO;
import br.com.alura.forum.controller.dto.InTopicoDTO;
import br.com.alura.forum.controller.dto.OutDetalhesTopicoDTO;
import br.com.alura.forum.controller.dto.OutTopicoDTO;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repository.CursoRepository;
import br.com.alura.forum.repository.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository  topicoRepository;
	
	@Autowired
	private CursoRepository  cursoRepository;
	
	@RequestMapping(method=RequestMethod.GET )//Colcoar metodo dentro como parametro - Modo 1 de indicar metodo HTTP
	//@ResponseBody // Para não buscar uma view e retornar o retorno "puro" - raw
	
	public List<OutTopicoDTO> lista(String nomeCurso) {
		
		if (nomeCurso == null) {			
			List<Topico> topicos = topicoRepository.findAll();
			return OutTopicoDTO.converter(topicos);	
		} else  {
			List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
			return OutTopicoDTO.converter(topicos);			
		}
	}	
	
	//Deve retornar 201 ao inves de 200, pois 201 é a criação de recurso no servidor.
	@PostMapping //[metodo]Mapping - Modo 2 de definir o método http
	public ResponseEntity<OutTopicoDTO> cadastrar(@RequestBody @Valid InTopicoDTO novoTopico, UriComponentsBuilder uriBuilder) {
		Topico topico = novoTopico.converter(cursoRepository);
		topicoRepository.save(topico);
		
		//201 deve devolver a URL do recurso criado (location)
		// e a representação do recurso criado (detalhes)
		
		//URIBuilder chama a URI (localhost no caso local), faz um endereço (path) e concatena com o ID do item (buildAndExtend). 
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		
		//Passa o 201 (created) e no corpo passa todo o novo recurso com detalhes (.body)
		return ResponseEntity.created(uri).body(new OutTopicoDTO(topico));
	}
	
	@GetMapping(value="/{id}")
	public OutDetalhesTopicoDTO detalhar(@PathVariable("id") Long id) {
		Topico topico = topicoRepository.getById(id);		
		
		return new OutDetalhesTopicoDTO(topico);
	}
	
	//PUT - arruma tudo o recurso
	//PATH - arruma uma parte do recurso
	//Quando não tem definido essas ideias, usa-se PUT normalmente
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<OutTopicoDTO> atualizarTopico(@PathVariable Long id, @RequestBody @Valid InAtualizacaoTopicoDTO topicoAtualizado){
		
		Topico topicoAlterado = topicoAtualizado.atualizar(id, topicoRepository);
		
		return ResponseEntity.ok(new OutTopicoDTO(topicoAlterado));
		
	}
	
}
