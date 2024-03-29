package br.medtec.utils;

import java.util.Collection;

public class UtilColecao {
    public static boolean colecaoValida(Collection colecao) {
        return (colecao != null) && (!colecao.isEmpty());
    }
}
