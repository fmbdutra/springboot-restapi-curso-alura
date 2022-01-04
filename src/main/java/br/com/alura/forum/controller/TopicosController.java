package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
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
	private TopicoRepository topicoRepository;

	@Autowired
	private CursoRepository cursoRepository;

	@RequestMapping(method = RequestMethod.GET) // Colcoar metodo dentro como parametro - Modo 1 de indicar metodo HTTP
	// @ResponseBody // Para não buscar uma view e retornar o retorno "puro" - raw

	@ResponseStatus(code = HttpStatus.OK)
	public List<OutTopicoDTO> lista(String nomeCurso) {

		if (nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return OutTopicoDTO.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCurso_Nome(nomeCurso);
			return OutTopicoDTO.converter(topicos);
		}
	}

	// Deve retornar 201 ao inves de 200, pois 201 é a criação de recurso no
	// servidor.
	@PostMapping // [metodo]Mapping - Modo 2 de definir o método http
	@Transactional
	public ResponseEntity<OutTopicoDTO> cadastrar(@RequestBody @Valid InTopicoDTO novoTopico,
			UriComponentsBuilder uriBuilder) {
		Topico topico = novoTopico.converter(cursoRepository);
		topicoRepository.save(topico);

		// 201 deve devolver a URL do recurso criado (location)
		// e a representação do recurso criado (detalhes)

		// URIBuilder chama a URI (localhost no caso local), faz um endereço (path) e
		// concatena com o ID do item (buildAndExtend).
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

		// Passa o 201 (created) e no corpo passa todo o novo recurso com detalhes
		// (.body)
		return ResponseEntity.created(uri).body(new OutTopicoDTO(topico));
	}

	@GetMapping("/{id}")
	public ResponseEntity<OutDetalhesTopicoDTO> detalhar(@PathVariable("id") Long id) {
//		Topico topico = topicoRepository.getById(id);		
//		Optional<Topico> topico = topicoRepository.findById(id);

//		if (topico.isPresent()) return ResponseEntity.ok(new OutDetalhesTopicoDTO(topico.get()));
//		else return ResponseEntity.notFound().build();

//		return topico.map(t -> ResponseEntity.ok(new OutDetalhesTopicoDTO(t)))
//				.orElseGet(() -> ResponseEntity.notFound().build());
		
//		return topico.map(t -> new OutDetalhesTopicoDTO(t))
//					 .map( t -> ResponseEntity.ok(t))
//					 .orElseGet(() -> ResponseEntity.notFound().build());
		
		return topicoRepository.findById(id)
				.map(OutDetalhesTopicoDTO::new)
				.map(ResponseEntity::ok) //é igual ao t -> ResponseEntity.ok(t) chamado de metodo de referencia
				 //JAVA 8 > Metodos de referencia
//				.orElseGet(() -> ResponseEntity.notFound().build());
				.orElse(ResponseEntity.notFound().build());

//		return new OutDetalhesTopicoDTO(topico);
	}

	// PUT - arruma tudo o recurso
	// PATH - arruma uma parte do recurso
	// Quando não tem definido essas ideias, usa-se PUT normalmente
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<OutTopicoDTO> atualizarTopico(@PathVariable Long id,
			@RequestBody @Valid InAtualizacaoTopicoDTO topicoAtualizado) {
		Optional<Topico> topico = topicoRepository.findById(id);

		if (topico.isPresent()) {
			Topico topicoAlterado = topicoAtualizado.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new OutTopicoDTO(topicoAlterado));
		} else
			return ResponseEntity.notFound().build();
//		return ResponseEntity.ok(new OutTopicoDTO(topicoAlterado));

	}

	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> removerTopico(@PathVariable Long id) {
		Optional<Topico> topico = topicoRepository.findById(id);
		if (topico.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		} else
			return ResponseEntity.notFound().build();

	}

}
