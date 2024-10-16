package com.utfpr;

public class GerenciadorCorrida implements Runnable {
    private final Competidor competidor;
    private final Corrida corrida;

    public GerenciadorCorrida(Competidor competidor, Corrida corrida) {
        this.competidor = competidor;
        this.corrida = corrida;
    }

    @Override
    public void run() {
        try{
            //simula tempo da corrida
            Thread.sleep((int) (Math.random() * 1000) + 500);
            corrida.adicionarCompetidor(competidor); //adiciona ordem de chegada
            corrida.incrementarCompetidoresTerminados(); //atualiza contador
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
