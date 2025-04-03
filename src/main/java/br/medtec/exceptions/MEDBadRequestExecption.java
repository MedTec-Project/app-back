package br.medtec.exceptions;

import br.medtec.utils.JsonUtils;
import br.medtec.utils.Validations;
import jakarta.ws.rs.WebApplicationException;

public class MEDBadRequestExecption extends WebApplicationException {
    public MEDBadRequestExecption(String message) {
        super(message);
    }
}
