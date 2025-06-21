package br.medtec.exceptions;

import br.medtec.utils.JsonUtils;
import br.medtec.utils.Validations;
import jakarta.ws.rs.WebApplicationException;

public class MEDValidationExecption extends WebApplicationException {

    public MEDValidationExecption(Validations validations){
       super(JsonUtils.toJson(validations.validations));
    }
}
