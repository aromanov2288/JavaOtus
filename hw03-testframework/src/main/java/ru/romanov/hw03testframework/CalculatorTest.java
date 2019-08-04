package ru.romanov.hw03testframework;

import ru.romanov.hw03testframework.framework.annotations.AfterAll;
import ru.romanov.hw03testframework.framework.annotations.AfterEach;
import ru.romanov.hw03testframework.framework.annotations.BeforeAll;
import ru.romanov.hw03testframework.framework.annotations.BeforeEach;
import ru.romanov.hw03testframework.framework.annotations.ExpectedException;
import ru.romanov.hw03testframework.framework.annotations.Test;

public class CalculatorTest {

    private Calculator calculator;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("Начинаем тестирование.");
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("Запускаем тест.");
        calculator = new Calculator();
    }

    @Test
    public void sumTest() throws Exception {
        if (calculator.sum(1, 1) != 2) {
            throw new Exception("Ошибка при суммировании a и b");
        }
    }

    @Test
    public void diffTest() throws Exception {
        if (calculator.diff(3, 2) != 1) {
            throw new Exception("Ошибка при вычитании a из b");
        }
    }

    @Test
    public void divisionTest() throws Exception {
        if (calculator.division(15, 5) != 3) {
            throw new Exception("Ошибка при делении a на b");
        }
    }

    @Test
    @ExpectedException(IllegalArgumentException.class)
    public void divisionByZeroTest() {
        calculator.division(15, 0);
    }

    @Test
    public void multiplicationTest() throws Exception {
        if (calculator.multiplication(10, 10) != 100) {
            throw new Exception("Ошибка при умножении a на b");
        }
    }

    @AfterEach
    public void afterEach() {
        System.out.println("Выполнение теста зевершено.");
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("Выполнение тестов зевершено.");
    }
}
