package com.utfpr;

import java.util.*;
import java.util.stream.IntStream;

public class CorridaMotos {

    public static void main(String[] args) throws InterruptedException {
        int numeroDeCompetidores = 10;
        int numeroDeCorridas = 10;

        Competidor[] competidores = new Competidor[numeroDeCompetidores];

        // Inicializa os competidores
        IntStream.range(1, numeroDeCompetidores + 1).forEach(i -> competidores[i - 1] = new Competidor(i));

        // Executa 10 corridas
        IntStream.rangeClosed(1, numeroDeCorridas).forEach(ignored -> {
            Corrida corrida = new Corrida(numeroDeCompetidores);


            // Cria uma thread para cada competidor em cada corrida
            IntStream.range(0, numeroDeCompetidores).forEach(i -> new Thread(new GerenciadorCorrida(competidores[i], corrida)).start());

            // Aguarda até que todos os competidores tenham cruzado a linha de chegada
            while (!corrida.todosCompetidoresTerminaram()) {
                try {
                    Thread.sleep(10);  // Faz o main thread aguardar
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // Distribui os pontos de acordo com a ordem de chegada
            corrida.distribuirPontos();
            corrida.resetarCorrida();

        });

        exibirPodioEPlacar(competidores);
    }

    // Função para exibir o pódio e a tabela de pontos
    private static void exibirPodioEPlacar(Competidor[] competidores) {
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
