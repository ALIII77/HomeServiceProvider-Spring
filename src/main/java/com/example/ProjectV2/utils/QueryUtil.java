package com.example.ProjectV2.utils;

import com.example.ProjectV2.base.BaseEntity;
import com.example.ProjectV2.exception.CustomizedIllegalArgumentException;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.validation.ConstraintViolation;

import java.util.Set;

public class QueryUtil {


    public static <E> E getResultQuery(TypedQuery<E> query) {
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static <E extends BaseEntity<?>> void checkEntity(E e) {
        Set<ConstraintViolation<E>> constraintViolations = Validate.getValidator().validate(e);
        if (!constraintViolations.isEmpty()) {
            throw new CustomizedIllegalArgumentException(constraintViolations.toString());
        }
    }


}
