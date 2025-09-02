package hexlet.code.schemas;

public class NumberSchema extends BaseSchema<Integer> {
    private boolean isPositive = false;
    private Integer minRange = null;
    private Integer maxRange = null;

    public NumberSchema required() {
        this.isRequired = true;
        return this;
    }


    public NumberSchema positive() {
        this.isPositive = true;
        return this;
    }

    public NumberSchema range(int min, int max) {
        this.minRange = min;
        this.maxRange = max;
        return this;
    }

    @Override
    public boolean validateValue(Integer value) {
        if (isPositive && value <= 0) {
            return false;
        }

        if (minRange != null && maxRange != null) {
            return value >= minRange && value <= maxRange;
        }

        return true;
    }
}
