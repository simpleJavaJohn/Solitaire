public class Card{
    String suit;
    int rank;
    Boolean hidden;
    Card parent;
    Card child;

    Card(String suit, int rank, Boolean hidden, Card parent){
        this.suit = suit;
        this.rank = rank;
        this.hidden = hidden;
        this.parent = parent;
        this.child = null;
    }

    public String toString(){
        if(hidden) return "XX";
        if(rank == 0) return ("A" + suit);
        if(rank == 9) return ("T" + suit);
        if(rank == 10) return ("J" + suit);
        if(rank == 11) return ("Q" + suit);
        if(rank == 12) return ("K" + suit);
        return ("" + (rank + 1) + suit);
    }
}