import dao.EventDAO;
import dao.EventDAOImpl;
import exception.BindingNotFoundException;
import exception.ConstructorNotFoundException;
import exception.TooManyConstructorsException;
import injector.Injector;
import injector.InjectorImpl;
import injector.Provider;
import service.EventService;
import service.EventServiceImpl;

public class Main {
    public static void main(String[] args) throws BindingNotFoundException, ConstructorNotFoundException, TooManyConstructorsException {
        Injector injector=new InjectorImpl();
        injector.bindSingleton(EventDAO.class, EventDAOImpl.class);
        injector.bind(EventService.class, EventServiceImpl.class);

        Provider<EventService> serviceProvider=injector.getProvider(EventService.class);
        EventService eventService=serviceProvider.getInstance();
        EventService eventService1=serviceProvider.getInstance();
        EventService eventService2=serviceProvider.getInstance();
        System.out.println(eventService+" "+eventService1+" "+eventService2);
    }
}
