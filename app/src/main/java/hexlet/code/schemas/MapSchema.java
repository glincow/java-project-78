package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema<Map<?, ?>> {
    public MapSchema required() {
        this.required = true;
        return this;
    }

    public MapSchema sizeof(int size) {
        addCheck("size", value -> value.size() == size);
        return this;
    }

    public void shape(Map<String, ? extends BaseSchema<?>> schemasToValidate) {
        addCheck("shape", value -> {
            for (Map.Entry<?, ?> entry : value.entrySet()) {
                Object key = entry.getKey();
                Object mapValue = entry.getValue();
                BaseSchema<Object> valueSchema = (BaseSchema<Object>) schemasToValidate.get(key);
                if (valueSchema != null && !valueSchema.isValid(mapValue)) {
                    return false;
                }
            }
            return true;
        });
    }
}
