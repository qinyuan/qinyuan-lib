package com.qinyuan.lib.lang;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

/**
 * Test RandomUtils
 * Created by qinyuan on 15-4-4.
 */
public class RandomUtilsTest {

    @Test
    public void testGetOne() {
        assertThat(RandomUtils.getOne(new ArrayList())).isNull();

        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5);
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 100; i++) {
            set.add(RandomUtils.getOne(list));
        }
        assertThat(set).containsAll(list).hasSameSizeAs(list);
    }

    @Test
    public void testDisorganizeOrder() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        for (int i = 0; i < 10; i++) {
            assertThat(RandomUtils.disorganizeOrder(list)).hasSameSizeAs(list).containsAll(list).isNotEqualTo(list);
        }
        System.out.println(RandomUtils.disorganizeOrder(list));
    }

    @Test
    public void testGetOneKey() {
        Map<String, String> map = new HashMap<>();
        assertThat(RandomUtils.getOneKey(map)).isNull();

        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");

        Set<String> set = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            set.add(RandomUtils.getOneKey(map));
        }
        Set<String> keySet = map.keySet();
        assertThat(keySet).containsAll(set);
        assertThat(set).containsAll(keySet);
    }

    @Test
    public void testRemoveOne() {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("Key3", "value3");

        RandomUtils.removeOne(map);
        assertThat(map).hasSize(2);
        System.out.println(map);

        RandomUtils.removeOne(map);
        assertThat(map).hasSize(1);
        System.out.println(map);

        RandomUtils.removeOne(map);
        assertThat(map).isEmpty();
        System.out.println(map);

        RandomUtils.removeOne(map);
        assertThat(map).isEmpty();
        System.out.println(map);

        map = new HashMap<>();
        map.put(null, "value");
        System.out.println(map.size());
        RandomUtils.removeOne(map);
        System.out.println(map.size());
    }

    @Test
    public void testSubList() {
        List<Integer> list = Lists.newArrayList(1, 2, 3, 4, 5, 6);
        List<Integer> subList;
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            subList = RandomUtils.subList(list, 3);
            assertThat(subList).hasSize(3);
            set.addAll(subList);
        }
        assertThat(set).hasSameSizeAs(list);

        try {
            RandomUtils.subList(list, list.size() + 1);
            fail("there should be exception thrown here");
        } catch (Exception e) {
            // nothing to do
        }

        for (int i = 0; i < 5; i++) {
            subList = RandomUtils.subList(list, list.size(), false, false);
            assertThat(subList).hasSameSizeAs(list).containsAll(list).isNotEqualTo(list);

            subList = RandomUtils.subList(list, list.size(), false, true);
            assertThat(subList).isEqualTo(list);
        }

        subList = RandomUtils.subList(list, list.size() + 1, true, true);
        System.out.println("ordered repeatable subList: " + subList);

        subList = RandomUtils.subList(list, list.size() + 1, true, false);
        System.out.println("unordered repeatable subList: " + subList);
    }

    @Test
    public void testNextIntegers() throws Exception {
        List<Integer> integers = RandomUtils.nextIntegers(1, 3, 3);
        assertThat(integers).containsExactly(1, 2, 3);

        try {
            RandomUtils.nextIntegers(1, 3, 4);
            fail("there should be exception thrown here");
        } catch (Exception e) {
            // nothing to do
        }

        Set<Integer> integerSet = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            integers = RandomUtils.nextIntegers(1, 5, 3);
            assertThat(integers).hasSize(3);
            integerSet.addAll(integers);
        }
        assertThat(integerSet).hasSize(5);

        System.out.print("unrepeatable unsorted integers: ");
        IterationUtils.repeat(4, new IterationUtils.Repeatable() {
            @Override
            public void run() {
                System.out.print(RandomUtils.nextIntegers(1, 5, 3, false, false) + " ");
            }
        });

        System.out.print("\nrepeatable unsorted integers: ");
        IterationUtils.repeat(4, new IterationUtils.Repeatable() {
            @Override
            public void run() {
                System.out.print(RandomUtils.nextIntegers(1, 5, 3, true, false) + " ");
            }
        });

        System.out.print("\nunrepeatable sorted integers: ");
        IterationUtils.repeat(4, new IterationUtils.Repeatable() {
            @Override
            public void run() {
                System.out.print(RandomUtils.nextIntegers(1, 5, 3, false, true) + " ");
            }
        });

        System.out.print("\nrepeatable sorted integers: ");
        IterationUtils.repeat(4, new IterationUtils.Repeatable() {
            @Override
            public void run() {
                System.out.print(RandomUtils.nextIntegers(1, 5, 3, true, true) + " ");
            }
        });
    }
}
