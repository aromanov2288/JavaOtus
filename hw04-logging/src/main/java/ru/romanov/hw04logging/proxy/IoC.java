package ru.romanov.hw04logging.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

public class IoC {

    public static <T> T newInstance(Class<T> intClass, Class<? extends T> implClass)
            throws IllegalAccessException, InstantiationException, InvocationTargetException, NoSuchMethodException {
        InvocationHandler handler = new CustomInvocationHandler<>(implClass);
        return (T) Proxy.newProxyInstance(IoC.class.getClassLoader(), new Class<?>[]{intClass}, handler);
    }

    private static class CustomInvocationHandler<T> implements InvocationHandler {

        private T t;

        private Set<MethodSignature> logAnnotatedMethodSet;

        public CustomInvocationHandler(Class<? extends T> implClass)
                throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
            this.t = implClass.getConstructor().newInstance();
            this.logAnnotatedMethodSet = getLogAnnotatedMethodSet(implClass);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (logAnnotatedMethodSet.contains(new MethodSignature(method))) {
                StringBuilder builder = new StringBuilder();
                builder.append("executed method: ").append(method.getName()).append(", params: ");
                int i = 0;
                for (Object arg : args) {
                    builder.append("[").append(i).append("]=").append(arg).append(" ");
                }
                System.out.println(builder.toString());
            }
            return method.invoke(t, args);

        }

        private Set<MethodSignature> getLogAnnotatedMethodSet(Class<? extends T> implClass) {
            Set<MethodSignature> set = new HashSet<>();
            Method[] methods = implClass.getMethods();
            for (Method method : methods) {
                if (method.isAnnotationPresent(LogProxy.class)) {
                    set.add(new MethodSignature(method));
                }
            }
            return set;
        }
    }
}
