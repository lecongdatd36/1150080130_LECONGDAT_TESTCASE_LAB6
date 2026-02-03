package bai1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AirthematicTest {
    private String message = "Fpoly exception";

    private JunitMessage junitMessage = new JunitMessage(message);

    @Test(expected = ArithmeticException.class)
    public void testJunitMessage() {
        System.out.println("Fpoly Junit Message exception is printing");
        junitMessage.printMessage();
    }

    @Test
    public void testJunitHiMessage() {
        message = "Hi!" + message;
        System.out.println("Fpoly Junit Message is printing");
        assertEquals(message, junitMessage.printHiMessage());
    }
}
