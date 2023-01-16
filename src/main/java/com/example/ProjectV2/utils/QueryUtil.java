package com.example.ProjectV2.utils;

import com.example.ProjectV2.base.BaseEntity;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class QueryUtil {


    public static <E extends BaseEntity<?>> void checkEntity(E e) {
        Set<ConstraintViolation<E>> constraintViolations = Validate.getValidator().validate(e);
        if (!constraintViolations.isEmpty()) {
            throw new CustomizedIllegalArgumentException(constraintViolations.toString());
        }
    }



}
