package project.simulation.wsc;

import java.util.Objects;

//record is a special class in java which is intended mainly for data storage
public record Vector2D(int x, int y) {
    public String toString(){
        return "(" + x + "," + y + ")";
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x + other.x(), this.y + other.y);
    }

    //due to this snippet of code we check if some object is instance of Vector2D
    //if yes we overwrite a otherVector variable with value of other variable
    //this kind of operation is called "pattern matching" in java
    public boolean equals(Object other) {
        if (other instanceof Vector2D otherVector) {
            return this.x == otherVector.x && this.y == otherVector.y;
        }
        return false;
    }

    public boolean precedes (Vector2D other) {
        return this.x < other.x && this.y < other.y;
    }

    public boolean follows (Vector2D other) {
        return this.x >= other.x && this.y >= other.y;
    }

    //this method is used for generate a unique id for Vector2D object, it is very important for identify ours objects
    @Override
    public int hashCode() {
        return Objects.hash(this.x, this.y);
    }
}
