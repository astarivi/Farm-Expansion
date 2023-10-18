package ovh.astarivi.farmexpansion.util;


public enum Nutrient {
    NITROGEN(0),
    PHOSPHORUS(1),
    POTASSIUM(2);

    final private int value;

    Nutrient(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
