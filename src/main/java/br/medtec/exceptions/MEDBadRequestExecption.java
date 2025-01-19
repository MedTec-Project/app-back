package br.medtec.exceptions;

import br.medtec.utils.JsonUtils;
import br.medtec.utils.Validcoes;
import jakarta.ws.rs.WebApplicationException;

public class MEDBadRequestExecption extends WebApplicationException {
    public MEDBadRequestExecption(String message) {
        super(message);
    }

    public MEDBadRequestExecption(Validcoes validcoes){
        super(JsonUtils.toJson(validcoes.validacoes));
    }
}
