package ru.romanov.hw03testframework.framework;

import ru.romanov.hw03testframework.framework.annotations.AfterAll;
import ru.romanov.hw03testframework.framework.annotations.AfterEach;
import ru.romanov.hw03testframework.framework.annotations.BeforeAll;
import ru.romanov.hw03testframework.framework.annotations.BeforeEach;
import ru.romanov.hw03testframework.framework.annotations.ExpectedException;
import ru.romanov.hw03testframework.framework.annotations.Test;
import ru.romanov.hw03testframework.framework.exception.TestFrameworkException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TestClassComponentsBuilder {

    private String className;

    private TestClassComponents testClassComponents = new TestClassComponents();

    private Set<Class> singleMethodAnnotations = new HashSet<>(
            Arrays.asList(BeforeAll.class, AfterAll.class, Test.class, BeforeEach.class, AfterEach.class));

    private Set<Class> staticMethodAnnotations = new HashSet<>(Arrays.asList(BeforeAll.class, AfterAll.class));

    public TestClassComponentsBuilder(String className) {
        this.className = className;
    }

    public TestClassComponents build() throws Exception {
        Class clazz = Class.forName(className);
        Method[] methods = clazz.getDeclaredMethods();

        findSingleEmptyConstructor(clazz);

        for (Method method : methods) {
            checkMethodAnnotations(method);
            checkMethodSignature(method);
            processMethodAnnotations(method);
        }

        return testClassComponents;
    }

    private void findSingleEmptyConstructor(Class clazz) throws TestFrameworkException {
        Constructor[] constructors = clazz.getConstructors();
        Constructor emptyConstructor = null;

        for (Constructor constructor : constructors) {
            if (constructor.getParameterCount() == 0) {
                emptyConstructor = constructor;
                break;
            }
        }

        if (emptyConstructor == null) {
            throw new TestFrameworkException(
                    "Empty constructor is necessary but not exists in class " + clazz.getName());
        } else {
            int modifier = emptyConstructor.getModifiers();
            if (!Modifier.isPublic(modifier)) {
                throw new TestFrameworkException(
                        "Constructor " + emptyConstructor.getName() + " must be public.");
            } else if (Modifier.isStatic(modifier)) {
                throw new TestFrameworkException(
                        "Constructor " + emptyConstructor.getName() + " must be non-static.");
            } else if (emptyConstructor.getDeclaredAnnotations().length != 0) {
                throw new TestFrameworkException("Empty constructor must not be marked with any annotation.");
            } else {
                testClassComponents.setEmptyConstructor(emptyConstructor);
            }
        }
    }

    private void checkMethodAnnotations(Method method) throws TestFrameworkException {
        Annotation[] annotations = method.getDeclaredAnnotations();
        int annotationsCount = method.getDeclaredAnnotations().length;

        if (annotationsCount > 1) {
            if (annotationsCount > 2) {
                throw new TestFrameworkException(
                        "More than one annotation is not applicable for method " + method.getName() + ".");
            } else {
                Set<Class> aClassSet = new HashSet<>();
                for (Annotation annotation : annotations) {
                    aClassSet.add(annotation.annotationType());
                }

                if (!aClassSet.containsAll(Arrays.asList(Test.class, ExpectedException.class))) {
                    throw new TestFrameworkException(
                            "This combination of two annotations is not applicable for method " + method.getName() + ".");
                }
            }
        } else if (annotationsCount == 1) {
            Class aClass = annotations[0].annotationType();
            if (!singleMethodAnnotations.contains(aClass)) {
                throw new TestFrameworkException(
                        "Method " + method.getName() + " must not be marked with annotation " + aClass.getName() + ".");
            }
        }
    }

    private void checkMethodSignature(Method method) throws TestFrameworkException {
        int modifier = method.getModifiers();
        if (!Modifier.isPublic(modifier)) {
            throw new TestFrameworkException(
                    "Method " + method.getName() + " must be public.");
        } else if (method.getParameterCount() > 0) {
            throw new TestFrameworkException(
                    "Method " + method.getName() + " must has not any parameters.");
        } else if (!method.getReturnType().equals(Void.TYPE)) {
            throw new TestFrameworkException(
                    "Method " + method.getName() + " must return void type.");
        } else {
            for (Annotation annotation : method.getDeclaredAnnotations()) {
                if (!Modifier.isStatic(modifier) && staticMethodAnnotations.contains(annotation.annotationType())) {
                    throw new TestFrameworkException(
                            "Annotation " + annotation.toString() + " is not applicable for non-static method " + method.getName() + ".");
                } else if (Modifier.isStatic(modifier) && !staticMethodAnnotations.contains(annotation.annotationType())) {
                    throw new TestFrameworkException(
                            "Annotation " + annotation.toString() + " is not applicable for static method " + method.getName() + ".");
                }
            }
        }
    }

    private void processMethodAnnotations(Method method) {
        for (Annotation annotation : method.getDeclaredAnnotations()) {
            Class aClass = annotation.annotationType();
            if (aClass.equals(BeforeAll.class)) {
                testClassComponents.setBeforeAllMethod(method);
            } else if (aClass.equals(AfterAll.class)) {
                testClassComponents.setAfterAllMethod(method);
            } else if (aClass.equals(Test.class)) {
                testClassComponents.addTestMethod(method);
            } else if (aClass.equals(ExpectedException.class)) {
                testClassComponents.addExpectedException(method, ((ExpectedException) annotation).value());
            } else if (aClass.equals(BeforeEach.class)) {
                testClassComponents.setBeforeEachMethod(method);
            } else if (aClass.equals(AfterEach.class)) {
                testClassComponents.setAfterEachMethod(method);
            }
        }
    }
}
