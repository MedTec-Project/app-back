package br.medtec.utils;

import java.util.Collection;
import java.util.List;

public class UtilColecao {
    public static boolean colecaoValida(Collection colecao) {
        return (colecao != null) && (!colecao.isEmpty());
    }

    public static boolean listaValida(List<?> result) {
        return !result.isEmpty();
    }
}
