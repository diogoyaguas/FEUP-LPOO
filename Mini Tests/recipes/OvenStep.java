package recipes;

public class OvenStep extends RecipeStep{

    private int temperature;

    public OvenStep(String name, String action, int temp) throws IllegalArgumentException{
        super(name, action);
        this.temperature = temp;
    }

    public int getTemperature() {
        return this.temperature;
    }


}