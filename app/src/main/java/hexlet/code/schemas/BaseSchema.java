package hexlet.code.schemas;

import java.util.LinkedHashMap;
import java.util.function.Predicate;
import java.util.Map;

public abstract class BaseSchema<T> {
    protected Map<String, Predicate<T>> checks = new LinkedHashMap<>();
    protected boolean required = false;

    protected final void addCheck(String name, Predicate<T> validate) {
        checks.put(name, validate);
    }

    public final boolean isValid(T value) {
        if (value == null) {
            return !required;
        }
        return checks.values().stream().allMatch(check -> check.test(value));
    }
}
