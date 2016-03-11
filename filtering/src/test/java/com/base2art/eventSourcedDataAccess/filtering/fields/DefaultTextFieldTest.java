package com.base2art.eventSourcedDataAccess.filtering.fields;

import com.base2art.eventSourcedDataAccess.filtering.TextField;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultTextFieldTest {

    @Test
    public void testIn() throws Exception {

        TextField field = new DefaultTextField();
        field.inSet("abc");
        assertThat(field.in().get().length).isEqualTo(1);
        field.inSet();
        assertThat(field.in().isPresent()).isFalse();
    }

    @Test
    public void testIsNullOrEmpty() throws Exception {

        DefaultTextField field = new DefaultTextField();

        assertThat(field.isNotNullOrEmpty().isPresent()).isEqualTo(false);

        field.requireIsNotNullOrEmpty();
        assertThat(field.isNotNullOrEmpty().get()).isEqualTo(true);

        field.isNotNullOrEmpty(false);
        assertThat(field.isNotNullOrEmpty().isPresent()).isEqualTo(false);
    }

//    @Test
//    public void testBetween() throws Exception {
//
//        DefaultTextField field = new DefaultTextField();
//
//        assertThat(field.between()).isEqualTo(null);
//
//        field.between(Between.of("x", "y"));
//        assertThat(field.between().getLower()).isEqualTo("x");
//        assertThat(field.between().getUpper()).isEqualTo("y");
//
//        field.between("a", "b");
//        assertThat(field.between().getLower()).isEqualTo("a");
//        assertThat(field.between().getUpper()).isEqualTo("b");
//    }
//
//    @Test
//    public void testBetweenValidation() throws Exception {
//
//        DefaultTextField field = new DefaultTextField();
//
//        Between.of("x", "y");
//
//        Between.of("x", "x");
//
//        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
//                  .isThrownBy(() -> Between.of(0, -1));
//    }
}