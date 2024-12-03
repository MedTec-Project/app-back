package br.medtec.exceptions;

import br.medtec.utils.JsonUtils;
import br.medtec.utils.Validcoes;
import jakarta.ws.rs.WebApplicationException;

public class MEDValidationExecption extends WebApplicationException {

    public MEDValidationExecption(Validcoes validcoes){
       super(JsonUtils.toJson(validcoes.validacoes));
    }
}
