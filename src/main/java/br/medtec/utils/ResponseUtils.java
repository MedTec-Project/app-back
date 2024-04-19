package br.medtec.utils;

import jakarta.ws.rs.core.Response;

public class ResponseUtils {

        public static Response created(Object object){
            return Response.status(Response.Status.CREATED).entity(object).build();
        }

        public static Response ok(Object object){
            return Response.status(Response.Status.OK).entity(object).build();
        }

        public static Response notFound(Object object){
            return Response.status(Response.Status.NOT_FOUND).entity(object).build();
        }

        public static Response badRequest(Object object){
            return Response.status(Response.Status.BAD_REQUEST).entity(object).build();
        }

        public static Response badRequest(String message){
            return Response.status(Response.Status.BAD_REQUEST).entity(message).build();
        }

        public static Response forbidden(Object object){
            return Response.status(Response.Status.FORBIDDEN).build();
        }

         public static Response internalServerError(String message, Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(message).build();
        }
}
