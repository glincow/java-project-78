package hexlet.code.schemas;

public class StringSchema {
    private boolean isRequired = false;
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

    public boolean isValid(String value) {
        if (value == null || value.isEmpty()) {
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

