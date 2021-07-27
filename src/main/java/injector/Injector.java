package injector;

import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.TooManyConstructorsException;

public interface Injector {
    <T> Provider<T> getProvider(Class<T> type) throws BindingNotFoundException, ConstructorNotFoundException, TooManyConstructorsException;

    <T> void bind(Class<T> intf, Class<? extends T> impl);

    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl);
}
