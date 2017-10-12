package converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.core.convert.converter.GenericConverter;
import org.springframework.core.convert.support.GenericConversionService;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * <p>Title:MyConversionService </p>
 * <p>Description:</p>
 * Created with IntelliJ IDEA.
 * User: qxy
 * Date: 2017/10/12
 * Time: 14:33
 */
public class MyConversionService implements ConversionService {

    @Autowired
    private GenericConversionService conversionService;
    private Set<?> converters;

    @PostConstruct
    public void afterPropertiesSet() {
        if (converters != null) {
            for (Object converter : converters) {
                if (converter instanceof Converter<?, ?>) {
                    conversionService.addConverter((Converter<?, ?>)converter);
                } else if (converter instanceof ConverterFactory<?, ?>) {
                    conversionService.addConverterFactory((ConverterFactory<?, ?>)converter);
                } else if (converter instanceof GenericConverter) {
                    conversionService.addConverter((GenericConverter)converter);
                }
            }
        }
    }

    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    public boolean canConvert(TypeDescriptor sourceType,
                              TypeDescriptor targetType) {
        return conversionService.canConvert(sourceType, targetType);
    }

    public <T> T convert(Object source, Class<T> targetType) {
        return conversionService.convert(source, targetType);
    }

    public Object convert(Object source, TypeDescriptor sourceType,
                          TypeDescriptor targetType) {
        return conversionService.convert(source, sourceType, targetType);
    }

    public Set<?> getConverters() {
        return converters;
    }

    public void setConverters(Set<?> converters) {
        this.converters = converters;
    }

}
