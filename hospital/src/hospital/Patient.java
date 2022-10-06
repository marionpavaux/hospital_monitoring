package hospital;

import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class Patient {
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private int id; 
	
	public Patient(ContinuousSpace<Object> space, Grid<Object> grid, int id) { 
		this.space = space;
		this.grid = grid;
	}
	public Integer getId() {
		return this.id; 
	}
}
