package nl.dacodadragon.slinkydog.configuration;

import nl.dacodadragon.slinkydog.configuration.exceptions.InvalidValueException;
import nl.dacodadragon.slinkydog.utility.EnumUtil;

import java.util.Arrays;
import java.util.List;

public final class ConfigurationValueParser {

	public static Object parse(ParseContext context) {
		Class<?> targetType = context.getExpectedType();

		if (targetType.equals(String.class))
			return context.getValue();

		if (targetType.equals(int.class) || targetType.equals(Integer.class))
			return parseInt(context);

		if (targetType.equals(double.class) || targetType.equals(Double.class))
			return parseDouble(context);

		if (targetType.equals(float.class) || targetType.equals(Float.class))
			return parseFloat(context);

		if (targetType.equals(boolean.class) || targetType.equals(Boolean.class))
			return parseBoolean(context);

		if (targetType.isEnum())
			return parseEnumValue(context);

		if (context.isCollection()){
			return parse(context.sameContextWithType(context.collectionElementType()));
		}

		throw new RuntimeException("Cannot parse type " + targetType.getName());

	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static Object parseEnumValue(ParseContext context) {
		try {
			return Enum.valueOf((Class) context.getExpectedType(), EnumUtil.lookup(context.getValue(), context.getExpectedType()));
		} catch (RuntimeException e) {

			List<String> possibleValues = Arrays.asList(EnumUtil.getNames(context.getExpectedType()));
			throw new InvalidValueException(context, "Value isn't inside of allowed values.", possibleValues);
		}
	}

	private static Object parseFloat(ParseContext context) {
		try {
			return Float.parseFloat(context.getValue());
		} catch (NumberFormatException e) {
			throw new InvalidValueException(context, "Value isn't a decimal.");
		}
	}

	private static Object parseDouble(ParseContext context) {
		try {
			return Double.parseDouble(context.getValue());
		} catch (NumberFormatException e) {
			throw new InvalidValueException(context, "Value isn't a decimal.");
		}
	}

	private static Object parseInt(ParseContext context) {
		try {
			return Integer.parseInt(context.getValue());
		} catch (NumberFormatException e) {
			throw new InvalidValueException(context, "Value isn't a whole number.");
		}
	}

	private static Object parseBoolean(ParseContext context) {
		if (context.getValue().equalsIgnoreCase("true"))
			return true;

		if (context.getValue().equalsIgnoreCase("false"))
			return false;

		throw new InvalidValueException(context, "Value isn't a boolean.", ArgumentConsts.booleanArgs);
	}
}
