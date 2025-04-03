package br.medtec.utils;

import br.medtec.exceptions.MEDBadRequestExecption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Validations {

    private final Logger log = LoggerFactory.getLogger(Validations.class);

    public List<String> validations;

    public Validations() {
        this.validations = new ArrayList<>();
    }

    public Validations(Object object) {
        this.validations = new ArrayList<>();
        log.error("Validations for {}", object.getClass().getName());
    }

    public void add(String message) {
        validations.add(message);
        log.error(message);
    }

    public void throwErrors() {
        if (!validations.isEmpty()) {
            String message = String.join(", ", validations);
            throw new MEDBadRequestExecption(message);
        }
    }
}
