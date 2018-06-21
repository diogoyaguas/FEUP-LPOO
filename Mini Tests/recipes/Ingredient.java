package recipes;

public abstract class Ingredient implements Comparable{

    private String name;

    public Ingredient(String name) throws IllegalArgumentException{
        if (name == null) throw new IllegalArgumentException();
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    @Override
    public boolean equals(Object ing) {
        return this.name.equals(((Ingredient)ing).getName());
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int compareTo(Object ing){
        return this.name.compareTo(((Ingredient) ing).getName());
    }

}