package com.utfpr;

import java.util.Arrays;

public class ExebidorResultados {

    public static void exibirPodioEPlacar(Competidor[] competidores) {
        // Ordena os competidores pela pontuação acumulada
        Arrays.sort(competidores);

        // Exibe o pódio (os três primeiros)
        System.out.println("\n==== Pódio ====");
        for (int i = 0; i < 3; i++) {
            System.out.println((i + 1) + "º lugar: Competidor #" + competidores[i].getNumeroCompetidor() + " com " + competidores[i].getPontos() + " pontos");
        }

        // Exibe a tabela completa de pontos
        System.out.println("\n==== Tabela de Pontos ====");
        for (Competidor competidor : competidores) {
            System.out.println("Competidor #" + competidor.getNumeroCompetidor() + " com " + competidor.getPontos() + " pontos");
        }
    }
}
