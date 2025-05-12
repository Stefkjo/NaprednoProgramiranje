package Auditoriska_7;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

class InvalidPickerArgumentException extends Exception {
    public InvalidPickerArgumentException(String message) {
        super(message);
    }
}

class FinalistPicker {
    private int finalists;
    static Random RANDOM = new Random();

    public FinalistPicker(int finalists) {
        this.finalists = finalists;
    }

    public List<Integer> pick(int n) throws InvalidPickerArgumentException {
        if (n > finalists) {
            throw new InvalidPickerArgumentException("The number n cannot exceed the number of finalists");
        }
        if (n <= 0) {
            throw new InvalidPickerArgumentException("The number n must be a positive number");
        }

        return RANDOM.ints(2*n, 1, finalists+1)
                .boxed()
                .distinct()
                .limit(n)
                .toList();


//        List<Integer> pickedFinalists = new ArrayList<>();
//
//        while(pickedFinalists.size()!=n){
//            int pick = RANDOM.nextInt(finalists)+1;
//            if(!pickedFinalists.contains(pick)){
//                pickedFinalists.add(pick);
//            }
//        }
//        return pickedFinalists;
    }
}

public class FinalistTest {
    public static void main(String[] args) {
        FinalistPicker picker = new FinalistPicker(15);
        try{
            System.out.println(picker.pick(5));
        }catch (InvalidPickerArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
