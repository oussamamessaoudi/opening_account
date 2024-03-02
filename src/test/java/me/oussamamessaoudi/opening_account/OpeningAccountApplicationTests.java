package me.oussamamessaoudi.opening_account;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class OpeningAccountApplicationTests {

    @Test
    void contextLoads() {
        assertTrue(true);
    }

    @Test
    public void whenEmptyString_thenAccept() {
        var palindromeTester = new OpeningAccountApplication();
        assertTrue(palindromeTester.isPalindrome(""));
    }

}
