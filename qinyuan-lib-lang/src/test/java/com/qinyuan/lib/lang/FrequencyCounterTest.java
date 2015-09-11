package com.qinyuan.lib.lang;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FrequencyCounterTest {
    @Test
    public void testTop() throws Exception {
        FrequencyCounter counter = new FrequencyCounter();

        counter.add("1");
        counter.add("1");
        counter.add("2");
        counter.add("2");
        counter.add("2");
        counter.add("3");
        counter.add("3");
        counter.add("4");

        assertThat(counter.top(0)).isEmpty();
        assertThat(counter.top(1)).containsExactly("2");
        assertThat(counter.top(2)).containsExactly("2");
        assertThat(counter.top(3)).containsExactly("2", "3", "1");
        assertThat(counter.top(4)).containsExactly("2", "3", "1", "4");
        assertThat(counter.top(5)).containsExactly("2", "3", "1", "4");

        // test exclude value
        counter = new FrequencyCounter();
        counter.addExclude(5);
        counter.add(2);
        counter.add(2);
        counter.add(3);
        counter.add(5);
        counter.add(5);
        assertThat(counter.top(1)).containsExactly(2);
        assertThat(counter.top(2)).containsExactly(2, 3);
        assertThat(counter.top(3)).containsExactly(2, 3);
    }
}
