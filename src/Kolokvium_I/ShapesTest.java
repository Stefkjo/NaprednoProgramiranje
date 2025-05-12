package Kolokvium_I;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

enum Color {
    RED, GREEN, BLUE
}
interface Scalable{
    void scale(float scaleFactor);
}
interface Stackable{
     float weight();
}

abstract class Form implements Scalable, Stackable, Comparable<Form> {

     String ID;
     Color color;

    public Form(String ID, Color color){
        this.ID = ID;
        this.color = color;
    }

}

class Circle extends Form{

    float radius;

    public Circle(String ID, Color color, float radius){
        super(ID, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        this.radius+=scaleFactor;
    }

    @Override
    public float weight() {
        return (float) ((radius*radius)*Math.PI);
    }

    @Override
    public String toString(){
        return String.format("C: %-5s%-10s%10.2f\n", this.ID, this.color.toString(), this.weight());
    }

    @Override
    public int compareTo(Form other) {
        return Float.compare(this.weight(), other.weight());
    }
}

class Rectangle extends Form{

    float width;
    float height;

    public Rectangle(String ID, Color color, float width, float height){
        super(ID, color);
        this.height = height;
        this.width = width;
    }

    @Override
    public void scale(float scaleFactor) {
        this.width+=scaleFactor;
        this.height+=scaleFactor;
    }

    @Override
    public float weight() {
        return width*height;
    }

    @Override
    public String toString(){
        return String.format("R: %-5s%-10s%10.2f\n", this.ID, this.color.toString(), this.weight());
    }

    @Override
    public int compareTo(Form other) {
        return Float.compare(this.weight(), other.weight());
    }
}



class Canvas{

    private List<Form> circles;
    private List<Form> rectangles;

    public Canvas(){
        this.circles = new ArrayList<>();
        this.rectangles = new ArrayList<>();
    }

    public void add(String id, Color color, float radius){
        circles.add(new Circle(id, color, radius));
    }

    public void add(String id, Color color, float width, float height){
        rectangles.add(new Rectangle(id, color, width, height));
    }

    public void scale(String ID, float scaleFactor){
        for(Form form:circles){

            if(form.ID.compareTo(ID)==0){
                form.scale(scaleFactor);
            }

        }
        for(Form form:rectangles){
            if(form.ID.compareTo(ID)==0){
                form.scale(scaleFactor);
            }
        }
    }

    public void sort(){
        circles.sort(Comparator.reverseOrder());
        rectangles.sort(Comparator.reverseOrder());
    }

    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(Form form:circles){
            builder.append(form);
        }

        for(Form form:rectangles){
            builder.append(form);
        }

        return builder.toString();
    }

}


public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}

