package br.com.caelum.loja.session;

import java.io.StringWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJBException;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import br.com.caelum.loja.entity.Livro;
import br.com.caelum.loja.util.Livros;

@Stateless
@Local(Mensageiro.class)
public class MensageiroBean implements Mensageiro {

	@Resource(mappedName="java:/ConnectionFactory")
	private ConnectionFactory fabrica;
	
	@Resource(mappedName="java:/queue/loja")
	private Destination destination;
	
	@Override
	public void enviaMensagem(List<Livro> livros) {
		
		try {
			
			// criando sessao
			Connection conexao = fabrica.createConnection("jms", "caelum");
			Session sessao = conexao.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			// criando um wrapper jaxb
			Livros wrapper = new Livros();
			wrapper.adicionaLivros(livros);
			
			// gerando xml
			Marshaller marshaller = JAXBContext.newInstance(Livros.class).createMarshaller();
			StringWriter sw = new StringWriter();
			marshaller.marshal(wrapper, sw);
			
			// criando mensagem jms
			TextMessage tm = sessao.createTextMessage(sw.toString());
			
			// criando um produto de mensagens
			MessageProducer produtor = sessao.createProducer(destination);
			produtor.send(tm);
			
			// fechando conexao
			produtor.close();
			conexao.close();
			
			
		} catch (JMSException e) {
			throw new EJBException(e);
		} catch (JAXBException e) {
			throw new EJBException(e);
		}
		
		System.out.println("MSG: " + livros.toString());
	}

}
