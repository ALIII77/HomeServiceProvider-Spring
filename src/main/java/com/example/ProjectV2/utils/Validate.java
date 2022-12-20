package com.example.ProjectV2.utils;

import jakarta.validation.Validator;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

public class Validate {
    private static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static Validator getValidator() {
        return validatorFactory.getValidator();
    }
}
