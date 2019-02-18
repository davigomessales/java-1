package br.com.codenation;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import br.com.codenation.desafio.annotation.Desafio;
import br.com.codenation.desafio.app.MeuTimeInterface;
import br.com.codenation.desafio.entities.*;
import br.com.codenation.desafio.exceptions.*;

public class DesafioMeuTimeApplication implements MeuTimeInterface {

	private List<Time> listaTimes;
	private List<Jogador> listaJogadores;

	public DesafioMeuTimeApplication() {
		this.listaTimes = new ArrayList<>();
		this.listaJogadores = new ArrayList<>();
	}

	@Desafio("incluirTime")
	public void incluirTime(Long id, String nome, LocalDate dataCriacao, String corUniformePrincipal, String corUniformeSecundario) {
		Time time = new Time(id, nome, dataCriacao, corUniformePrincipal, corUniformeSecundario );
		if (this.listaTimes.contains(time)){
			throw new IdentificadorUtilizadoException("Identificador do time ja utilizado.");
		}
		else
			this.listaTimes.add(time);
	}

	@Desafio("incluirJogador")
	public void incluirJogador(Long id, Long idTime, String nome, LocalDate dataNascimento, Integer nivelHabilidade, BigDecimal salario) {
		Jogador jogador = new Jogador(id, idTime, nome, dataNascimento, nivelHabilidade, salario);
		if (this.listaJogadores.contains(jogador)){
			throw new IdentificadorUtilizadoException("Identificador do jogador ja utilizado.");
		}
		else {
			if (!(this.listaTimes.contains(new Time(idTime,"", LocalDate.now(),"", "")))){
				throw new TimeNaoEncontradoException("Time não encontrado.");
			}
			else {
				this.listaJogadores.add(jogador);
			}
		}
	}

	@Desafio("definirCapitao")
	public void definirCapitao(Long idJogador) {
		Jogador jogador = jogadorExiste(idJogador);
		if(jogador != null){
			for (Time t: listaTimes){
				if (jogador.getIdTime().longValue() == t.getId().longValue()){
					t.setIdCapitao(jogador.getId());
				}
			}
		}
		else {
			throw new JogadorNaoEncontradoException("Jogador não encontrado.");
		}
	}

	@Desafio("buscarCapitaoDoTime")
	public Long buscarCapitaoDoTime(Long idTime) {
		Time time = timeExiste(idTime);
		if (time != null) {
			if (time.getIdCapitao()!= null){
				return time.getIdCapitao();
			}
			else {
				throw new CapitaoNaoInformadoException("Capitao não informado.");
			}
		}
		else{
			throw new TimeNaoEncontradoException("Time não encontrado.");
		}
	}

	@Desafio("buscarNomeJogador")
	public String buscarNomeJogador(Long idJogador) {
		Jogador jogador = jogadorExiste(idJogador);
		if (jogador != null) return jogador.getNome();
		else {
			throw new JogadorNaoEncontradoException("Jogador nao encontrado.");
		}
	}

	@Desafio("buscarNomeTime")
	public String buscarNomeTime(Long idTime) {
		Time time = timeExiste(idTime);
		if (time != null) return time.getNome();
		else{
			throw new TimeNaoEncontradoException("Time não encontrado.");
		}
	}

	@Desafio("buscarJogadoresDoTime")
	public List<Long> buscarJogadoresDoTime(Long idTime) {
		Time time = timeExiste(idTime);
		if (time != null){
			List<Long> jogadoresDoTimeId = new ArrayList<>();
			for (Jogador j: listaJogadores){
				if ((jogadorExiste(j.getId())!= null) && (j.getIdTime().longValue() == idTime.longValue())){
					jogadoresDoTimeId.add(j.getId());
				}
			}
			Collections.sort(jogadoresDoTimeId);
			return jogadoresDoTimeId;
		}
		else {
			throw new TimeNaoEncontradoException("Time não encontrado.");
		}
	}
	@Desafio("buscarMelhorJogadorDoTime")
	public Long buscarMelhorJogadorDoTime(Long idTime) {
		Time time = timeExiste(idTime);
		if (time != null){
			List<Jogador> jogadoresDoTime = new ArrayList<>();
			for (Jogador j: listaJogadores){
				if ((jogadorExiste(j.getId())!= null) && j.getIdTime().longValue() == idTime.longValue()){
					jogadoresDoTime.add(j);
				}
			}
			Jogador max = Collections.max(jogadoresDoTime, Comparator.comparing(Jogador::getNivelHabilidade));
			//Jogador max = Collections.max(jogadoresDoTime, new ComparatorHabilidade());
			return max.getId();
		}
		else {
			throw new TimeNaoEncontradoException("Time não encontrado.");
		}
	}

	@Desafio("buscarJogadorMaisVelho")
	public Long buscarJogadorMaisVelho(Long idTime) {
		Time time = timeExiste(idTime);
		if (time != null){
			List<Jogador> jogadoresDoTime = new ArrayList<>();
			for (Jogador j: listaJogadores) {
				if ((jogadorExiste(j.getId())!= null) && j.getIdTime().longValue() == idTime.longValue()){
					jogadoresDoTime.add(j);
				}
			}
			Jogador min = Collections.max(jogadoresDoTime, new ComparatorIdade());
			return min.getId();
		}
		else {
			throw new TimeNaoEncontradoException("Time não encontrado.");
		}
	}

	@Desafio("buscarTimes")
	public List<Long> buscarTimes() {
		List<Long> todosOsTimes = new ArrayList<>();
		for (Time t: listaTimes){
			todosOsTimes.add(t.getId());
		}
		Collections.sort(todosOsTimes);
		return todosOsTimes;

	}

	@Desafio("buscarJogadorMaiorSalario")
	public Long buscarJogadorMaiorSalario(Long idTime) {
		Time time = timeExiste(idTime);
		if (time != null){
			List<Jogador> jogadoresDoTime = new ArrayList<>();
			for (Jogador j: listaJogadores){
				if ((jogadorExiste(j.getId())!= null) && j.getIdTime().longValue() == idTime.longValue()){
					jogadoresDoTime.add(j);
				}
			}
			Jogador max = Collections.max(jogadoresDoTime, new ComparatorSalario());
			return max.getId();
		}
		else {
			throw new TimeNaoEncontradoException("Time não encontrado.");
		}
	}

	@Desafio("buscarSalarioDoJogador")
	public BigDecimal buscarSalarioDoJogador(Long idJogador) {
		Jogador jogador;
		jogador = jogadorExiste(idJogador);
		if (jogador != null) return jogador.getSalario();
		else {
			throw new JogadorNaoEncontradoException("Jogador nao encontrado.");
		}
	}

	@Desafio("buscarTopJogadores")
	public List<Long> buscarTopJogadores(Integer top) {
		listaJogadores.sort(Comparator.comparing(Jogador::getNivelHabilidade).reversed());
		List<Long> topJogadoresLista = new ArrayList<>();
		if((listaJogadores != null) && top < listaJogadores.size()) {
			for (Jogador k : listaJogadores.subList(0, top)) {
				topJogadoresLista.add(k.getId());
			}
			return topJogadoresLista;
		}
		else {
			return topJogadoresLista;
		}
	}

	@Desafio("buscarCorCamisaTimeDeFora")
	public String buscarCorCamisaTimeDeFora(Long timeDaCasa, Long timeDeFora) {
		Time timeCasa = timeExiste(timeDaCasa);
		Time timeFora = timeExiste(timeDeFora);
		if ((timeCasa != null) && (timeFora != null)) {
			if (timeCasa.getCorUniformePrincipal().equals(timeFora.getCorUniformePrincipal())) {
				return timeFora.getCorUniformeSecundario();
			} else return timeFora.getCorUniformePrincipal();
		}
		else {
			throw new TimeNaoEncontradoException("Time não encontrado.");
		}
	}

	private Time timeExiste (Long idTime){
		for(Time t: listaTimes){
			if (t.getId().longValue() == idTime.longValue()){
				return t;
			}
		}
		return null;
	}

	private Jogador jogadorExiste (Long idJogador){
		for(Jogador j: listaJogadores){
			if (j.getId().longValue() == idJogador.longValue()){
				return j;
			}
		}
		return null;
	}

}
