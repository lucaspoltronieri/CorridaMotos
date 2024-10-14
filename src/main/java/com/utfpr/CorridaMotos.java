package com.utfpr;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class CorridaMotos {

    public static class Competidor implements Comparable<Competidor> {
        private int numeroCompetidor;
        private int pontos;

        public Competidor(int numeroCompetidor) {
            this.numeroCompetidor = numeroCompetidor;
            this.pontos = 0;
        }

        public void adicionarPontos(int pontos) {
            this.pontos += pontos;  // Adiciona pontos ao competidor
        }

        public int getNumeroCompetidor() {
            return numeroCompetidor;
        }

        public int getPontos() {
            return pontos;
        }

        public int compareTo(Competidor outro) {
            return Integer.compare(outro.getPontos(), this.pontos);
        }
    }

    public static class Corrida implements Runnable {
        private final Competidor competidor;
        private List<Competidor> ordemChegada = Collections.synchronizedList(new ArrayList<>());
        private static AtomicInteger competidoresTerminados = new AtomicInteger(0);

        public Corrida(Competidor competidor, List<Competidor> ordemChegada) {
            this.competidor = competidor;
            this.ordemChegada = ordemChegada;
        }

        @Override
        public void run() {
            try {
                // Simula o tempo da corrida
                Thread.sleep(10);
                ordemChegada.add(competidor);  // Adiciona à lista
                competidoresTerminados.incrementAndGet();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public static void resetarCompetidoresTerminados() {
            competidoresTerminados.set(0);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numeroDeCompetidores = 10;
        int numeroDeCorridas = 10;

        Competidor[] competidores = new Competidor[numeroDeCompetidores];

        // Inicializa os competidores

        IntStream.range(1, numeroDeCompetidores + 1).
                forEach(i -> competidores[i - 1] = new Competidor(i));

        // Executa 10 corridas
        
        IntStream.rangeClosed(1, numeroDeCorridas).forEach(corrida -> {
            List<Competidor> ordemChegada = Collections.synchronizedList(new ArrayList<>());
            Corrida.resetarCompetidoresTerminados();

            // Cria uma thread para cada competidor em cada corrida
            IntStream.range(0, numeroDeCompetidores).
                    forEach(i -> new Thread(new Corrida(competidores[i], ordemChegada)).start());

            // Aguarda até que todos os competidores tenham cruzado a linha de chegada
            while (Corrida.competidoresTerminados.get() < numeroDeCompetidores) {
                try {
                    Thread.sleep(10);  // Faz o main thread aguardar
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            // Distribui os pontos de acordo com a ordem de chegada

            IntStream.range(0, ordemChegada.size()).
                    forEach(i -> ordemChegada.get(i).adicionarPontos(numeroDeCorridas - i));
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
