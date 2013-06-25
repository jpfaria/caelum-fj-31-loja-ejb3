package br.com.caelum.loja.session;

import java.util.HashMap;
import java.util.Map;

import br.com.caelum.loja.entity.Livro;

public class GerenciadorLojaBean implements GerenciadorLoja{

	private Map<String, Livro> repositorio;
	
	public GerenciadorLojaBean() {
		this.repositorio = new HashMap<String, Livro>();
		
		Livro l1 = new Livro();
		l1.setNome("Pais e Filhos");
		l1.setPreco(100.0);
		
		Livro l2 = new Livro();
		l2.setNome("Noites brancas");
		l2.setPreco(200.0);
		
		this.repositorio.put("1111", l1);
		this.repositorio.put("2222", l2);
	}
	
	@Override
	public Livro procura(String isbn) {
		return this.repositorio.get(isbn);
	}

}
