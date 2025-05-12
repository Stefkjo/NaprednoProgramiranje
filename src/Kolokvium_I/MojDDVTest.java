package Kolokvium_I;

import java.util.ArrayList;
import java.util.List;

class ItemR{

    double itemPrice;
    String itemType;

    public ItemR(double itemPrice, String itemType){
        this.itemPrice = itemPrice;
        this.itemType = itemType;
    }

    public double getValue(){
        if(this.itemType.equals("A")){
            return itemPrice*0.18;
        }else if(this.itemType.equals("B")){
            return itemPrice*0.05;
        }
        return 0;
    }

}

class Receipt{

    List<ItemR> items;

    public Receipt(){
        items = new ArrayList<>();
    }

    public double getTotalPrice(){
        double totalPrice= 0;

        for(ItemR item:items){

        }
        return 0;
    }
}

class MojDDV{


    //ID item_price1 item_tax_type1 item_price2 item_tax_type2 … item_price-n
    public MojDDV(){

    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();
//
//        System.out.println("===READING RECORDS FROM INPUT STREAM===");
//        mojDDV.readRecords(System.in);
//
//        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
//        mojDDV.printTaxReturns(System.out);
//
//        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
//        mojDDV.printStatistics(System.out);

    }
}