package br.medtec.utils;

import jakarta.ws.rs.core.Response;

public class ResponseUtils {

        public static Response created(Object object){
            return Response.status(Response.Status.CREATED).entity(object).build();
        }

        public static Response created(String message){
            return Response.status(Response.Status.CREATED).entity(new Mensagem(message)).build();
        }

        public static Response ok(Object object){
            return Response.status(Response.Status.OK).entity(object).build();
        }

    public static Response ok(String mensagem){
        return Response.status(Response.Status.OK).entity(new Mensagem(mensagem)).build();
    }

        public static Response notFound(Object object){
            return Response.status(Response.Status.NOT_FOUND).entity(object).build();
        }

        public static Response objectbadRequest(Object object){
            return Response.status(Response.Status.BAD_REQUEST).entity(object).build();
        }

        public static Response badRequest(String message){
            return Response.status(Response.Status.BAD_REQUEST).entity(new Mensagem(message)).build();
        }

        public static Response forbidden(Object object){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

         public static Response internalServerError(String message) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new Mensagem(message)).build();
        }

        public static Response deleted(){
            return Response.status(Response.Status.NO_CONTENT).build();
        }

        public static Response unauthorized(){
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
}
