package com.base2art.eventSourcedDataAccess.utils;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.stream.Collectors;

import static com.base2art.eventSourcedDataAccess.utils.Reflection.fields;
import static com.base2art.eventSourcedDataAccess.utils.Reflection.getAllFields;
import static org.assertj.core.api.Assertions.assertThat;

public class ReflectionTest {

    @Test
    public void testGetAllFields() throws Exception {

        assertThat(getAllFields(Person.class).length).isEqualTo(1);
        assertThat(getAllFields(Employee.class).length).isEqualTo(2);
        assertThat(getAllFields(Manager.class).length).isEqualTo(3);


        List<Field> fields = fields(Manager.class)
                .filter(x -> !Modifier.isFinal(x.getModifiers()))
                .collect(Collectors.toList());

        assertThat(fields.size()).isEqualTo(2);
    }

    @Test
    public void getFieldData() throws Exception {

        Field[] fields = getAllFields(Manager.class);

        Manager m = new Manager("abc");
        m.setBadgeNumber("BADGE");
        m.setName("Name1");

        fields[0].setAccessible(true);
        fields[1].setAccessible(true);
        fields[2].setAccessible(true);

        assertThat(fields[0].get(m)).isEqualTo("abc");
        assertThat(fields[1].get(m)).isEqualTo("BADGE");
        assertThat(fields[2].get(m)).isEqualTo("Name1");

        fields[0].set(m, fields[0].get(m) + "1");
        fields[1].set(m, fields[1].get(m) + "2");
        fields[2].set(m, fields[2].get(m) + "3");

        assertThat(fields[0].get(m)).isEqualTo("abc1");
        assertThat(fields[1].get(m)).isEqualTo("BADGE2");
        assertThat(fields[2].get(m)).isEqualTo("Name13");
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    private static class Manager extends Employee {

        private final String manager;
    }

    @Data
    @EqualsAndHashCode(callSuper = false)
    private static class Employee extends Person {

        private String badgeNumber;
    }

    @Data
    private static class Person {

        private String name;
    }
}