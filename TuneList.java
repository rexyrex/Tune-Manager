public class TuneList{
	
	private Node head;
	private Node headLike;
	
	public TuneList(){
		head = null;
	}
	
	
	public void addTune(String artist, String title){
		Tune t = new Tune(artist,title);
		//node to be added containing new tune t
		Node addNode = new Node(t,null,null);
		
		// --- ORDERING ALPHABETICAL ---
		//case 1 : no tunes in list, head of tuneList = tune
		if(head == null){ 
			head = addNode;
			
		//case 2 : tune should be added to start of list (becomes head)
		} else if(compareTuneAlph(t,head.getTune())<0){
			addNode.setNextAlph(head);
			head = addNode;
		
		} else {		
			Node nextAlph = head;
			while(compareTuneAlph(t,nextAlph.getTune()) >0){
			
				//case 3 : tune should be added at the end of list
				if(nextAlph.getNextAlph() == null){
					addNode.setNextAlph(null);
					nextAlph.setNextAlph(addNode);
					
				//case 4 : recurse through list to find where song should be inserted				
				} else if(compareTuneAlph(t,nextAlph.getNextAlph().getTune())<0){				
					addNode.setNextAlph(nextAlph.getNextAlph());					
					nextAlph.setNextAlph(addNode);
					
				} else {
					nextAlph = nextAlph.getNextAlph();			
				}
			}
		}

		//--- ORDERING BY LIKES ---
		
		Node nextLike = headLike;
		
		if(headLike == null){
			headLike = addNode;
		} else if(headLike.getTune().getLikes() == 0 && compareTuneAlph(t,headLike.getTune())<0){
			addNode.setNextLike(headLike);
			headLike = addNode;
		} else {
			//Find which node starts at 0 likes
			while(nextLike.getTune().getLikes()!=0){
				nextLike = nextLike.getNextLike();
			}
			
			while(compareTuneAlph(t,nextLike.getTune()) >0){
			
				//case 3 : tune should be added at the end of list
				if(nextLike.getNextLike() == null){
					addNode.setNextLike(null);
					nextLike.setNextLike(addNode);
					break;
				//case 4 : recurse through list to find where song should be inserted				
				} else if(compareTuneAlph(t,nextLike.getNextAlph().getTune())<0){				
					addNode.setNextLike(nextLike.getNextLike());					
					nextLike.setNextLike(addNode);
					
				} else {
					nextLike = nextLike.getNextLike();			
				}
			}
		}		
	}
	
	public void likeTune(String artist, String title){
		Node beforeNodeToLike = headLike;
		
		// case 1 : node to like is first Node of List -> just increment like
		if(isCorrectNode(artist, title, headLike)){
			headLike.getTune().like();
		}
		
		
		// case 2&3 : search list for node to like
		Node nodeToLike;
					
			while(!isCorrectNode(artist,title, beforeNodeToLike.getNextLike())){
				beforeNodeToLike = beforeNodeToLike.getNextLike();
				
				if(beforeNodeToLike.getNextLike() == null){
					break;
				}
				
			}
			
			//if node is found 
			if(beforeNodeToLike.getNextLike() != null && isCorrectNode(artist,title, beforeNodeToLike.getNextLike())){
				nodeToLike = beforeNodeToLike.getNextLike();
				nodeToLike.getTune().like();
			
				// case 3 : node to like is at end of list -> node before node to like points to null
				
				if(nodeToLike.getNextLike() == null){
					beforeNodeToLike.setNextLike(null);
				// case 2 : node to like is in middle of list -> node before node to like points to next node
				} else {
					beforeNodeToLike.setNextLike(nodeToLike.getNextLike());
				}
				
				// Place node in correct place in list
				
				//Find which node starts at current node likes
			
				Node nextLike = headLike;
				Tune t = nodeToLike.getTune();
				
				
				// check if tune has the greatest likes -> put at start of list
				if(t.getLikes() > headLike.getTune().getLikes()){
					nodeToLike.setNextLike(headLike);
					headLike = nodeToLike;
				}
					System.out.println("1 TEST: NEXT LIKE TITLE = " + nextLike.getTune().getTitle());
					System.out.println("1 TEST: NEXT LIKE Artist = " + nextLike.getTune().getArtist());
				while(nextLike.getTune().getLikes()>nodeToLike.getTune().getLikes()){
				System.out.println("TEST: NEXT LIKE TITLE = " + nextLike.getTune().getArtist());
					nextLike = nextLike.getNextLike();
				}
				
				while(compareTuneAlph(t,nextLike.getTune()) >0){
				
					//case 3 : tune should be added at the end of list
					if(nextLike.getNextLike() == null){
						nodeToLike.setNextLike(null);
						nextLike.setNextLike(nodeToLike);
						break;
					//case 4 : recurse through list to find where song should be inserted				
					} else if(compareTuneAlph(t,nextLike.getNextAlph().getTune())<0){				
						nodeToLike.setNextLike(nextLike.getNextLike());					
						nextLike.setNextLike(nodeToLike);
						
					} else {
						nextLike = nextLike.getNextLike();			
					}
				}
			}
	}
	
	public String listAlphabetically(){
		
		Node nextNode = head;
		String result = "";
		while(nextNode != null){
			result += songToString(nextNode.getTune());
			
			nextNode = nextNode.getNextAlph();
		}
		return result;
	} 
	
	public String listByLikes(){
		String result = "";
		Node nextNode = headLike;
		while(nextNode != null){
			result += songToString(nextNode.getTune());			
			nextNode = nextNode.getNextLike();
		}
		
		return result;
	}
	
	//return true if node contains tune with corresponding artist + tune
	public boolean isCorrectNode(String artist, String title, Node checkNode){
		if(checkNode.getTune().getArtist().equals(artist) && checkNode.getTune().getTitle().equals(title)){
			return true;
		} else {
			return false;
		}
	}
	
	//return 0 if same, positive int if t1 > t2, negative if t2 > t1
	public int compareTuneAlph(Tune t1, Tune t2){
		String t1String = getTuneAndArtist(t1.getTitle(), t1.getArtist());
		String t2String = getTuneAndArtist(t2.getTitle(), t2.getArtist());
		int compare = t1String.compareTo(t2String);
		return compare;
	}
	
	public String getTuneAndArtist(String tune, String artist){
		return artist + " " + tune; 
	}
	
	public String songToString(Tune t){
		return t.getArtist() + "\n" + t.getTitle() + "\n" + t.getLikes() +"\n";
	}
	
}

class Node {
	//tune at current node
	private Tune theTune;
	//next tune in alphabetical order
	private Node nextAlph;
	//next tune in popularity order
	private Node nextLike;
	
	public Node(Tune t, Node nA, Node nL){
		theTune = t;
		nextAlph = nA;
		nextLike = nL;
	}
	

	
	public Tune getTune(){
		return theTune;
	}
	
	public Node getNextAlph(){
		return nextAlph;
	}
	
	public Node getNextLike(){
		return nextLike;
	}
	
	public void setNextAlph(Node node){
		nextAlph = node;
	}
	
	public void setNextLike(Node node){
		nextLike = node;
	}
	
	
	
	
}

class Tune{
	//variables defining tune
	private String artist;
	private String title;
	private int likes;
	
	//constructor
	public Tune(String a, String t){
		artist = a;
		title = t;
		
	}
	
	public String getArtist(){
		return artist;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getLikes(){
		return likes;
	}
	
	//like method
	public void like(){
		likes++;
	}
}