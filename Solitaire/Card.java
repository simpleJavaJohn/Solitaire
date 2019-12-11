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
    
    public Card getBottomChild() {
    	Card curCard = this;
    	while(curCard.child != null) {
    		curCard = curCard.child;
    	}
    	return curCard;
    }
    
    public void moveTo(Card rCard) {
    	parent.child = null;
    	parent.hidden = false;
        parent = rCard;
        rCard.child = this;
    }
    
    public Boolean isChild(Card rCard) {
    	return ((suit.equals(rCard.suit) || rCard.suit.equals("Any"))
    			&& rank == rCard.rank-1);
    			
    }
    
    public Boolean isDone() {
    	if(rank == 0) return true;
    	if(child == null) return false;
    	if((child.suit.equals(suit) || suit.equals("Any")) && child.rank == rank-1) return child.isDone();
    	return false;
    }
    
    public int numDone() {
    	if(parent == null) return 1;
    	if((parent.suit.equals(suit) || parent.suit.equals("Any")) && parent.rank == rank+1 && !parent.hidden) return 1 + parent.numDone();
    	return 1;
    }
}