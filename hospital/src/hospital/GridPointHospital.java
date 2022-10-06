package hospital;
import repast.simphony.space.grid.GridPoint;
import java.util.ArrayList;
import java.util.List;

public class GridPointHospital extends GridPoint {
	
	private String roomName; 
	/*Donne le o� les endroits o� le robot doit aller*/ 
	private ArrayList<Integer> localisation; 
	
	public GridPointHospital(int x,int y,String roomName, ArrayList<Integer> localisation) {
		super(x,y); 
		this.roomName = roomName; 
		this.localisation = localisation; 		
	}
	
	public String getRoomName() {
		return this.roomName; 
	}
	
	public List<Integer> getLocalisation(){
		return this.localisation; 
	}
}
