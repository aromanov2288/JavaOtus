package ru.romanov.hw03testframework.framework;

import ru.romanov.hw03testframework.framework.exception.TestFrameworkException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class TestFramework {

    public static void runTests(String... classNames) throws Exception {
        for (String className : classNames) {
            TestClassComponentsBuilder builder = new TestClassComponentsBuilder(className);
            TestClassComponents components = builder.build();
            invokeMethod(components.getBeforeAllMethod(), null);
            Map<Method, Class> expectedExceptionMap = components.getExpectedExceptionMap();
            int testCount = 0;

            for (Method method : components.getTestMethodList()) {
                testCount++;
                System.out.println("Тест № " + testCount + " (" + method.getName() + ")");

                Object testClass = components.getEmptyConstructor().newInstance();
                Class expectedExceptionClass = expectedExceptionMap.get(method);
                invokeMethod(components.getBeforeEachMethod(), testClass);

                try {
                    method.invoke(testClass);
                    if (expectedExceptionMap.containsKey(method)){
                        throw new TestFrameworkException("Exception " + expectedExceptionClass.getName()
                                + " was expected but was not thrown");
                    }
                }  catch (TestFrameworkException customException) {
                    System.out.println("Expected exception: " + expectedExceptionClass.getName() + ". Actual exception: null.");
                    continue;
                } catch (Exception e) {
                    Class invokedExceptionClass = e.getCause().getClass();
                    if (expectedExceptionClass == null) {
                        System.out.println("Test failed with error:");
                        System.out.println("Expected exception: null Actual exception: " + invokedExceptionClass.getName());
                    } else if (!expectedExceptionClass.equals(invokedExceptionClass)) {
                        System.out.println("Test failed with error:");
                        System.out.println("Expected exception: " + expectedExceptionClass +
                                ". Actual exception: " + invokedExceptionClass);
                    }
                    continue;
                }

                invokeMethod(components.getAfterEachMethod(), testClass);
                System.out.println("Test passes successfully!");
            }

            invokeMethod(components.getAfterAllMethod(), null);
        }
    }

    private static void invokeMethod(Method method, Object object) throws InvocationTargetException, IllegalAccessException {
        if (method != null) {
            method.invoke(object);
        }
    }
}
