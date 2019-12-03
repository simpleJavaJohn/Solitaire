import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game{
    Card roots[];

    public static void main(String[] args) {
        Game game = new Game();
        Scanner reader = new Scanner(System.in);
        while(true){
            String cmd = reader.nextLine(); 
            game.cmdReader(cmd);
        }

    }

    public void randPop(){
        Random rand = new Random();
        ArrayList<Card> cardsLeft = new ArrayList<Card>();
        for(int i=0; i<52; i++){
            String suit;
            int rank = i%13;
            if(i<13) suit = "C";
            else if(i<26) suit = "D";
            else if(i<39) suit = "H";
            else suit = "S";

            Card curCard = new Card(suit, rank, false, null);
            cardsLeft.add(curCard);
        }
        Card lastCard;
        for(int i=0; i<8; i++){
            lastCard = roots[i];
            for(int j=0; j<6; j++){
                Card curCard = cardsLeft.get(rand.nextInt(cardsLeft.size()));
                if(i>3 && j<4) curCard.hidden = true;
                curCard.parent = lastCard;
                lastCard.child = curCard;
                lastCard = curCard;
                cardsLeft.remove(curCard);
            }
        }
        for(int i=8; i<12; i++){
            Card curCard = cardsLeft.get(rand.nextInt(cardsLeft.size()));
            curCard.parent = roots[i];
            roots[i].child = curCard;
            cardsLeft.remove(curCard);
        }
        print();
    }
    
    public void initializeGame() {
        this.roots = new Card[12];
        roots[0] = new Card("Any", 13, false, null);
        roots[1] = new Card("Any", 13, false, null);
        roots[2] = new Card("Any", 13, false, null);
        roots[3] = new Card("Any", 13, false, null);
        roots[4] = new Card("Any", -2, false, null);
        roots[5] = new Card("Any", -2, false, null);
        roots[6] = new Card("Any", -2, false, null);
        roots[7] = new Card("Any", -2, false, null);
        roots[8] = new Card("Any", -1, false, null);
        roots[9] = new Card("Any", -1, false, null);
        roots[10] = new Card("Any", -1, false, null);
        roots[11] = new Card("Any", -1, false, null);
    }

    public void print(){
    	if(roots == null) {
    		System.out.println("Start a game by typing \"new game\"");
    		return;
    	}
        String runLine = "";
        for(int i=8; i<12; i++){
            if(roots[i].child == null){
                runLine += "    ";
            } else {
                runLine += roots[i].child + "  ";
            }
        }
        System.out.println(runLine);
        System.out.println(" ");
        Card cardCol1 = roots[0];
        Card cardCol2 = roots[1];
        Card cardCol3 = roots[2];
        Card cardCol4 = roots[3];
        Card cardCol5 = roots[4];
        Card cardCol6 = roots[5];
        Card cardCol7 = roots[6];
        Card cardCol8 = roots[7];
        while(cardCol1 != null ||
            cardCol2 != null ||
            cardCol3 != null ||
            cardCol4 != null ||
            cardCol5 != null ||
            cardCol6 != null ||
            cardCol7 != null ||
            cardCol8 != null){
            if(cardCol1 != null) cardCol1 = cardCol1.child;
            if(cardCol2 != null) cardCol2 = cardCol2.child;
            if(cardCol3 != null) cardCol3 = cardCol3.child;
            if(cardCol4 != null) cardCol4 = cardCol4.child;
            if(cardCol5 != null) cardCol5 = cardCol5.child;
            if(cardCol6 != null) cardCol6 = cardCol6.child;
            if(cardCol7 != null) cardCol7 = cardCol7.child;
            if(cardCol8 != null) cardCol8 = cardCol8.child;
            runLine = "";
            if(cardCol1 != null){ runLine += cardCol1 + " ";} else {runLine += "   ";}
            if(cardCol2 != null){ runLine += cardCol2 + " ";} else {runLine += "   ";}
            if(cardCol3 != null){ runLine += cardCol3 + " ";} else {runLine += "   ";}
            if(cardCol4 != null){ runLine += cardCol4 + " ";} else {runLine += "   ";}
            if(cardCol5 != null){ runLine += cardCol5 + " ";} else {runLine += "   ";}
            if(cardCol6 != null){ runLine += cardCol6 + " ";} else {runLine += "   ";}
            if(cardCol7 != null){ runLine += cardCol7 + " ";} else {runLine += "   ";}
            if(cardCol8 != null){ runLine += cardCol8 + " ";} else {runLine += "   ";}
            System.out.println(runLine);
        }
    }

    public Card findCard(String cmdCard){
    	if(cmdCard.equals("XX")) return null;
    	for(int i=0; i<roots.length; i++){
            Card curCard = roots[i].child;
            while(curCard != null){
                if(curCard.toString().equals(cmdCard)) return curCard;
                curCard = curCard.child;
            }
        }
        return null;
    }
    
    public Card findTopRowCard(String cmdCard) {
    	for(int i=8; i<roots.length; i++){
            Card curCard = roots[i].child;
            if(curCard != null) {
            	if(curCard.toString().equals(cmdCard)) return curCard;
            }
        }
        return null;
    }
    
    public Card findOpenCard(String cmdCard) {
    	for(int i=0; i<roots.length-4; i++) {
    		Card curCard = roots[i].child;
    		while(curCard != null){
                if(curCard.toString().equals(cmdCard)) return curCard;
                curCard = curCard.child;
            }
    	}
    	return null;
    }

    public void cmdReader(String cmd){
        if(cmd.startsWith("move")){
            if(cmd.endsWith("up")) this.moveUp(cmd.substring(5, 7));
            if(cmd.endsWith("down")) this.moveDown(cmd.substring(5, 7));
            if(cmd.length() == 7) this.move(cmd.substring(5));
        }
        if(cmd.startsWith("print")) print();
        if(cmd.equalsIgnoreCase("new game")) {
        	initializeGame();
        	randPop();
        }
    }

    public void moveUp(String cmdCard){
        System.out.println("Moving -" + cmdCard + "- up... WIP");
        Card mCard = findOpenCard(cmdCard);
        if(mCard == null) {
        	System.out.println("Could not find that card");
        	return;
        } else if(mCard.child != null) {
        	System.out.println("Card is not in bottom row");
        	return;
        }
        Card rCard = null;
        for(int i=8; i<roots.length; i++){
            Card curCard = roots[i];
            if(curCard.child == null) {
            	rCard= curCard;
            	break;
            }
        }
        if(rCard == null) {
        	System.out.println("No open space");
        	return;
        }
        mCard.parent.child = null;
        mCard.parent.hidden = false;
        mCard.parent = rCard;
        rCard.child = mCard;
        print();
    }

    public void moveDown(String cmdCard){
        System.out.println("Moving -" + cmdCard + "- down... WIP");
        Card mCard = findTopRowCard(cmdCard);
        if(mCard == null) {
        	System.out.println("Card is not in top row");
        	return;
        }
        Card rCard = null;
        search:
        for(int i=0; i<roots.length-4; i++){
            Card curCard = roots[i];
            while(curCard != null){
                if(curCard.child == null
                		&& (curCard.suit.equals(mCard.suit) || curCard.suit.equals("Any"))
                		&& curCard.rank == mCard.rank + 1) {
                	rCard = curCard;
                	break search;
                }
                curCard = curCard.child;
            }
        }
        if(rCard == null) {
        	System.out.println("Could not move to receiving card");
        	return;
        }
        mCard.parent.child = null;
        mCard.parent = rCard;
        rCard.child = mCard;
        print();
    }

    public void move(String cmdCard){
        System.out.println("Moving -" + cmdCard + "-... WIP");
        Card mCard = findCard(cmdCard);
        if(mCard == null) {
        	System.out.println("Could not find card");
        	return;
        }
        Card rCard = null;
        search:
        for(int i=0; i<roots.length; i++){
            Card curCard = roots[i];
            while(curCard != null){
                if(curCard == mCard) break;
                if((curCard.suit.equals(mCard.suit) || curCard.suit.equals("Any"))
                		&& curCard.rank == mCard.rank + 1
                		&& curCard.child == null
                		&& !curCard.hidden) {
                	rCard = curCard;
                	break search;
                }
                curCard = curCard.child;
            }
        }
        if(rCard == null) {
        	System.out.println("Could not move to receiving card");
        	return;
        }        
        mCard.parent.child = null;
        mCard.parent.hidden = false;
        mCard.parent = rCard;
        rCard.child = mCard;
        print();
        
    }
}