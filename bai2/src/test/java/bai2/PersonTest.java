package bai2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.fail;

public class PersonTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testExpectedExceptionRule() {
        exception.expect(IllegalArgumentException.class);
        new Person("Fpoly", -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testExpectedExceptionAnnotation() {
        new Person("Fpoly", -1);
    }

    @Test
    public void testTryCatch() {
        try {
            new Person("Fpoly", -1);
            fail("Should have thrown an IllegalArgumentException because age is invalid!");
        } catch (IllegalArgumentException e) {
          
        }
    }
}
