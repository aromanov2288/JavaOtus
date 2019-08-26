package ru.romanov.hw04logging.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;

public class MethodSignature {

    private final String name;

    private final Class<?>[] parameters;

    public MethodSignature(Method method) {
        this.name = method.getName();
        this.parameters = method.getParameterTypes();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MethodSignature that = (MethodSignature) o;
        return Objects.equals(name, that.name) &&
                Arrays.equals(parameters, that.parameters);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(parameters);
        return result;
    }
}
