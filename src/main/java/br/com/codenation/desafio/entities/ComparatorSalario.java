package br.com.codenation.desafio.entities;

import java.util.Comparator;

public class ComparatorSalario implements Comparator<Jogador> {
    @Override
    public int compare(Jogador o1, Jogador o2) {
        if(o1.getSalario().doubleValue() == o2.getSalario().doubleValue()){
            return 0;
        }
        else {
            if (o1.getSalario().doubleValue() > o2.getSalario().doubleValue()) {
                return 1;
            } else return -1;
        }
    }
}
