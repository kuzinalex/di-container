package injector;

import annotation.Inject;
import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.TooManyConstructorsException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InjectorImpl implements Injector {

    private Map<Class, Class> prototypeBindings = new HashMap<>();
    private Map<Class, Class> singletonBindings = new HashMap<>();
    private Map<Class, Object> singletons = new HashMap<>();

    @Override
    public synchronized <T> Provider<T> getProvider(Class<T> type) throws TooManyConstructorsException {

        boolean isSingleton;
        List<Constructor<?>> annotatedConstructors = new ArrayList<>();
        Class<?> bindingType;

        if (singletons.containsKey(type)) {
            return () -> (T) singletons.get(type);
        }

        if (singletonBindings.get(type) != null) {
            bindingType = singletonBindings.get(type);
            isSingleton = true;
        } else if (prototypeBindings.get(type) != null) {
            bindingType = prototypeBindings.get(type);
            isSingleton = false;
        } else {
            return null;
        }

        for (Constructor<?> con : bindingType.getConstructors()
        ) {
            if (con.isAnnotationPresent(Inject.class)) {
                annotatedConstructors.add(con);
            }
        }

        if (annotatedConstructors.size() > 1) {
            throw new TooManyConstructorsException();
        } else if (annotatedConstructors.isEmpty()) {

            return () -> {
                try {
                    if (singletons.containsKey(type)) {
                        return (T) singletons.get(type);
                    } else if (isSingleton) {
                        singletons.put(type, bindingType.newInstance());
                        return (T) singletons.get(type);
                    } else {
                        return (T) bindingType.newInstance();
                    }
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new ConstructorNotFoundException();
                }
            };
        } else {

            return () -> {
                Constructor<?> constructor = annotatedConstructors.get(0);
                Class<?>[] parameters = constructor.getParameterTypes();
                List<Object> args = new ArrayList<>();

                for (Class<?> param : parameters
                ) {
                    Provider<?> provider = getProvider(param);
                    if (provider == null) {
                        throw new BindingNotFoundException();
                    } else {
                        args.add(provider.getInstance());
                    }
                }
                try {
                    if (singletons.containsKey(type)) {
                        return (T) singletons.get(type);
                    } else
                        if (isSingleton) {
                        singletons.put(type, constructor.newInstance(args.toArray()));
                        return (T) singletons.get(type);
                    } else {
                        return (T) constructor.newInstance(args.toArray());
                    }
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    throw new ConstructorNotFoundException();
                }
            };
        }
    }

    @Override
    public <T> void bind(Class<T> intf, Class<? extends T> impl) {
        prototypeBindings.put(intf, impl);
    }

    @Override
    public <T> void bindSingleton(Class<T> intf, Class<? extends T> impl) {
        singletonBindings.put(intf, impl);
    }
}
