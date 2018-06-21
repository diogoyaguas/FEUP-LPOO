package recipes;

import java.util.ArrayList;

public class Recipe {

    private String name;

    private ArrayList<RecipeStep> steps = new ArrayList<>();

    public Recipe(String name) throws IllegalArgumentException{
        if (name == null) throw new IllegalArgumentException();
        this.name = name;
    }

    public void addStep(RecipeStep step){
        if (!steps.contains(step))
            this.steps.add(step);
    }

    public int getStepCount(){
        return this.steps.size();
    }

}