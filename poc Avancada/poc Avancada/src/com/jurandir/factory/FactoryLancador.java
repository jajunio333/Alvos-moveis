package com.jurandir.factory;

import com.jurandir.modules.Carregador;
import com.jurandir.modules.Lancador;
import com.jurandir.modules.Posicao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FactoryLancador {
    private List<Lancador> listaLancador;

    public FactoryLancador(int numeroLancadores, long largura) {
        listaLancador = new ArrayList<>();
        for (int i=0; i < numeroLancadores; i++) {
            listaLancador.add(new Lancador(new Posicao(Math.round(largura/2), 0L), 15, 5, new Carregador(15)));
        }
    }

    public List<Lancador> getListaLancador() {
        return listaLancador;
    }

}
