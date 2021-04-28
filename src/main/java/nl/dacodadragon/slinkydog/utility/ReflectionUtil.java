package nl.dacodadragon.slinkydog.utility;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import nl.dacodadragon.slinkydog.SlinkydogDebug;

public class ReflectionUtil {
    public static Class<?> getCollectionElementType(Type type){
        return (Class<?>)((ParameterizedType)type).getActualTypeArguments()[0];
    }

    public static boolean isCollection(Class<?> type) {
        return type.isArray() || List.class.isAssignableFrom(type);
    }

    public static void addToCollection(Object list, Class<?> collectionType, Object value) {
        SlinkydogDebug.assertEqualType(collectionType, value.getClass());

        if (collectionType.equals(String.class)) {
            List<String> stringCollection = (List<String>)list;
            stringCollection.add((String)value);
        }

        throw new RuntimeException("ElementType of " + collectionType.getName() + " is not (yet) supported");
    }

    public static void removedFromCollection(Object list, Class<?> collectionType, Object value) {
        SlinkydogDebug.assertEqualType(collectionType, value.getClass());

        if (collectionType.equals(String.class)) {
            List<String> stringCollection = (List<String>)list;
            stringCollection.remove((String)value);
        }

        throw new RuntimeException("ElementType of " + collectionType.getName() + " is not (yet) supported");
    }

    public static void clearCollection(Object list, Class<?> collectionType) {

        if (collectionType.equals(String.class)) {
            List<String> stringCollection = (List<String>)list;
            stringCollection.clear();
        }

        throw new RuntimeException("ElementType of " + collectionType.getName() + " is not (yet) supported");
    }

}
