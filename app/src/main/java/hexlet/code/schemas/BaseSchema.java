package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    boolean isRequired = false;

    public boolean isValid(T value) {
        if (value == null) {
            return !isRequired;
        }
        return validateValue(value);
    }

    public abstract boolean validateValue(T value);
}
