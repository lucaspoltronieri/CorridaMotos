package com.utfpr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Corrida implements ICorrida {
    private List<Competidor> ordemChegada;
    private int numeroDeCompetidores;
    private final AtomicInteger competidoresTerminados;

    public Corrida(int numeroDeCompetidores) {
        this.ordemChegada = Collections.synchronizedList(new ArrayList<>());
        this.numeroDeCompetidores = numeroDeCompetidores;
        this.competidoresTerminados = new AtomicInteger(0);
    }

    @Override
    public void adicionarCompetidor(Competidor competidor) {
        ordemChegada.add(competidor);
    }

    @Override
    public void incrementarCompetidoresTerminados() {
        competidoresTerminados.incrementAndGet(); //incrementa competidor de forma atomica
    }

    @Override
    public boolean todosCompetidoresTerminaram() {
        return competidoresTerminados.get() == numeroDeCompetidores;
    }

    @Override
    public void distribuirPontos() {
        IntStream.range(0, ordemChegada.size())
                .forEach(i -> ordemChegada.get(i).adicionarPontos(numeroDeCompetidores - i));
    }

    @Override
    public void resetarCorrida() {
        competidoresTerminados.set(0);
        ordemChegada.clear();
    }

    public List<Competidor> getOrdemChegada() {
        return ordemChegada;
    }
}
