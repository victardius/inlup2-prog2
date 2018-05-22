import java.awt.Color;;

public enum Category {
	
	Bus (Color.RED),
	Underground (Color.BLUE),
	Train (Color.GREEN),
	None (Color.BLACK);
	
	private Color c;
	
	Category(Color c){
		this.c = c;
	}
	
	public Color getColor(){
		
		return c;
		
	}

}
