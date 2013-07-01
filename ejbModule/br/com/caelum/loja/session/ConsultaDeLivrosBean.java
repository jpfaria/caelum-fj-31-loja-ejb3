package br.com.caelum.loja.session;

import java.util.List;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.caelum.loja.entity.Livro;
import br.com.caelum.loja.ws.ConsultaDeLivros;

//@WebService
@Stateless 
@Remote(ConsultaDeLivros.class)
public class ConsultaDeLivrosBean implements ConsultaDeLivros{

	@PersistenceContext
	private EntityManager manager;
	
	@Override
	//@WebMethod
	public List<Livro> buscaLivro(@WebParam(name="nome") String nome) {
	
		/*
		Query query = manager.createQuery("select l from Livro as l "
				+ "left join fetch l.autores where l.nome like :nome");
		
		query.setParameter("nome", "%" + nome + "%");
		return query.getResultList();
		*/
		
		return null;
		
	}
	
}