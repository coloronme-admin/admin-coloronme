package com.coloronme.admin.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<ValueOfDate, String> {

    private boolean isTo;
    private String message;

    @Override
    public void initialize(ValueOfDate constraintAnnotation) {
        isTo = constraintAnnotation.isTo();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if(!date.isEmpty()) {
            try {
                LocalDate parseDate = LocalDate.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                int year = parseDate.getYear();
                int month = parseDate.getMonthValue();
                int day = parseDate.getDayOfMonth();

                if (year < 1900) {
                    return false;
                }
                if (month < 1 || month > 12) {
                    return false;
                }
                if (day < 1 || day > parseDate.lengthOfMonth()) {
                    return false;
                }

                if (isTo && parseDate.isAfter(LocalDate.now())) {
                    context.disableDefaultConstraintViolation();
                    context.buildConstraintViolationWithTemplate("to는 오늘 날짜보다 이후일 수 없습니다.")
                            .addConstraintViolation();
                    return false;
                }
            } catch (DateTimeException e) {
                return false;
            }
        }
        return true;
    }
}