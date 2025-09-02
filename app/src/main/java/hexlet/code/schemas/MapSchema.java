package hexlet.code.schemas;

import java.util.Map;

public class MapSchema extends BaseSchema<Map<?, ?>> {
    private Integer requiredSize = null;


    public MapSchema required() {
        this.isRequired = true;
        return this;
    }

    public MapSchema sizeOf(Integer size) {
        this.requiredSize = size;
        return this;
    }

    @Override
    public boolean validateValue(Map<?, ?> value) {
        if (requiredSize != null) {
            return value.size() == requiredSize;
        }

        return true;
    }
}
