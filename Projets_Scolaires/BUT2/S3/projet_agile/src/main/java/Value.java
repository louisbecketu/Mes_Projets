public enum Value {
    DEUX(2), TROIS(3), QUATRE(4), CINQ(5), SIX(6), SEPT(7), HUIT(8), NEUF(9), DIX(10), VALET(11), DAME(12), ROI(13), ACE(14);

    private int valeur;

    Value(int valeur) {
        this.valeur = valeur;
    }

    public int getValeur(){
        return valeur;
    }
}
