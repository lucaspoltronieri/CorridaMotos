package com.utfpr;

public class Competidor implements Comparable<Competidor> {
    private final int numeroCompetidor;
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
