package com.coloronme.product.personalColor.dto;

public enum PersonalColorGroupEnum {
    SPRING, SUMMER, AUTUMN, WINTER;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
