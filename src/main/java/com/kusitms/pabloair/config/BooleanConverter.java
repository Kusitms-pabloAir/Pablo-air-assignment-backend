package com.kusitms.pabloair.config;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean attribute) {
        return (attribute != null&& attribute) ? "True" : "False";
    }

    @Override
    public Boolean convertToEntityAttribute(String yn) {
        return "True".equalsIgnoreCase(yn);
    }
}