package Auditoriska_1;

public class SumAvg {
    public static double sum(double[][] a){
        double sum = 0;
        for(int i = 0;i<a.length;i++){
            for(int j = 0;j<a[0].length;j++){
                sum+=a[i][j];
            }
        }
        return sum;
    }
    public static double avg(double[][] a){
        return sum(a)/(a.length*a[0].length);
    }
    public static void main(String[] args) {

    }
}
