package hospital;
import java.util.ArrayList;

import repast.simphony.space.continuous.NdPoint; 


public class Salle {
	
	public String name;
	public NdPoint localisation;
	public Integer dureeTraitement; 
	
	public Salle(String name, NdPoint localisation,Integer dureeTraitement) {
		this.name = name; 
		this.localisation = localisation; 
		this.dureeTraitement = dureeTraitement; 
	}
	
	public String getName() {
		return this.name; 
	}
	
	public NdPoint getLocalisation(){
		return this.localisation; 
	}
	
	public Integer getDureeTraitement() {
		return this.dureeTraitement; 
	}
	
	
}
