package br.medtec.exceptions;

import jakarta.ws.rs.WebApplicationException;

public class MEDBadRequestExecption extends WebApplicationException {
    public MEDBadRequestExecption(String message) {
        super(message);
    }
}
