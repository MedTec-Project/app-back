package br.medtec.exceptions;

import br.medtec.utils.ResponseUtils;
import br.medtec.utils.Validacao;
import br.medtec.utils.Validcoes;
import jakarta.ws.rs.WebApplicationException;

public class MEDBadRequestExecption extends WebApplicationException {
    public MEDBadRequestExecption(Validacao validacao){
        super(ResponseUtils.badRequest(validacao));
    }

    public MEDBadRequestExecption(String mensagem){
        super(ResponseUtils.badRequest(mensagem));
    }



}
