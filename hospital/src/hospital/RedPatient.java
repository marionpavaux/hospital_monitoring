package hospital;

import java.util.ArrayList;

import repast.simphony.context.Context;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;

public class RedPatient extends Patient {
	
	public RedPatient(ContinuousSpace<Object> space, Grid<Object> grid, int id) { 
		super(space,grid,id);
	}
}
