package service;

import annotation.Inject;
import dao.EventDAO;

public class EventServiceImpl implements EventService {

    private EventDAO eventDAO;

    @Inject
    public EventServiceImpl(EventDAO eventDAO) {
        this.eventDAO = eventDAO;
    }
}
