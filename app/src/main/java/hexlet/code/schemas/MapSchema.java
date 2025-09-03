package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private Integer requiredSize = null;
    private Map<String, BaseSchema<?>> schemas = null;

    public MapSchema required() {
        this.isRequired = true;
        return this;
    }

    public MapSchema sizeof(Integer size) {
        this.requiredSize = size;
        return this;
    }

    public void shape(Map<String, BaseSchema<?>> schemasToValidate) {
        this.schemas = schemasToValidate;
    }

    @Override
    public boolean validateValue(Map<?, ?> value) {
        if (requiredSize != null) {
            return value.size() == requiredSize;
        }

        if (schemas != null) {
            for (Map.Entry<?, ?> entry : value.entrySet()) {
                Object key = entry.getKey();
                Object mapValue = entry.getValue();
                BaseSchema<Object> valueSchema = (BaseSchema<Object>) schemas.get(key);
                if (valueSchema != null && !valueSchema.isValid(mapValue)) {
                    return false;
                }
            }
        }

        return true;
    }
}
