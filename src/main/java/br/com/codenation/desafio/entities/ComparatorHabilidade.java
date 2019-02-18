package br.com.codenation.desafio.entities;

        import java.util.Comparator;

public class ComparatorHabilidade implements Comparator<Jogador> {

    @Override
    public int compare(Jogador o1, Jogador o2) {
        if(o1.getNivelHabilidade().intValue() == o2.getNivelHabilidade().intValue()){
            return 0;
        }
        else {
            if (o1.getNivelHabilidade() > o2.getNivelHabilidade()) {
                return 1;
            } else {
                return -1;
            }
        }
    }
}
