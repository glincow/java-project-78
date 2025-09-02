package hexlet.code;

import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid("any string")).isTrue();
    }

    @Test
    void testRequired() {
        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("any string")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
    }

    @Test
    void testMinLength() {
        schema.minLength(5);

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hex")).isFalse();
    }

    @Test
    void testContains() {
        schema.contains("hex");

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("what")).isFalse();
    }

    @Test
    void testComplexValidation() {
        schema.required()
                .minLength(5)
                .contains("hex");

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("hex")).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("what")).isFalse();
    }

    @Test
    void testMinLengthPriority() {
        schema.minLength(10)
                .minLength(4);

        assertThat(schema.isValid("Hexlet")).isTrue();
        assertThat(schema.isValid("Hex")).isFalse();
    }

    @Test
    void testContainsPriority() {
        schema.contains("what")
                .contains("hex");

        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid("what")).isFalse();
    }
}
