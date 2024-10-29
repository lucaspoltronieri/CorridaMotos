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

        ExebidorResultados.exibirPodioEPlacar(competidores);
    }
}
