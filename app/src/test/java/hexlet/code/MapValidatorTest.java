package hexlet.code;

import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MapValidatorTest {
    private Validator validator;
    private MapSchema schema;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        schema = validator.map();
    }

    @Test
    void testDefaultValidation() {
        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testRequired() {
        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testSizeof() {
        schema.sizeof(2);

        Map<String, String> data = new HashMap<>();
        assertThat(schema.isValid(data)).isFalse();

        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isFalse();

        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();

        data.put("key3", "value3");
        assertThat(schema.isValid(data)).isFalse();
    }

    @Test
    void testComplexValidation() {
        schema.required()
                .sizeof(2);

        assertThat(schema.isValid(null)).isFalse();

        Map<String, String> data = new HashMap<>();
        assertThat(schema.isValid(data)).isFalse();

        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isFalse();

        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    void testWithDifferentMapTypes() {
        schema.required();

        Map<Integer, String> intKeyMap = new HashMap<>();
        intKeyMap.put(1, "value1");
        assertThat(schema.isValid(intKeyMap)).isTrue();

        Map<String, Integer> intValueMap = new HashMap<>();
        intValueMap.put("key1", 1);
        assertThat(schema.isValid(intValueMap)).isTrue();
    }

    @Test
    void testEmptyMapValidation() {
        schema.required()
                .sizeof(0);

        assertThat(schema.isValid(new HashMap<>())).isTrue();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isFalse();
    }
}
