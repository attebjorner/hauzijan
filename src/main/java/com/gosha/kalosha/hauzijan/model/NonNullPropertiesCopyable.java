package com.gosha.kalosha.hauzijan.model;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

public interface NonNullPropertiesCopyable
{
    default void copyNonNullProperties(NonNullPropertiesCopyable source)
    {
        if (!this.getClass().isAssignableFrom(source.getClass()))
        {
            throw new IllegalArgumentException("Unable to copy properties from " + source.getClass() + " to " + this.getClass() + ".");
        }

        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = src.getPropertyDescriptors();
        Set<String> nullPropertiesNames = new HashSet<>();
        for (var pd : propertyDescriptors)
        {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null)
            {
                nullPropertiesNames.add(pd.getName());
            }
        }
        String[] nullProperties = nullPropertiesNames.toArray(String[]::new);

        BeanUtils.copyProperties(source, this, nullProperties);
    }
}
