//package Kolokviumski;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//class Squares {
//
//    int size;
//
//    public Squares(int size) {
//        this.size = size;
//    }
//
//    public int getL(){
//        return this.size*4;
//    }
//
//}
//
//class Canvas implements Comparable<Canvas>{
//
//    String ID;
//    List<Squares> squares;
//
//    public Canvas(String line) {
//        squares = new ArrayList<>();
//        String[] parts = line.split("\\s+");
//        this.ID = parts[0];
//        for (int i = 1; i < parts.length; i++) {
//            Squares square = new Squares(Integer.parseInt(parts[i]));
//            squares.add(square);
//        }
//    }
//
//    public int getMaxSize(){
//        int total = 0;
//        for(Squares square:squares){
//            total+= square.getL();
//        }
//        return total;
//    }
//
//    @Override
//    public int compareTo(Canvas other) {
//        return Integer.compare(this.getMaxSize(), other.getMaxSize());
//    }
//
//}
//
//
//class ShapesApplication {
//
//    List<Canvas> canvases;
//
//    public ShapesApplication() {
//        canvases = new ArrayList<>();
//    }
//
//    public int readCanvases(InputStream inputStream) {
//
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//
//        canvases = br.lines()
//                .map(Canvas::new)
//                .collect(Collectors.toUnmodifiableList());
//
//        int totalSizesRead = 0;
//
//        for (Canvas canvas : canvases) {
//            totalSizesRead += canvas.squares.size();
//        }
//
//        return totalSizesRead;
//
//    }
//
//    public void printLargestCanvasTo(OutputStream outputStream){
//
//        PrintWriter wr = new PrintWriter(outputStream);
//
//        Canvas biggest;
//
//       biggest = canvases.stream().max(Comparator.naturalOrder()).orElse(null);
//
//        wr.write(String.format("%s %d %d%n",biggest.ID, biggest.squares.size(), biggest.getMaxSize()));
//        wr.flush();
//    }
//
//}
//
//public class Shapes1Test {
//
//    public static void main(String[] args) {
//        ShapesApplication shapesApplication = new ShapesApplication();
//
//        System.out.println("===READING SQUARES FROM INPUT STREAM===");
//        System.out.println(shapesApplication.readCanvases(System.in));
//        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
//        shapesApplication.printLargestCanvasTo(System.out);
//
//    }
//}