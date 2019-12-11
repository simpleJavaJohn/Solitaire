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
    }
    
    public void initializeGame() {
        this.roots = new Card[12];
        for(int i=0; i<roots.length; i++) {
        	int rank = 13;
        	if(i>3) rank=-2;
        	if(i>7) rank=-1;
        	roots[i] = new Card("Any", rank, false, null);
        }
    }
    
    public void loadWinner1() {
    	for(int i=0; i<4; i++) {
    		Card lastCard = roots[i];
    		for(int j=12; j>=0; j--) {
    			String suit = "";
    			if(i==0) suit = "C";
    			if(i==1) suit = "D";
    			if(i==2) suit = "H";
    			if(i==3) suit = "S";
    			Card newCard = new Card(suit, j, false, lastCard);
    			lastCard.child = newCard;
    			lastCard = newCard;
    		}
    	}
    	roots[0].child.moveTo(roots[4]);
    }
    
    public void loadLoser1() {
    	for(int i=0; i<4; i++) {
    		Card lastCard = roots[i];
    		for(int j=12; j>=0; j--) {
    			String suit = "";
    			if(i==0) suit = "C";
    			if(i==1) suit = "D";
    			if(i==2) suit = "H";
    			if(i==3) suit = "S";
    			boolean hidden = false;
    			if(i==0 && j>8) hidden = true;
    			Card newCard = new Card(suit, j, hidden, lastCard);
    			lastCard.child = newCard;
    			lastCard = newCard;
    		}
    	}
    	roots[0].child.moveTo(roots[4]);
    }
    
    public Boolean hasWon() {
    	for(int i=0; i<4; i++) if(!roots[i].isDone()) return false;
    	return true;
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
    
    public Card findChild(Card rCard, int column) {
    	for(int i=0; i<roots.length; i++){
    		if(i == column) continue;
            Card curCard = roots[i].child;
            while(curCard != null){
                if(curCard.isChild(rCard)) return curCard;
                curCard = curCard.child;
            }
        }
        return null;
    }
    
    public Card findKing(String kingSuit, int column) {
    	for(int i=0; i<roots.length; i++){
    		if(i == column) continue;
            Card curCard = roots[i].child;
            while(curCard != null){
                if(curCard.rank==12 && curCard.suit.equals(kingSuit)) return curCard;
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
            print();
        }
        else if(cmd.startsWith("print")) print();
        else if(cmd.equalsIgnoreCase("new game")) {
        	initializeGame();
        	randPop();
        	print();
        }
        else if(cmd.equalsIgnoreCase("winner 1")) {
        	initializeGame();
        	loadWinner1();
        	print();
        }
        else if(cmd.equalsIgnoreCase("loser 1")) {
        	initializeGame();
        	loadLoser1();
        	print();
        }
        else if(cmd.equalsIgnoreCase("solve")) {
        	if(solve(0)) {
        		System.out.println("Solvable");
        	} else {
        		System.out.println("Not Solvable");
        	}
        }
        else if(cmd.equalsIgnoreCase("unsolvable")) {
        	if(unsolvable(0, 12)) {
        		System.out.println("Unsolvable");
        	} else {
        		System.out.println("Possiblly solvable");
        	}
        }
        else if(cmd.equalsIgnoreCase("AI")) AI();
        else if(cmd.equalsIgnoreCase("Did I win?")) {
        	if(hasWon()) {
        		System.out.println("Yes Congrats!");
        	} else {
        		System.out.println("Not yet");
        	}
        }
    }

    public Boolean moveUp(String cmdCard){
        Card mCard = findOpenCard(cmdCard);
        if(mCard == null) {
        	System.out.println("Could not find that card");
        	return false;
        } else if(mCard.child != null) {
        	System.out.println("Card is not in bottom row");
        	return false;
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
        	return false;
        }
        mCard.moveTo(rCard);
        return true;
    }

    public Boolean moveDown(String cmdCard){
        Card mCard = findTopRowCard(cmdCard);
        if(mCard == null) {
        	System.out.println("Card is not in top row");
        	return false;
        }
        Card rCard = null;
        for(int i=0; i<roots.length-4; i++){
            Card curCard = roots[i].getBottomChild();
            if((curCard.suit.equals(mCard.suit) || curCard.suit.equals("Any")) && curCard.rank == mCard.rank + 1) {
            	rCard = curCard;
            	break;
            }
        }
        if(rCard == null) {
        	System.out.println("Could not move to receiving card");
        	return false;
        }
        mCard.moveTo(rCard);
        return true;
    }

    public Boolean move(String cmdCard){
        Card mCard = findCard(cmdCard);
        if(mCard == null) {
        	System.out.println("Could not find card");
        	return false;
        }
        Card rCard = null;
        search:
        for(int i=0; i<roots.length-4; i++){
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
        	return false;
        }        
        mCard.moveTo(rCard);
        return true;
        
    }
    
    public Boolean makeAIMove(int depthOfAI) {
    	for(int i=0; i<roots.length-4; i++) {
    		Card rCard = roots[i].getBottomChild();
    		if(rCard.rank <= 0) continue;
    		if(rCard.rank == 13) {
    			for(int j=0; j<4; j++) {
    				String kingSuit = "";
    				if(j==0) kingSuit = "C";
        			if(j==1) kingSuit = "D";
        			if(j==2) kingSuit = "H";
        			if(j==3) kingSuit = "S";
    				Card mCard = findKing(kingSuit, i);
    				if(mCard == null || mCard.hidden || mCard.parent.rank == 13) continue;
    				if(!unsolveMove(rCard, mCard, 0, depthOfAI)) {
    					mCard.moveTo(rCard);
    					print();
    					return true;
    				}
    			}
    		} else {
    			Card mCard = findChild(rCard, i);
        		if(mCard == null || mCard.hidden) continue;
        		if(!unsolveMove(rCard, mCard, 0, depthOfAI)) {
        			mCard.moveTo(rCard);
        			print();
					return true;
				}
    		}
    	}
    	Card rCard = null;
    	for(int i=7; i<roots.length; i++) {
    		Card curCard = roots[i];
    		if(curCard.child != null) continue;
    		rCard = curCard;
    		break;
    	}
    	if(rCard == null) return false;
    	int numOpen = 0;
    	for(int i=7; i<roots.length; i++) if(roots[i].child == null) numOpen++;
    	for(int i=0; i<roots.length-4; i++) {
    		Card mCard = roots[i];
    		mCard = mCard.getBottomChild();
    		if(mCard.suit.equals("Any")) continue;
    		if(mCard.numDone() > numOpen) continue;
    		if(!unsolveMove(rCard, mCard, 0, depthOfAI)) {
    			mCard.moveTo(rCard);
    			print();
				return true;
			}
    	}
    	return false;
    }
    
    public Boolean solveMove(Card rCard, Card mCard, int timesRun) {
    	Card oldParent = mCard.parent;
		Boolean oldParentHidden = oldParent.hidden;
		mCard.moveTo(rCard);
		//if(solve(timesRun+1)) return true;
		Boolean rv = solve(timesRun+1);
		mCard.parent = oldParent;
		oldParent.child = mCard;
		oldParent.hidden = oldParentHidden;
		rCard.child = null;
		//return false;
		return rv;
    }
    
    public Boolean unsolveMove(Card rCard, Card mCard, int timesRun, int depthOfAI) {
    	Card oldParent = mCard.parent;
		Boolean oldParentHidden = oldParent.hidden;
		mCard.moveTo(rCard);
		Boolean rv = (unsolvable(timesRun, depthOfAI));
		//if(unsolvable(timesRun, depthOfAI)) return true;
		mCard.parent = oldParent;
		oldParent.child = mCard;
		oldParent.hidden = oldParentHidden;
		rCard.child = null;
		return rv;
		//return false;
    }
    
    public Boolean solve(int timesRun) {
    	if(hasWon()) return true;
    	if(timesRun > 10) return false;
    	for(int i=0; i<roots.length-4; i++) {
    		Card rCard = roots[i].getBottomChild();
    		if(rCard.rank <= 0) continue;
    		if(rCard.rank == 13) {
    			for(int j=0; j<4; j++) {
    				String kingSuit = "";
    				if(j==0) kingSuit = "C";
        			if(j==1) kingSuit = "D";
        			if(j==2) kingSuit = "H";
        			if(j==3) kingSuit = "S";
    				Card mCard = findKing(kingSuit, i);
    				if(mCard == null || mCard.hidden || mCard.parent.rank == 13) continue;
    				if(solveMove(rCard, mCard, timesRun)) return true;
    			}
    		} else {
    			Card mCard = findChild(rCard, i);
        		if(mCard == null || mCard.hidden) continue;
        		if(solveMove(rCard, mCard, timesRun)) return true;
    		}
    	}
    	Card rCard = null;
    	for(int i=7; i<roots.length; i++) {
    		Card curCard = roots[i];
    		if(curCard.child != null) continue;
    		rCard = curCard;
    		break;
    	}
    	if(rCard == null) return false;
    	int numOpen = 0;
    	for(int i=7; i<roots.length; i++) if(roots[i].child == null) numOpen++;
    	for(int i=0; i<roots.length-4; i++) {
    		Card mCard = roots[i];
    		mCard = mCard.getBottomChild();
    		if(mCard.suit.equals("Any")) continue;
    		if(mCard.numDone() > numOpen) continue;
    		if(solveMove(rCard, mCard, timesRun)) return true;
    	}
    	return false;
    }
    
    public Boolean unsolvable(int timesRun, int depthOfAI) {
    	if(hasWon()) return false;
    	if(timesRun > depthOfAI || depthOfAI <= 0) return false;
    	for(int i=0; i<roots.length-4; i++) {
    		Card rCard = roots[i].getBottomChild();
    		if(rCard.rank <= 0) continue;
    		if(rCard.rank == 13) {
    			for(int j=0; j<4; j++) {
    				String kingSuit = "";
    				if(j==0) kingSuit = "C";
        			if(j==1) kingSuit = "D";
        			if(j==2) kingSuit = "H";
        			if(j==3) kingSuit = "S";
    				Card mCard = findKing(kingSuit, i);
    				if(mCard == null || mCard.hidden || mCard.parent.rank == 13) continue;
    				if(!unsolveMove(rCard, mCard, timesRun+1, depthOfAI)) return false;
    			}
    		} else {
    			Card mCard = findChild(rCard, i);
        		if(mCard == null || mCard.hidden) continue;
        		if(!unsolveMove(rCard, mCard, timesRun+1, depthOfAI)) return false;
    		}
    	}
    	Card rCard = null;
    	for(int i=7; i<roots.length; i++) {
    		Card curCard = roots[i];
    		if(curCard.child != null) continue;
    		rCard = curCard;
    		break;
    	}
    	if(rCard == null) return false;
    	int numOpen = 0;
    	for(int i=7; i<roots.length; i++) if(roots[i].child == null) numOpen++;
    	for(int i=0; i<roots.length-4; i++) {
    		Card mCard = roots[i];
    		mCard = mCard.getBottomChild();
    		if(mCard.suit.equals("Any")) continue;
    		if(mCard.numDone() > numOpen) continue;
    		if(!unsolveMove(rCard, mCard, timesRun+1, depthOfAI)) return false;
    	}
    	return true;
    }
    
    public void AI() {
    	int numMoves=0;
    	int depthOfAIInit = 8;
    	int depthOfAI = depthOfAIInit;
    	while(!hasWon()) {
    		if(numMoves>70) {
    			System.out.println("The AI Lost in "+ numMoves +" moves");
				return;
    		}
    		if(!makeAIMove(depthOfAI)) {
    			if(depthOfAI == 0) {
    				System.out.println("The AI Lost in "+ numMoves +" moves");
    				return;
    			}
    			depthOfAI = depthOfAI/2;
    			System.out.println(depthOfAI);
    		} else {
    			numMoves++;
    			depthOfAI = depthOfAIInit;
    		}
    		
    	}
    	System.out.println("The AI won in "+ numMoves +"moves");
    }
}