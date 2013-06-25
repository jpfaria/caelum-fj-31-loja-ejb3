package br.com.caelum.loja.session;

import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.Schedule;
import javax.ejb.ScheduleExpression;
import javax.ejb.Stateless;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;

@Stateless
@Remote(Agendador.class)
public class AgendadorBean implements Agendador {

	@Resource
	TimerService timer;
	
	@Override
	public void agenda(String expressaoMinutos, String expressaoSegundos) {
		ScheduleExpression expression = new ScheduleExpression();
		expression.hour("*").minute(expressaoMinutos).second(expressaoSegundos);
		
		TimerConfig config = new TimerConfig();
		config.setInfo(expression.toString());
		config.setPersistent(false);
		
		timer.createCalendarTimer(expression, config);
		
		System.out.println("Agendamento: " + expression);		
	}
	
	
	@Timeout
	public void realizaTransacaoBancaria(Timer timer){
		System.out.println(timer.getInfo());
	}
	
	@Schedule(hour="*", minute="*/1", second="0", persistent=false)
	public void enviaEmailCadaMinutoComInformacoesDoDolar() {
		System.out.println("Enviando email a cada minuto");
	}

}