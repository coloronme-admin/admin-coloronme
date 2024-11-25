package com.coloronme.product.personalColor.dto;

public enum PersonalColorGroupEnum {
    SPRING, SUMMER, FALL, WINTER;

    public String toLowerCase() {
        return this.name().toLowerCase();
    }
}
