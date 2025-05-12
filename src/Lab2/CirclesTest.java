package Lab2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}

interface Movable {
    void moveUp() throws ObjectCanNotBeMovedException;

    void moveDown() throws ObjectCanNotBeMovedException;

    void moveLeft() throws ObjectCanNotBeMovedException;

    void moveRight() throws ObjectCanNotBeMovedException;

    int getCurrentXPosition();

    int getCurrentYPosition();
}

class MovablePoint implements Movable {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (y + ySpeed > MovablesCollection.getYMax()) {
            throw new ObjectCanNotBeMovedException("Point (" + x + "," + (y + ySpeed) + ") is out of bounds");
        }
        this.y += ySpeed;
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (y - ySpeed < 0) {
            throw new ObjectCanNotBeMovedException("Point (" + x + "," + (y - ySpeed) + ") is out of bounds");
        }
        this.y -= ySpeed;
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (x - xSpeed < 0) {
            throw new ObjectCanNotBeMovedException("Point (" + (x - xSpeed) + "," + y + ") is out of bounds");
        }
        x -= xSpeed;
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (x + xSpeed > MovablesCollection.getXMax()) {
            throw new ObjectCanNotBeMovedException("Point (" + (x + xSpeed) + "," + y + ") is out of bounds");
        }
        x += xSpeed;
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    @Override
    public String toString() {
        return "Movable point with coordinates (" + x + "," + y + ")";
    }
}

class MovableCircle implements Movable {
    private int radius;
    private MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    public MovablePoint getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        center.moveUp();
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        center.moveDown();
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        center.moveLeft();
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        center.moveRight();
    }

    @Override
    public int getCurrentXPosition() {
        return center.getCurrentXPosition();
    }

    @Override
    public int getCurrentYPosition() {
        return center.getCurrentYPosition();
    }

    @Override
    public String toString() {
        return "Movable circle with center coordinates (" + center.getCurrentXPosition() + "," + center.getCurrentYPosition() + ") and radius " + radius;
    }
}

class MovablesCollection {
    private List<Movable> movable;
    private static int x_MAX = 0;
    private static int y_MAX = 0;

    public MovablesCollection(int x_MAX, int y_MAX) {
        this.movable = new ArrayList<>();
        MovablesCollection.x_MAX = x_MAX;
        MovablesCollection.y_MAX = y_MAX;
    }

    public static int getYMax() {
        return y_MAX;
    }

    public static int getXMax() {
        return x_MAX;
    }

    public static void setxMax(int i) {
        x_MAX = i;
    }

    public static void setyMax(int i) {
        y_MAX = i;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if (m instanceof MovablePoint) {
            MovablePoint point = (MovablePoint) m;
            if ((point.getCurrentXPosition() >= 0 && point.getCurrentXPosition() <= x_MAX) && (point.getCurrentYPosition() >= 0 && point.getCurrentYPosition() <= y_MAX)) {
                movable.add(m);
            } else {
                throw new MovableObjectNotFittableException(m.toString() + " can not be fitted into the collection");
            }
        } else if (m instanceof MovableCircle) {
            MovableCircle circle = (MovableCircle) m;
            MovablePoint center = circle.getCenter();
            if ((center.getCurrentXPosition() - circle.getRadius() >= 0 && center.getCurrentXPosition() + circle.getRadius() <= x_MAX) && (center.getCurrentYPosition() - circle.getRadius() >= 0 && center.getCurrentYPosition() + circle.getRadius() <= y_MAX)) {
                movable.add(m);
            } else {
                throw new MovableObjectNotFittableException("Movable circle with center (" + center.getCurrentXPosition() + "," + center.getCurrentYPosition() + ") and radius " + circle.getRadius() + " can not be fitted into the collection");
            }
        }

    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) throws ObjectCanNotBeMovedException {
        for (Movable move : movable) {
            if ((move instanceof MovablePoint && type==TYPE.POINT) || ((move instanceof MovableCircle && type==TYPE.CIRCLE))) {
                try{
                    if (direction == DIRECTION.DOWN) {
                        move.moveDown();
                    }
                    if (direction == DIRECTION.UP) {
                        move.moveUp();
                    }
                    if (direction == DIRECTION.LEFT) {
                        move.moveLeft();
                    }
                    if (direction == DIRECTION.RIGHT) {
                        move.moveRight();
                    }
                }catch (ObjectCanNotBeMovedException e){
                    System.out.println(e.getMessage());
                }
            }
        }

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Collection of movable objects with size ").append(this.movable.size()).append(":\n");
        movable.forEach(movable1 -> {
            builder.append(movable1.toString()).append("\n");
        });
        return builder.toString();
    }
}

public class CirclesTest {

    public static void main(String[] args) throws ObjectCanNotBeMovedException {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) {
                try {
                    collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                try {
                    collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());


    }


}
