package br.com.codenation.desafio.entities;

import java.util.Comparator;

public class ComparatorIdade implements Comparator<Jogador> {

    @Override
    public int compare(Jogador o1, Jogador o2) {
        if (o1.getDataNascimento().isEqual(o2.getDataNascimento())){
            return 0;
        }
        else {
            if (o1.getDataNascimento().isBefore(o2.getDataNascimento())){
                return 1;
            }
            else return -1;
        }
    }
}
