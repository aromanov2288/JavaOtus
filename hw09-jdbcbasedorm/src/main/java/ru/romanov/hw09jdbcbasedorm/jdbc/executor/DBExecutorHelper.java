package ru.romanov.hw09jdbcbasedorm.jdbc.executor;

import javafx.util.Pair;
import ru.romanov.hw09jdbcbasedorm.api.annotation.Entity;
import ru.romanov.hw09jdbcbasedorm.api.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBExecutorHelper {

    Map<Class, String> permissibleTypes = new HashMap<>();

    public DBExecutorHelper() {
        permissibleTypes.put(byte.class, "TINYINT");
        permissibleTypes.put(Byte.class, "TINYINT");
        permissibleTypes.put(short.class, "SMALLINT");
        permissibleTypes.put(Short.class, "SMALLINT");
        permissibleTypes.put(int.class, "INT");
        permissibleTypes.put(Integer.class, "INT");
        permissibleTypes.put(long.class, "BIGINT");
        permissibleTypes.put(Long.class, "BIGINT");

        permissibleTypes.put(boolean.class, "BOOLEAN");
        permissibleTypes.put(Boolean.class, "BOOLEAN");
        permissibleTypes.put(char.class, "VARCHAR (255)");
        permissibleTypes.put(Character.class, "VARCHAR (255)");

        permissibleTypes.put(float.class, "REAL");
        permissibleTypes.put(Float.class, "REAL");
        permissibleTypes.put(double.class, "DOUBLE");
        permissibleTypes.put(Double.class, "DOUBLE");

        permissibleTypes.put(String.class, "VARCHAR (255)");
    }

    //CREATE TABLE table_name (column_1 type_1 auto_increment,  column_2 type_2, column_2 type_2);
    public String createTableSqlStr(Class entityClass) throws DBExecutorHelperException {
        checkIsEntityClassValid(entityClass);
        StringBuilder builder = new StringBuilder("CREATE TABLE ").append(entityClass.getSimpleName()).append(" (");
        boolean flag = false;

        for (Field field : entityClass.getDeclaredFields()) {
            if (flag) {
                builder.append(", ");
            }
            flag = true;
            builder.append(field.getName()).append(" ").append(permissibleTypes.get(field.getType()));
            if (field.isAnnotationPresent(Id.class)) {
                builder.append(" AUTO_INCREMENT");
            }
        }
        return builder.append(");").toString();
    }

    public String dropTableSqlStr(Class entityClass) throws DBExecutorHelperException {
        checkIsEntityClassValid(entityClass);
        return "DROP TABLE IF EXISTS " + entityClass.getSimpleName() + ";";
    }

    //INSERT INTO table_name (column_1, column_2, column_3) VALUES (?, ?, ?);
    public Pair<String, List<String>> insertSqlData(Object entity) throws DBExecutorHelperException {
        Class entityClass = entity.getClass();
        checkIsEntityClassValid(entityClass);

        StringBuilder builderFirst = new StringBuilder("INSERT INTO ").append(entityClass.getSimpleName()).append(" (");
        StringBuilder builderSecond = new StringBuilder("VALUES (");
        List<String> paramList = new ArrayList<>();
        boolean flag = false;

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                if (getFieldLongValue(entity, field) != 0) {
                    throw new DBExecutorHelperException("Нельзя сохранять сущность с заранее заданным идентификатором");
                } else {
                    continue;
                }
            }
            if (flag) {
                builderFirst.append(", ");
                builderSecond.append(", ");
            }
            flag = true;
            builderFirst.append(field.getName());
            builderSecond.append("?");
            paramList.add(getFieldValue(entity, field));
        }
        builderFirst.append(") ");
        builderSecond.append(");");
        return new Pair<>(builderFirst.toString() + builderSecond.toString(), paramList);
    }

    //UPDATE table_name SET column_1 = ?, column_2 = ?, column_3 = ? WHERE id = ?;
    public Pair<String, List<String>> updateSqlData(Object entity) throws DBExecutorHelperException {
        Class entityClass = entity.getClass();
        checkIsEntityClassValid(entityClass);

        StringBuilder builderFirst = new StringBuilder("UPDATE ").append(entityClass.getSimpleName()).append(" SET ");
        StringBuilder builderSecond = new StringBuilder(" WHERE ");
        List<String> paramList = new ArrayList<>();
        Long id = null;
        boolean flag = false;

        for (Field field : entityClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                builderSecond.append(field.getName());
                id = getFieldLongValue(entity, field);
            } else {
                if (flag) {
                    builderFirst.append(", ");
                }
                flag = true;
                builderFirst.append(field.getName()).append(" = ?");
                paramList.add(getFieldValue(entity, field));
            }
        }
        builderSecond.append(" = ?;");
        paramList.add(id.toString());
        return new Pair<>(builderFirst.toString() + builderSecond.toString(), paramList);
    }

    //SELECT column_1, column_2, column_3 FROM table_name WHERE id = ?;
    public String loadByIdSqlData(Class entityClazz) throws DBExecutorHelperException {
        checkIsEntityClassValid(entityClazz);

        StringBuilder builderFirst = new StringBuilder("SELECT ");
        StringBuilder builderSecond = new StringBuilder("WHERE ");
        boolean flag = false;

        for (Field field : entityClazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                builderSecond.append(field.getName());
            }
            if (flag) {
                builderFirst.append(", ");
            }
            flag = true;
            builderFirst.append(field.getName());
        }
        builderFirst.append(" FROM ").append(entityClazz.getSimpleName()).append(" ");
        builderSecond.append(" = ?;");
        return builderFirst.toString() + builderSecond.toString();
    }

    public  Object createInstanceAndSetData(Class entityClass, ResultSet resultSet) throws DBExecutorHelperException {
        checkIsEntityClassValid(entityClass);

        Constructor constructor;
        Object object;
        try {
            constructor = entityClass.getConstructor();
            object = constructor.newInstance();
        } catch (NoSuchMethodException e) {
            throw new DBExecutorHelperException("Класс должен содержать конструктор без параметров!");
        } catch (Exception e) {
            throw new DBExecutorHelperException(e);
        }
        for (Field field : entityClass.getDeclaredFields()) {
            boolean accessibleFlag = false;
            if (!field.trySetAccessible()) {
                field.setAccessible(true);
                accessibleFlag = true;
            }
            try {
                field.set(object, resultSet.getObject(field.getName()));
            } catch (Exception e) {
                throw new DBExecutorHelperException(e);
            }
            if (accessibleFlag) {
                field.setAccessible(false);
            }
        }
        return object;
    }

    private String getFieldValue(Object entity, Field field) throws DBExecutorHelperException {
        String value;
        boolean accessibleFlag = false;
        try {
            if (!field.trySetAccessible()) {
                field.setAccessible(true);
                accessibleFlag = true;
            }
            value = field.get(entity).toString();
        } catch (IllegalAccessException e) {
            throw new DBExecutorHelperException(e);
        } finally {
            if (accessibleFlag) {
                field.setAccessible(false);
            }
        }
        return value;
    }

    private Long getFieldLongValue(Object entity, Field field) throws DBExecutorHelperException {
        Long value;
        boolean accessibleFlag = false;
        try {
            if (!field.trySetAccessible()) {
                field.setAccessible(true);
                accessibleFlag = true;
            }
            value = field.getLong(entity);
        } catch (IllegalAccessException e) {
            throw new DBExecutorHelperException(e);
        } finally {
            if (accessibleFlag) {
                field.setAccessible(false);
            }
        }
        return value;
    }

    private void checkIsEntityClassValid(Class entityClass) throws DBExecutorHelperException {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new DBExecutorHelperException("Класс должен быть помечен аннотацией @Entity!");
        } else {
            int idCount = 0;
            Class idClass = null;
            for (Field field : entityClass.getDeclaredFields()) {
                if (!permissibleTypes.keySet().contains(field.getType())) {
                    throw new DBExecutorHelperException("Класс содержит не допустимые типы!");
                } else if (field.isAnnotationPresent(Id.class)) {
                    idCount++;
                    idClass = field.getType();
                }
            }
            if (idCount != 1) {
                throw new DBExecutorHelperException("В классе строго одно поле должно быть помечено аннотацией @Id!");
            } else if (!(idClass.equals(long.class) || idClass.equals(Long.class))) {
                throw new DBExecutorHelperException("Поле Id должно быть типа long либо Long!");
            }
        }
    }
}
