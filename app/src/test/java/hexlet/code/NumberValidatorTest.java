package hexlet.code;

import hexlet.code.schemas.NumberSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NumberValidatorTest {
    private Validator validator;
    private NumberSchema schema;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        schema = validator.number();
    }

    @Test
    void testDefaultValidation() {
        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(-5)).isTrue();
        assertThat(schema.isValid(0)).isTrue();
    }

    @Test
    void testRequired() {
        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isTrue();
        assertThat(schema.isValid(0)).isTrue();
    }

    @Test
    void testPositive() {
        schema.positive();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
    }

    @Test
    void testRange() {
        schema.range(5, 10);

        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(7)).isTrue();
        assertThat(schema.isValid(4)).isFalse();
        assertThat(schema.isValid(11)).isFalse();
    }

    @Test
    void testComplexValidation() {
        schema.required()
                .positive()
                .range(5, 10);

        assertThat(schema.isValid(7)).isTrue();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(-5)).isFalse();
        assertThat(schema.isValid(0)).isFalse();
        assertThat(schema.isValid(4)).isFalse();
        assertThat(schema.isValid(11)).isFalse();
    }

    @Test
    void testPositiveWithoutRequired() {
        schema.positive();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isFalse();
    }

    @Test
    void testRequiredAfterPositive() {
        schema.positive()
                .required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isFalse();
    }
}
