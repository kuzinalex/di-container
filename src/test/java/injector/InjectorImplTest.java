package injector;

import dao.TestDAOImpl;
import dao.TestDao;
import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.TooManyConstructorsException;
import org.junit.jupiter.api.Test;
import service.NoDefaultConstructorService;
import service.Service;
import service.ServiceImpl;
import service.TwoConstructorsService;

import static org.junit.jupiter.api.Assertions.*;

class InjectorImplTest {


    @Test
    void getProviderSuccess() throws ConstructorNotFoundException, TooManyConstructorsException, BindingNotFoundException {
        Injector injector = new InjectorImpl();
        injector.bind(Service.class, ServiceImpl.class);
        injector.bind(TestDao.class, TestDAOImpl.class);

        Provider<Service> serviceProvider = injector.getProvider(Service.class);

        assertNotNull(serviceProvider);
        assertNotNull(serviceProvider.getInstance());
        assertSame(ServiceImpl.class, serviceProvider.getInstance().getClass());
    }


    @Test
    void getBindingNotFoundException() {
        Injector injector = new InjectorImpl();
        injector.bind(Service.class, ServiceImpl.class);

        assertThrows(BindingNotFoundException.class, () -> {
            injector.getProvider(Service.class).getInstance();
        });
    }


    @Test
    void getTooManyConstructorException() {
        Injector injector = new InjectorImpl();
        injector.bind(Service.class, TwoConstructorsService.class);
        injector.bind(TestDao.class, TestDAOImpl.class);

        assertThrows(TooManyConstructorsException.class, () -> {
            injector.getProvider(Service.class);
        });
    }

    @Test
    void getConstructorNotFoundException() {
        Injector injector = new InjectorImpl();
        injector.bind(Service.class, NoDefaultConstructorService.class);

        assertThrows(ConstructorNotFoundException.class, () -> {
            injector.getProvider(Service.class).getInstance();
        });
    }


    @Test
    void singletonAndPrototypesCheck() throws ConstructorNotFoundException, TooManyConstructorsException, BindingNotFoundException {
        Injector injector = new InjectorImpl();
        injector.bind(Service.class, ServiceImpl.class);
        injector.bindSingleton(TestDao.class, TestDAOImpl.class);

        Provider<Service> serviceProvider = injector.getProvider(Service.class);
        Provider<TestDao> daoProvider = injector.getProvider(TestDao.class);

        Service service1 = serviceProvider.getInstance();
        Service service2 = serviceProvider.getInstance();

        TestDao testDao1 = daoProvider.getInstance();
        TestDao testDao2 = daoProvider.getInstance();

        assertNotNull(serviceProvider);
        assertNotNull(serviceProvider.getInstance());
        assertNotNull(daoProvider);
        assertNotNull(daoProvider.getInstance());

        assertNotSame(service1, service2);
        assertSame(testDao1, testDao2);


    }
}