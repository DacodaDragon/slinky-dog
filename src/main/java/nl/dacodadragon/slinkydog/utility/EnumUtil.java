package nl.dacodadragon.slinkydog.utility;

import java.util.Arrays;

public class EnumUtil {
	@SuppressWarnings("rawtypes")
	public static String[] getNames(Class e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").split(", ");
	}

	@SuppressWarnings("rawtypes")
	public static String[] getLoweredNames(Class e) {
		return Arrays.toString(e.getEnumConstants()).replaceAll("^.|.$", "").toLowerCase().split(", ");
	}

	@SuppressWarnings("rawtypes")
	public static String lookup(String value, Class enumType) {

		for (String enumName : EnumUtil.getNames(enumType)) {
			if (enumName.equalsIgnoreCase(value)) {
				value = enumName;
				break;
			}
		}

		return value;
	}
}
