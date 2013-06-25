package br.com.caelum.loja.session;

import java.util.List;

import javax.ejb.Stateless;

import br.com.caelum.loja.entity.Livro;

@Stateless

public class MensageiroBean implements Mensageiro {

	@Override
	public void enviaMensagem(List<Livro> livros) {
		System.out.println("MSG: " + livros.toString());
	}

}
