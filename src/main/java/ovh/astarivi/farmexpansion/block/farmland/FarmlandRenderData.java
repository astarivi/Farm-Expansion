package ovh.astarivi.farmexpansion.block.farmland;


public record FarmlandRenderData(int nitrogen, int phosphorus, int potassium, int moisture) {
    public FarmlandRenderData(int[] npkm) {
        this(npkm[0], npkm[1], npkm[2], npkm[3]);
    }

    public int getCombinedNutrientScale() {
        return nitrogen + phosphorus + potassium;
    }
}
