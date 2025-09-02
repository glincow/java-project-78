package hexlet.code.schemas;

public class StringSchema extends BaseSchema<String> {
    private Integer minLength  = null;
    private String containsStr = null;

    public StringSchema required() {
        this.isRequired = true;
        return this;
    }

    public StringSchema minLength(int length) {
        this.minLength = length;
        return this;
    }

    public StringSchema contains(String str) {
        this.containsStr = str;
        return this;
    }

    public boolean validateValue(String value) {
        if (value.isEmpty()) {
            return !isRequired;
        }

        if (minLength != null && value.length() < minLength) {
            return false;
        }

        if (containsStr != null && !value.contains(containsStr)) {
            return false;
        }

        return true;
    }
}

