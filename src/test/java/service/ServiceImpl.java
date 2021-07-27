package service;

import annotation.Inject;
import dao.TestDao;

public class ServiceImpl implements Service {

    private TestDao testDao;

    @Inject
    public ServiceImpl(TestDao testDao) {
        this.testDao = testDao;
    }
}
