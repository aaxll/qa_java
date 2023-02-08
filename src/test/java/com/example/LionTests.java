package com.example;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.hamcrest.Matcher;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Enclosed.class)
public class LionTests {

    abstract static class SharedSetUp {
        @Rule //initMocks
        public MockitoRule rule = MockitoJUnit.rule();

        @Rule
        public ExpectedException exceptionRule = ExpectedException.none();

        @Mock
        Feline feline;
    }

    @RunWith(Parameterized.class)
    public static class LionParametrizedTests extends SharedSetUp {

        @Parameterized.Parameters(name = "{0}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"Самец", true},
                    {"Самка", false}
            });
        }

        private final String sex;
        private final boolean expected;

        public LionParametrizedTests(String sex, boolean expected) {
            this.sex = sex;
            this.expected = expected;
        }

        @Test
        public void getFoodTest() throws Exception {
            Lion lion = new Lion(sex, feline);
            lion.getFood();
            Mockito.verify(feline).getFood("Хищник");
        }

        @Test
        public void getKittensTest() throws Exception {
            Lion lion = new Lion(sex, feline);
            lion.getKittens();
            Mockito.verify(feline).getKittens();
        }

        @Test
        public void getKittensCountTest() throws Exception {
            Mockito.when(feline.getKittens()).thenReturn(5);
            Lion lion = new Lion(sex, feline);
            assertEquals(5, lion.getKittens());
        }

        @Test
        public void doesHaveManeTest() throws Exception {
            Lion lion = new Lion(sex, feline);
            assertEquals(expected, lion.doesHaveMane());
        }

    }

    public static class LionNotParametrizedTests extends SharedSetUp {

        @Test
        public void lionConstructorException() throws Exception {
            exceptionRule.expect(Exception.class);
            exceptionRule.expectMessage("Используйте допустимые значения пола животного - самей или самка");
            new Lion("?", feline);
        }

    }

}
