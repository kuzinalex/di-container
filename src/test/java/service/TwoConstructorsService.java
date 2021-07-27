package service;

import annotation.Inject;
import dao.TestDao;

public class TwoConstructorsService implements Service {
    private TestDao testDao;
    private ServiceImpl eventService;

    @Inject
    public TwoConstructorsService(TestDao testDao) {
        this.testDao = testDao;
    }

    @Inject
    public TwoConstructorsService(ServiceImpl eventService) {
        this.eventService = eventService;
    }
}
