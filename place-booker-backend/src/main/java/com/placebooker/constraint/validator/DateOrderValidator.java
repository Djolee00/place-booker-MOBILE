package com.placebooker.constraint.validator;

import com.placebooker.constraint.DateOrder;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateOrderValidator implements ConstraintValidator<DateOrder, Object> {

  private String dateFromField;
  private String dateToField;

  @Override
  public void initialize(DateOrder constraintAnnotation) {
    dateFromField = constraintAnnotation.dateFromField();
    dateToField = constraintAnnotation.dateToField();
  }

  @Override
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    LocalDate dateFrom = (LocalDate) getValue(value, dateFromField);
    LocalDate dateTo = (LocalDate) getValue(value, dateToField);

    return dateFrom == null || dateTo == null || !dateFrom.isAfter(dateTo);
  }

  private Object getValue(Object object, String fieldName) {
    try {
      Field field = object.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(object);
    } catch (Exception e) {
      throw new IllegalArgumentException("Failed to retrieve property value: " + fieldName, e);
    }
  }
}
