package ru.softtrack.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(Class<?> entityClass) {
        super(String.format("%s not found", entityClass.getSimpleName()));
    }
}
