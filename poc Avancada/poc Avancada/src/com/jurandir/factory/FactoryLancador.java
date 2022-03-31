package com.jurandir.factory;

import com.jurandir.modules.Lancador;
import com.jurandir.modules.Posicao;

import java.util.ArrayList;
import java.util.List;

public class FactoryLancador {
    private List<Lancador> listaLancador;

    public FactoryLancador(int numeroLancadores, long largura) {
        listaLancador = new ArrayList<>();
        for (int i=0; i < numeroLancadores; i++) {
            listaLancador.add(new Lancador( new Posicao(Math.round(largura/2), 0L), 100, 5));
        }
    }

    public List<Lancador> getListaLancador() {
        return listaLancador;
    }
}
