package ru.romanov.hw03testframework.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestClassComponents {

    private Constructor emptyConstructor;

    private Method beforeAllMethod;

    private Method afterAllMethod;

    private List<Method> testMethodList = new ArrayList<>();

    private Map<Method, Class> expectedExceptionMap = new HashMap<>();

    private Method beforeEachMethod;

    private Method afterEachMethod;

    public Constructor getEmptyConstructor() {
        return emptyConstructor;
    }

    public void setEmptyConstructor(Constructor emptyConstructor) {
        this.emptyConstructor = emptyConstructor;
    }

    public Method getBeforeAllMethod() {
        return beforeAllMethod;
    }

    public void setBeforeAllMethod(Method method) {
        this.beforeAllMethod = method;
    }

    public Method getAfterAllMethod() {
        return afterAllMethod;
    }

    public void setAfterAllMethod(Method method) {
        this.afterAllMethod = method;
    }

    public List<Method> getTestMethodList() {
        return testMethodList;
    }

    public void addTestMethod(Method method) {
        this.testMethodList.add(method);
    }

    public Map<Method, Class> getExpectedExceptionMap() {
        return expectedExceptionMap;
    }

    public void addExpectedException(Method method, Class expectedExpectedExceptionClass) {
        this.expectedExceptionMap.put(method, expectedExpectedExceptionClass);
    }

    public Method getBeforeEachMethod() {
        return beforeEachMethod;
    }

    public void setBeforeEachMethod(Method method) {
        this.beforeEachMethod = method;
    }

    public Method getAfterEachMethod() {
        return afterEachMethod;
    }

    public void setAfterEachMethod(Method method) {
        this.afterEachMethod = method;
    }
}
