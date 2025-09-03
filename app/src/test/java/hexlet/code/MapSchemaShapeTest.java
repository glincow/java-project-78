package hexlet.code;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MapSchemaShapeTest {
    private Validator validator;
    private MapSchema schema;

    @BeforeEach
    void setUp() {
        validator = new Validator();
        schema = validator.map();
    }

    @Test
    void testBasicShape() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number().positive());

        schema.shape(schemas);

        Map<Object, Object> human1 = new HashMap<>();
        human1.put("name", "John");
        human1.put("age", 25);
        assertThat(schema.isValid(human1)).isTrue();

        Map<Object, Object> human2 = new HashMap<>();
        human2.put("name", "");
        human2.put("age", 25);
        assertThat(schema.isValid(human2)).isFalse();

        Map<Object, Object> human3 = new HashMap<>();
        human3.put("name", "John");
        human3.put("age", -5);
        assertThat(schema.isValid(human3)).isFalse();
    }

    @Test
    void testComplexShape() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("firstName", validator.string().required());
        schemas.put("lastName", validator.string().required().minLength(2));
        schemas.put("age", validator.number().required().range(18, 100));

        schema.shape(schemas);

        Map<Object, Object> human1 = new HashMap<>();
        human1.put("firstName", "John");
        human1.put("lastName", "Smith");
        human1.put("age", 25);
        assertThat(schema.isValid(human1)).isTrue();

        Map<Object, Object> human2 = new HashMap<>();
        human2.put("firstName", "John");
        human2.put("lastName", "S");
        human2.put("age", 25);
        assertThat(schema.isValid(human2)).isFalse();

        Map<Object, Object> human3 = new HashMap<>();
        human3.put("firstName", "John");
        human3.put("lastName", "Smith");
        human3.put("age", 150);
        assertThat(schema.isValid(human3)).isFalse();
    }

    @Test
    void testShapeWithNullValues() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());
        schemas.put("age", validator.number());

        schema.shape(schemas);

        Map<Object, Object> human1 = new HashMap<>();
        human1.put("name", "John");
        human1.put("age", null);
        assertThat(schema.isValid(human1)).isTrue();

        Map<Object, Object> human2 = new HashMap<>();
        human2.put("name", null);
        human2.put("age", 25);
        assertThat(schema.isValid(human2)).isFalse();
    }

    @Test
    void testShapeWithExtraKeys() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());

        schema.shape(schemas);

        Map<Object, Object> human = new HashMap<>();
        human.put("name", "John");
        human.put("extraField", "value");
        assertThat(schema.isValid(human)).isTrue();
    }

    @Test
    void testCombinedValidation() {
        Map<String, BaseSchema<?>> schemas = new HashMap<>();
        schemas.put("name", validator.string().required());

        schema.required()
                .sizeof(2)
                .shape(schemas);

        Map<Object, Object> data1 = new HashMap<>();
        data1.put("name", "John");
        data1.put("age", 25);
        assertThat(schema.isValid(data1)).isTrue();

        Map<Object, Object> data2 = new HashMap<>();
        data2.put("name", "John");
        assertThat(schema.isValid(data2)).isFalse();

        assertThat(schema.isValid(null)).isFalse();
    }
}
