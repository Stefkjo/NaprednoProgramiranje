package Auditoriska_2;

import java.util.Arrays;

public class Deck {

    private PlayingCard[] cards;
    private boolean[] picked;
    private int total;

    public Deck() {
        total = 0;
        cards = new PlayingCard[52];
        picked = new boolean[52];
        for (int i = 0; i < PlayingCard.TYPE.values().length; i++) {
            for (int j = 0; j < 13; j++) {
                cards[j + (13 * i)] = new PlayingCard(PlayingCard.TYPE.values()[i], j + 1);
            }
        }
    }

    public String toString() {
        StringBuilder result = new StringBuilder();

        for (PlayingCard playingCard : cards) {
            result.append(playingCard);
            result.append("\n");
        }
        return result.toString();
    }

    public int hashCode(){
        final int prime  = 31;
        int result = 1;
        result = prime*result + Arrays.hashCode(cards);
        return result;
    }

    public boolean equals(Object obj){
        if(this==obj){
            return true;
        }
        if(obj==null){
            return false;
        }
        if(getClass()!= obj.getClass()){
            return false;
        }
        Deck other = (Deck) obj;
        return Arrays.equals(cards, other.cards);
    }

    public PlayingCard dealCard(){
        if(total == 52) return null;
        int card = (int)(52*Math.random());
        if(!picked[card]){
            total++;
            picked[card] = true;
            return cards[card];
        }
        return dealCard();
    }

    public static void main(String[] args) {
        Deck deck = new Deck();
        PlayingCard card;
        while((card=deck.dealCard())!=null){
            System.out.println(card);
        }
    }






}
