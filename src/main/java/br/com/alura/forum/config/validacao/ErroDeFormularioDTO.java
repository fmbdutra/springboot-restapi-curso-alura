package br.com.alura.forum.config.validacao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErroDeFormularioDTO {
	private String campo;
	private String erroMensagem;
	
	
}
