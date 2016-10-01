package ru.mail.park.main

import ru.mail.park.services.SessionService

/**
 * Created by kirrok on 01.10.16.
 */
class SessionServiceTest {
    private SessionService sessionService;

    @Before
    public void setup() {
        sessionService = new SessionService();
    }
    @Test
    public void ensureExixts() {

    }
}
