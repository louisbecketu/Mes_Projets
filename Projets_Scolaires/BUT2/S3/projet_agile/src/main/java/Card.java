import java.util.ArrayList;

public class Card implements Comparable<Card> {
    public Value value;
    public Color color;

    public Card(String value, String color){
        this.value = Value.valueOf(value);
        this.color = Color.valueOf(color);
    }

    public Value getValue() {
        return value;
    }

    public Color getColor() {
        return color;
    }

    public int compareTo(Card card){
        if (this.value.equals(card.getValue())){
            return 0;
        } else if (this.value.ordinal() > card.getValue().ordinal()){
            return 1;
        } else {
            return -1;
        }
    }

    public String toString(){
        String tp = "\n ______\n|";
        if(this.value.getValeur() == 11){
            tp += "V";
        }else if(this.value.getValeur() == 12){
            tp += "D";
        }else if(this.value.getValeur() == 13){
            tp += "R";
        }else if(this.value.getValeur() == 14){
            tp += "A";
        }else{
            tp += this.value.getValeur();
        }
        if(this.value.getValeur() == 10){
            tp += "    |\n|  ";
        }else{
            tp += "     |\n|  ";
        }
        if(this.color.equals(Color.CARREAU)){
        tp += "♦";
        }else if(this.color.equals(Color.COEUR)){
        tp += "♥";
        }else if(this.color.equals(Color.PIQUE)){
        tp += "♠";
        }else if(this.color.equals(Color.TREFLE)){
        tp += "♣";
        }
        if(this.value.getValeur() == 10){
            tp += "   |\n|    ";
        }else{
            tp += "   |\n|     ";
        }
        if(this.value.getValeur() == 11){
            tp += "V";
        }else if(this.value.getValeur() == 12){
            tp += "D";
        }else if(this.value.getValeur() == 13){
            tp += "R";
        }else if(this.value.getValeur() == 14){
            tp += "A";
        }else{
            tp += this.value.getValeur();
        }
        tp += "|\n ‾‾‾‾‾‾ \n";
        return tp;
    }

    public boolean equals(Card card){
        return this.value.equals(card.getValue());
    }
} 