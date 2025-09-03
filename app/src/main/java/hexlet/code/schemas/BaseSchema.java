package hexlet.code.schemas;

public abstract class BaseSchema<T> {
    private boolean isRequired = false;

    public void setRequired(boolean required) {
        isRequired = required;
    }

    public boolean isRequired() {
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
