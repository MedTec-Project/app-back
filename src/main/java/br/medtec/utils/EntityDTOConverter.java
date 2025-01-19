package br.medtec.utils;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class EntityDTOConverter {

        public static <T, U> U convertToDto(T entity, Class<U> dtoClass) {
            if (entity == null) {
                return null;
            }
            try {
                U dto = dtoClass.getDeclaredConstructor().newInstance();
                Field[] entityFields = entity.getClass().getDeclaredFields();
                for (Field entityField : entityFields) {
                    entityField.setAccessible(true);
                    Field dtoField;
                    try {
                        dtoField = dtoClass.getDeclaredField(entityField.getName());
                        dtoField.setAccessible(true);
                        dtoField.set(dto, entityField.get(entity));
                    } catch (NoSuchFieldException e) {
                        // Campo não encontrado no DTO, ignorar
                    }
                }
                return dto;
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter entidade para DTO", e);
            }
        }

        public static <T, U> T convertToEntity(U dto, Class<T> entityClass) {
            if (dto == null) {
                return null;
            }
            try {
                T entity = entityClass.getDeclaredConstructor().newInstance();
                Field[] dtoFields = dto.getClass().getDeclaredFields();
                for (Field dtoField : dtoFields) {
                    dtoField.setAccessible(true);
                    Field entityField;
                    try {
                        entityField = entityClass.getDeclaredField(dtoField.getName());
                        entityField.setAccessible(true);
                        entityField.set(entity, dtoField.get(dto));
                    } catch (NoSuchFieldException e) {
                        // Campo não encontrado na entidade, ignorar
                    }
                }
                return entity;
            } catch (Exception e) {
                throw new RuntimeException("Erro ao converter DTO para entidade", e);
            }
        }

        public static <T, U> List<U> convertToDtoList(List<T> entities, Class<U> dtoClass) {
            List<U> dtoList = new ArrayList<>();
            for (T entity : entities) {
                dtoList.add(convertToDto(entity, dtoClass));
            }
            return dtoList;
        }

        public static <T, U> List<T> convertToEntityList(List<U> dtos, Class<T> entityClass) {
            List<T> entityList = new ArrayList<>();
            for (U dto : dtos) {
                entityList.add(convertToEntity(dto, entityClass));
            }
            return entityList;
        }
}