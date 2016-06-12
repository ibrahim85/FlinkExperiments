// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package csv.converter;

import de.bytefish.jtinycsvparser.typeconverter.ITypeConverter;
import utils.StringUtils;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class IgnoreMissingValuesConverter<TTargetType> implements ITypeConverter<TTargetType> {

    private ITypeConverter<TTargetType> converter;
    private List<String> missingValueRepresentation;

    public IgnoreMissingValuesConverter(ITypeConverter<TTargetType> converter, String... missingValueRepresentation) {
        this(converter, Arrays.asList(missingValueRepresentation));
    }

    public IgnoreMissingValuesConverter(ITypeConverter<TTargetType> converter, List<String> missingValueRepresentation) {
        this.converter = converter;
        this.missingValueRepresentation = missingValueRepresentation;

    }

    @Override
    public TTargetType convert(final String s) {

        if(StringUtils.isNullOrWhiteSpace(s)) {
            return null;
        }

        boolean isMissingValue = missingValueRepresentation
                .stream()
                .anyMatch(x -> x.equals(s));

        if(isMissingValue) {
            return null;
        }

        return converter.convert(s);
    }

    @Override
    public Type getTargetType() {
        return Float.class;
    }
}
