package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    private boolean isRequired = false;

    public final void setRequired(boolean required) {
        isRequired = required;
    }

    public final boolean isRequired() {
        return isRequired;
    }

    public final boolean isValid(T value) {
        if (value == null) {
            return !isRequired;
        }
        return validateValue(value);
    }

    public abstract boolean validateValue(T value);
}
