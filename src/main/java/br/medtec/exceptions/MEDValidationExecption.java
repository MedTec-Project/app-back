package br.medtec.exceptions;

import br.medtec.utils.ResponseUtils;
import br.medtec.utils.Validcoes;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

public class MEDValidationExecption extends WebApplicationException {

    public final Validcoes validcoes;
    public MEDValidationExecption(Validcoes validcoes){
       super(ResponseUtils.badRequest(validcoes));
       this.validcoes = validcoes;
    }
}
