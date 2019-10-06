package ru.romanov.hw08jsonobjectwriter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class Rson {

    private Set<Class> simpleObjectTypeSet = new HashSet<>(
            Arrays.asList(Byte.class, Short.class, Integer.class, Long.class, Boolean.class, Float.class, Double.class));

    private Set<Class> literalObjectTypeSet = new HashSet<>(
            Arrays.asList(Character.class, String.class));

    public String toJson(Object object) throws IllegalAccessException {
        if (object == null) {
            return "null";
        } else if (object.getClass().isArray()) {
            return arrayToJson(object);
        } else if (object instanceof Collection) {
            return collectionToJson((Collection) object);
        } else if (object instanceof Map) {
            return mapToJson((Map) object);
        } else {
            return objectToJson(object);
        }
    }

    private String arrayToJson(Object array) throws IllegalAccessException {
        if (Array.getLength(array) == 0) {
            return "[]";
        } else {
            StringBuilder builder = new StringBuilder("[").append(toJson(Array.get(array, 0)));
            for (int i = 1; i < Array.getLength(array); i++) {
                builder.append(",").append(toJson(Array.get(array, i)));
            }
            return builder.append("]").toString();
        }
    }

    private String collectionToJson(Collection<?> collection) throws IllegalAccessException {
        if (collection.isEmpty()) {
            return "[]";
        } else {
            StringBuilder builder = new StringBuilder("[");
            boolean flag = false;
            for (Object element : collection) {
                if (flag) {
                    builder.append(",");
                }
                builder.append(toJson(element));
                flag = true;
            }
            return builder.append("]").toString();
        }
    }

    private String mapToJson(Map<?, ?> map) throws IllegalAccessException {
        if (map.isEmpty()) {
            return "{}";
        } else {
            StringBuilder builder = new StringBuilder("{");
            boolean flag = false;
            for (Object key : map.keySet()) {
                Object value = map.get(key);
                if (value != null) {
                    if (flag) {
                        builder.append(",");
                    }
                    flag = true;
                    if (key == null) {
                        builder.append("\"null\":");
                    } else {
                        builder.append("\"").append(key).append("\":");
                    }
                    builder.append(toJson(value));
                }
            }
            return builder.append("}").toString();
        }
    }

    private String objectToJson(Object object) throws IllegalAccessException {
        Class clazz = object.getClass();
        if (simpleObjectTypeSet.contains(clazz) || literalObjectTypeSet.contains(clazz)) {
            return literalObjectTypeSet.contains(clazz) ? "\"" + object + "\"" : object.toString();
        } else {
            StringBuilder builder = new StringBuilder("{");
            Field[] fields = object.getClass().getDeclaredFields();
            boolean flag = false;
            for (int i = 0; i < fields.length; i++) {
                boolean accessFlag = false;
                if (!fields[i].trySetAccessible()) {
                    accessFlag = true;
                    fields[i].setAccessible(true);
                }
                if (fields[i].get(object) != null) {
                    if (flag) {
                        builder.append(",");
                    }
                    flag = true;
                    builder.append("\"").append(fields[i].getName()).append("\":");
                    builder.append(toJson(fields[i].get(object)));
                }
                if (accessFlag) {
                    fields[i].setAccessible(false);
                }
            }
            return builder.append("}").toString();
        }
    }
}
