package com.tfl.billing;

import org.junit.Test;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class JourneyEventTest {

    private final UUID testCardId = UUID.randomUUID();
    private final UUID testReaderId = UUID.randomUUID();
    private JourneyEvent test = new JourneyEvent(testCardId, testReaderId) {};

    @Test
    public void returnsCorrectTime() {
        assertThat(test.time() <= System.currentTimeMillis(), is(true));
    }

    @Test
    public void returnsCardId() {
        assertThat(test.cardId(), is(testCardId));
    }

    @Test
    public void returnsReaderId() {
        assertThat(test.readerId(), is(testReaderId));
    }
}