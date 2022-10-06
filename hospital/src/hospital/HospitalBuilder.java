package hospital;

import java.util.ArrayList;
import java.util.List;

import repast.simphony.context.Context;
import repast.simphony.context.space.continuous.ContinuousSpaceFactory;
import repast.simphony.context.space.continuous.ContinuousSpaceFactoryFinder;
import repast.simphony.context.space.graph.NetworkBuilder;
import repast.simphony.context.space.grid.GridFactory;
import repast.simphony.context.space.grid.GridFactoryFinder;
import repast.simphony.dataLoader.ContextBuilder;
import repast.simphony.engine.environment.RunEnvironment;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.parameter.Parameters;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.continuous.NdPoint;
import repast.simphony.space.continuous.RandomCartesianAdder;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridBuilderParameters;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.space.grid.SimpleGridAdder;
import repast.simphony.space.grid.WrapAroundBorders;
import repast.simphony.util.ContextUtils;

public class HospitalBuilder implements ContextBuilder<Object> {

	public static ArrayList<ArrayList<Patient>> patient;
	
	@Override
	public Context build(Context<Object> context) {

		
		NetworkBuilder <Object > netBuilder = new NetworkBuilder <Object > ("hospital network", context , true );
		netBuilder.buildNetwork ();
		
		
		context.setId("hospital");

		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.WrapAroundBorders(), 12, 16);

		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		// Correct import : import repast.simphony.space.grid.WrapAroundBorders
		// ;
		Grid<Object> grid = gridFactory.createGrid("grid", context, new GridBuilderParameters<Object>(new WrapAroundBorders(), new SimpleGridAdder<Object>(), true, 12, 16));
		
		Parameters params = RunEnvironment.getInstance().getParameters();
		int robotCount = (Integer)params.getValue("robot_count");
		for (int i = 0; i < robotCount; i++) {
			int id = i; 
			Robot rob = new Robot(space, grid, id,context); 
			context.add(rob);
			space.moveTo(rob, 1,5);
			grid.moveTo(rob, 1,5);
		}
		int redPatientCount = (Integer)params.getValue("red_patient_count"); 
		int yellowPatientCount = (Integer)params.getValue("yellow_patient_count"); 
		int greenPatientCount = (Integer)params.getValue("green_patient_count"); 
		
		for (int i = 0; i < redPatientCount; i++) {
			int id = i; 
			RedPatient rp = new RedPatient(space, grid, id); 
			context.add(rp);
			space.moveTo(rp, 1,5);
			grid.moveTo(rp, 1,5);
		}
		
		for (int i = 0; i < yellowPatientCount; i++) {
			int id = i; 
			YellowPatient yp = new YellowPatient(space, grid, id); 
			context.add(yp);
			space.moveTo(yp, 1,5);
			grid.moveTo(yp, 1,5);
		}
		
		for (int i = 0; i < greenPatientCount; i++) {
			int id = i; 
			GreenPatient gp = new GreenPatient(space,grid,id); 
			context.add(gp);
			space.moveTo(gp, 1,5);
			grid.moveTo(gp, 1,5);
		}
		
		
		
		for (Object obj : context) {
			NdPoint pt = space.getLocation(obj);
			grid.moveTo(obj, (int) pt.getX(), (int) pt.getY());
		}
		return context;
	}
	
	@ScheduledMethod(start = 1, interval = 5)
	public void addPatient() {
		double chance = Math.random();
		//il y a 20% de chance qu'un patient de gravité sévère arrive
		Context<Object> context = ContextUtils.getContext(this);
		//on recrée un espace du même context pour les nouveaux arrivants 
		ContinuousSpaceFactory spaceFactory = ContinuousSpaceFactoryFinder.createContinuousSpaceFactory(null);
		ContinuousSpace<Object> space = spaceFactory.createContinuousSpace("space", context,new RandomCartesianAdder<Object>(), new repast.simphony.space.continuous.WrapAroundBorders(), 12, 16);
		GridFactory gridFactory = GridFactoryFinder.createGridFactory(null);
		Grid<Object> grid = gridFactory.createGrid("grid", context, new GridBuilderParameters<Object>(new WrapAroundBorders(), new SimpleGridAdder<Object>(), true, 12, 16));
		
		
		if (chance<=0.2) {
			int index = patient.get(0).size() + 1 ;
			Patient obj = new RedPatient(space,grid,index); 
			context.add(obj);
			patient.get(0).add(obj);
		}
		
		//il y a 30% de chance qu'un patient de gravité modérée arrive
		else if(chance<=0.5) {
			int index = patient.get(1).size() + 1 ;
			Patient obj = new YellowPatient(space,grid,index); 
			context.add(obj);
			patient.get(1).add(obj); 
		}
		
		//il y a 40% de chance qu'un patient léger arrive 
		else if(chance<=0.90) {
			int index = patient.get(2).size() + 1 ;
			Patient obj = new GreenPatient(space,grid,index); 
			context.add(obj);
			patient.get(2).add(obj); 
		} 
		// il y a 10% de chance qu'aucun patient n'arrive	 
	}
	
	public static ArrayList<ArrayList<Patient>> getPatient(){
		return patient; 
	}
	
	public void setPatient(int index, Patient p) {
		HospitalBuilder.patient.get(index).add(p); 
	}
	
	public static void removePatient(int index) {
		HospitalBuilder.patient.get(index).remove(0); 
	}
}
