package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorTest {
    private Validator validator;
    private StringSchema schema;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        schema = validator.string();
    }

    @Test
    void testDefaultValidation() {
        assertTrue(schema.isValid(""));
        assertTrue(schema.isValid(null));
        assertTrue(schema.isValid("any string"));
    }

    @Test
    void testRequired() {
        schema.required();

        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid(""));
        assertTrue(schema.isValid("any string"));
        assertTrue(schema.isValid("hexlet"));
    }

    @Test
    void testMinLength() {
        schema.minLength(5);

        assertTrue(schema.isValid("hexlet"));
        assertTrue(schema.isValid("what does the fox say"));
        assertFalse(schema.isValid("hex"));
    }

    @Test
    void testContains() {
        schema.contains("hex");

        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("what"));
    }

    @Test
    void testComplexValidation() {
        schema.required()
                .minLength(5)
                .contains("hex");

        assertTrue(schema.isValid("hexlet"));
        assertFalse(schema.isValid("hex"));
        assertFalse(schema.isValid(""));
        assertFalse(schema.isValid(null));
        assertFalse(schema.isValid("what"));
    }
}
