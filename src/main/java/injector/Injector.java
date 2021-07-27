package injector;

import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.TooManyConstructorsException;

public interface Injector {
    <T> Provider<T> getProvider(Class<T> type) throws BindingNotFoundException, ConstructorNotFoundException, TooManyConstructorsException; //получение инстанса класса со всеми иньекциями по классу интерфейса

    <T> void bind(Class<T> intf, Class<? extends T> impl); //регистрация байндинга по классу интерфейса и его реализации

    <T> void bindSingleton(Class<T> intf, Class<? extends T> impl); //регистрация синглтон класса
}
