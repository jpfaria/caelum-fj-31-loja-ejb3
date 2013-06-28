package br.com.caelum.loja.session;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;

import br.com.caelum.loja.entity.Livro;

@Stateless
@Local(Mensageiro.class)
public class MensageiroBean implements Mensageiro {

	@Resource(mappedName="java:/ConnectionFactory")
	private ConnectionFactory fabrica;
	
	@Resource(mappedName="java:/queue/loja")
	private Destination destination;
	
	@Override
	public void enviaMensagem(List<Livro> livros) {
		System.out.println("MSG: " + livros.toString());
	}

}
