package service;

import dao.TestDao;

public class NoDefaultConstructorService implements Service {
    private TestDao testDao;

    public NoDefaultConstructorService(TestDao testDao) {
        this.testDao = testDao;
    }
}
