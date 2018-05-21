import java.awt.Color;;

public enum Category {
	
	Bus (Color.GREEN),
	Underground (Color.BLUE),
	Train (Color.RED),
	None (Color.BLACK);
	
	private Color c;
	
	Category(Color c){
		this.c = c;
	}
	
	public Color getColor(){
		
		return c;
		
	}

}
