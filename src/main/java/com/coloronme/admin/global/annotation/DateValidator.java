package com.coloronme.admin.global.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateValidator implements ConstraintValidator<ValueOfDate, String> {

    private boolean isTo;
    private boolean isFrom;
    private LocalDate from;
    private LocalDate to;

    @Override
    public void initialize(ValueOfDate constraintAnnotation) {
        isFrom = constraintAnnotation.isFrom();
        isTo = constraintAnnotation.isTo();
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext context) {
        if(!date.isEmpty()) {
            try {
                LocalDate parseDate = LocalDate.from(LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd")));

                if(isFrom) {
                    from = parseDate;
                } else {
                    to = parseDate;
                }

                int year = parseDate.getYear();
                int day = parseDate.getDayOfMonth();

                if (year < 1900) {
                    return false;
                }

                if (day > parseDate.lengthOfMonth()) {
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