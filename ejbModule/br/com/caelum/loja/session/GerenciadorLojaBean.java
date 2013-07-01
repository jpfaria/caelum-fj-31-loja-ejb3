package br.com.caelum.loja.session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;

import br.com.caelum.loja.entity.Autor;
import br.com.caelum.loja.entity.Livro;

//@TransactionManagement(TransactionManagementType.BEAN)
@Stateless
@Remote(GerenciadorLoja.class)
//@Interceptors(AuditoriaInterceptor.class)
public class GerenciadorLojaBean implements GerenciadorLoja {

	@Resource
	private UserTransaction ut;
	
	@PersistenceContext
	private EntityManager manager;

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

	@Override
	public Livro procura(Long id) {
		Livro livro = this.manager.find(Livro.class, id);
		if (livro != null) {
			livro.getAutores().size();
		}
		return livro;
	}

	@Override
	public Livro salva(Livro livro) {
		this.manager.persist(livro);
		System.out.println("Livro salvo! ID: " + livro.getId());
		return livro;
	}
	/*
	public void salva(Livro livro) {
		this.manager.persist(livro);
		System.out.println("Livro salvo! ID: " + livro.getId());
		throw new SalvaLivroException();
	}
	*/
	/*
	public void salva(Livro livro) {
		try {
			this.ut.begin();
		}catch(Exception e) {
			throw new EJBException(e);
		}
		
		this.manager.persist(livro);
		System.out.println("Livro salvo! ID: " + livro.getId());
		
		try {
			this.ut.commit();
		}catch(Exception e) {
			try {
				this.ut.rollback();
			}catch(Exception e1) {
				throw new EJBException(e);
			}
			throw new EJBException(e);
		}
		
	}
	*/
	
	@Override
	public Autor salva(Autor autor) {
		this.manager.persist(autor);
		System.out.println("Livro salvo! ID: " + autor.getId());
		return autor;
	}

	@Override
	public List<Autor> getAutoresDoLivro(Livro livro) {
		Livro l = procura(livro.getId());
		if (livro != null) {
			l.getAutores().size();
		}
		return l.getAutores();
	}

	@Override
	public List<Livro> buscaLivrosPeloNomeDoAutor(String nome) {
		System.out.println("Buscando livros pelo autor: " + nome);
		
		String jpql = "select livro from Livro as livro"
				+ " join fetch livro.autores as autor"
				+ " where autor.nome like :busca";
		
		Query query = this.manager.createQuery(jpql);
		query.setParameter("busca", "%" + nome + "%");
		
		return query.getResultList();
	}

	@Override
	public List<Livro> listaLivros() {
		return this.manager.createQuery("select livro from Livro as livro "
				+ "join fetch livro.autores").getResultList();
	}
}
