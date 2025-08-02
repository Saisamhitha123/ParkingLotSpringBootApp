package com.example.demo.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {
        return value != null && value ? "true" : "false";
    }

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "true".equalsIgnoreCase(value);
    }

}
