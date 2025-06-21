package br.medtec.utils;

import java.util.Collection;
import java.util.List;

public class UtilCollection {
    public static boolean isCollectionValid(Collection colecao) {
        return (colecao != null) && (!colecao.isEmpty());
    }

    public static boolean isValidList(List<?> result) {
        return !result.isEmpty();
    }
}
