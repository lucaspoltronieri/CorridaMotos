package com.utfpr;

public interface ICorrida {
    void adicionarCompetidor(Competidor competidor);

    boolean todosCompetidoresTerminaram();

    void distribuirPontos();
    void resetarCorrida();
    void incrementarCompetidoresTerminados();
}
