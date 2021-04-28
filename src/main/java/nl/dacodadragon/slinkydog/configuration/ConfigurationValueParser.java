package nl.dacodadragon.slinkydog.configuration;

import nl.dacodadragon.slinkydog.configuration.exceptions.InvalidValueException;
import nl.dacodadragon.slinkydog.utility.EnumUtil;

import java.util.Arrays;
import java.util.List;

public final class ConfigurationValueParser {

	public static Object parse(ParseContext context) {
		if (context.getExpectedType().equals(String.class))
			return context.getValue();

		if (context.getExpectedType().equals(int.class) || context.getExpectedType().equals(Integer.class))
			return parseInt(context);

		if (context.getExpectedType().equals(double.class) || context.getExpectedType().equals(Double.class))
			return parseDouble(context);

		if (context.getExpectedType().equals(float.class) || context.getExpectedType().equals(Float.class))
			return parseFloat(context);

		if (context.getExpectedType().equals(boolean.class) || context.getExpectedType().equals(Boolean.class))
			return parseBoolean(context);

		if (context.getExpectedType().isEnum())
			return parseEnumValue(context);

		if (context.isCollection()){
			return parse(context.sameContextWithType(context.collectionElementType()));
		}

		throw new RuntimeException("Cannot parse type " + context.getExpectedType().getName());

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
