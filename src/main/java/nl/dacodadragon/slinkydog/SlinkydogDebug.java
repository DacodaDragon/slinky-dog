package nl.dacodadragon.slinkydog;

import java.util.logging.Logger;

import javax.swing.text.BadLocationException;

import org.bukkit.plugin.Plugin;

public final class SlinkydogDebug {
	private static Logger debugLogger;

	public static void setLogTarget(Logger logger) {
		debugLogger = logger;
	}

	public static void error(String message) {
		debugLogger.severe(message);
	}

	public static void warning(String message) {
		debugLogger.warning(message);
	}

	public static void log(String message) {
		debugLogger.info(message);
	}

	public static void detail(String message) {
		debugLogger.fine(message);
	}

	public static void detailer(String message) {
		debugLogger.finer(message);
	}

	public static void detailest(String message) {
		debugLogger.finest(message);
	}

	public static void softNullAssert(Object object, String message) {
		if (object == null)
			error(message);
	}

	public static void assertEqualType(Object a, Object b) {
		assertEqualType(a.getClass(), b.getClass());
	}

	public static void assertEqualType(Class<?> a, Class<?> b){
		if (!a.equals(b))
			throwNonEqualType(a, b);
	}

    private static void throwNonEqualType(Class<?> originalType, Class<?> targetType){
        throw new RuntimeException("Type  " + originalType + " does not equal " + targetType);
    }
}
