package com.utfpr;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        private final List<Competidor> ordemChegada;
        private static volatile int competidoresTerminados = 0;

        public Corrida(Competidor competidor, List<Competidor> ordemChegada) {
            this.competidor = competidor;
            this.ordemChegada = ordemChegada;
        }

        @Override
        public void run() {
            try {
                // Simula o tempo da corrida
                Thread.sleep(1000);
                // Registra o competidor na lista de chegada
                synchronized (ordemChegada) {
                    ordemChegada.add(competidor);  // Adiciona à lista
                }
                competidoresTerminados++;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        public static void resetarCompetidoresTerminados() {
            competidoresTerminados = 0;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int numeroDeCompetidores = 10;
        int numeroDeCorridas = 10;

        Competidor[] competidores = new Competidor[numeroDeCompetidores];

        // Inicializa os competidores
        for (int i = 0; i < numeroDeCompetidores; i++) {
            competidores[i] = new Competidor(i);
        }

        // Executa 10 corridas
        for (int corrida = 1; corrida <= numeroDeCorridas; corrida++) {
            List<Competidor> ordemChegada = new ArrayList<>();
            Corrida.resetarCompetidoresTerminados();  // Reseta contador chegada

            // Cria uma thread para cada competidor em cada corrida
            for (int i = 0; i < numeroDeCompetidores; i++) {
                new Thread(new Corrida(competidores[i], ordemChegada)).start();
            }

            // Aguarda até que todos os competidores tenham cruzado a linha de chegada
            while (Corrida.competidoresTerminados < numeroDeCompetidores) {
                Thread.sleep(10);  // Faz o main thread aguardar
            }

            // Distribui os pontos de acordo com a ordem de chegada
            for (int i = 0; i < ordemChegada.size(); i++) {
                int pontos = numeroDeCompetidores - i;  // Primeiro lugar ganha 10 pontos, segundo 9, etc.
                ordemChegada.get(i).adicionarPontos(pontos);
            }
        }

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
