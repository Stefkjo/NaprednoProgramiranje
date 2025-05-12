//package Kolokviumski;
//
//import java.io.*;
//import java.util.*;
//import java.util.stream.Collectors;
//
//class IrregularCanvasException extends Exception {
//
//    public IrregularCanvasException(String message) {
//        super(message);
//    }
//
//}
//
//abstract class Shape implements Comparable<Shape> {
//    double size;
//
//    public Shape(double size) {
//        this.size = size;
//    }
//
//    abstract public double getArea();
//}
//
//class Square extends Shape {
//
//    public Square(double size) {
//        super(size);
//    }
//
//    @Override
//    public double getArea() {
//        return size * size;
//    }
//
//    @Override
//    public int compareTo(Shape other) {
//        return Double.compare(this.getArea(), other.getArea());
//    }
//}
//
//class Circle extends Shape {
//
//    public Circle(double size) {
//        super(size);
//    }
//
//    @Override
//    public double getArea() {
//        return Math.PI * (size * size);
//    }
//
//    @Override
//    public int compareTo(Shape other) {
//        return Double.compare(this.getArea(), other.getArea());
//    }
//}
//
//class Canvases implements Comparable<Canvases> {
//
//    String ID;
//    List<Shape> shapes;
//
//    public Canvases(String ID, List<Shape> shapes) {
//        this.ID = ID;
//        this.shapes = shapes;
//    }
//
//    public static Canvases createCanvas(String line, double maxArea) throws IrregularCanvasException {
//
//        String[] parts = line.split("\\s+");
//        String canvasID = parts[0];
//        //canvas_id type_1 size_1 type_2  size_2 type_3 size_3
//        //    0       1       2       3     4
//        List<Shape> addShape = new ArrayList<>();
//        for (int i = 1; i < parts.length; i += 2) {
//            if (parts[i].equals("S")) {
//                if (Double.parseDouble(parts[i + 1])*Double.parseDouble(parts[i + 1]) > maxArea) {
//                        throw new IrregularCanvasException(String.format("Canvas %s has a shape with area larger than %.2f", canvasID, maxArea));
//                } else {
//                    Shape shape = new Square(Double.parseDouble(parts[i + 1]));
//                    addShape.add(shape);
//                }
//            } else {
//                if ((Double.parseDouble(parts[i + 1])*Double.parseDouble(parts[i + 1]))*Math.PI > maxArea) {
//                    throw new IrregularCanvasException(String.format("Canvas %s has a shape with area larger than %.2f", canvasID, maxArea));
//                } else {
//                    Shape shape = new Circle(Double.parseDouble(parts[i + 1]));
//                    addShape.add(shape);
//                }
//            }
//        }
//        return new Canvases(canvasID, addShape);
//    }
//
//    public double getSumOfAreas() {
//        double totalSum = 0;
//        for (Shape shape : shapes) {
//            totalSum += shape.getArea();
//        }
//        return totalSum;
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder builder = new StringBuilder();
//        int countSquares = 0;
//        int countCircles = 0;
//        for (Shape shape : shapes) {
//            if (shape instanceof Square) {
//                countSquares++;
//            } else {
//                countCircles++;
//            }
//        }
//        builder.append(String.format("%s %d %d %d %.2f %.2f %.2f",
//                this.ID,
//                shapes.size(),
//                countCircles,
//                countSquares,
//                this.getSumOfAreas() / shapes.size(),
//                shapes.stream().min(Comparator.naturalOrder()).get().getArea(),
//                shapes.stream().max(Comparator.naturalOrder()).get().getArea()));
//
//        builder.append("\n");
//
//        return builder.toString();
//    }
//
//    @Override
//    public int compareTo(Canvases other) {
//        return Double.compare(this.getSumOfAreas(), other.getSumOfAreas());
//
//    }
//}
//
//
//class ShapesApplications {
//
//    double maxArea;
//    List<Canvases> canvases;
//
//    public ShapesApplications(double maxArea) {
//        this.maxArea = maxArea;
//        canvases = new ArrayList<>();
//    }
//
//    public void readCanvases(InputStream inputStream) {
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        canvases = br.lines()
//                .map(line -> {
//                    try {
//                        return Canvases.createCanvas(line, maxArea);
//                    } catch (IrregularCanvasException e) {
//                        System.out.println(e.getMessage());
//                        return null;
//                    }
//                })
//                .filter(Objects::nonNull)
//                .collect(Collectors.toUnmodifiableList());
//
//    }
//
//    public void printCanvases(OutputStream outputStream) {
//        PrintWriter wr = new PrintWriter(outputStream);
//
//        List<Canvases> sortedCanvases = canvases.stream()
//                .sorted(Comparator.reverseOrder())
//                .collect(Collectors.toUnmodifiableList());
//
//        sortedCanvases.forEach(canvas -> wr.write(canvas.toString()));
//
//        wr.flush();
//    }
//}
//
//
//public class Shapes2Test {
//
//    public static void main(String[] args) {
//
//        ShapesApplications shapesApplication = new ShapesApplications(10000);
//
//        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
//        shapesApplication.readCanvases(System.in);
//
//        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
//        shapesApplication.printCanvases(System.out);
//
//
//    }
//}