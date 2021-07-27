package injector;

import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.TooManyConstructorsException;

public interface Provider<T> {
    T getInstance() throws ConstructorNotFoundException, TooManyConstructorsException, BindingNotFoundException;
}
