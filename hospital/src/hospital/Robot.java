package hospital;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import repast.simphony.query.space.grid.GridCellNgh;
import repast.simphony.random.RandomHelper;
import repast.simphony.space.SpatialMath;
import repast.simphony.space.continuous.ContinuousSpace;
import repast.simphony.space.grid.Grid;
import repast.simphony.space.grid.GridPoint;
import repast.simphony.util.ContextUtils;
import repast.simphony.util.SimUtilities;
import repast.simphony.context.Context;
import repast.simphony.engine.schedule.ScheduledMethod;
import repast.simphony.engine.watcher.Watch;
import repast.simphony.engine.watcher.WatcherTriggerSchedule;
import repast.simphony.query.space.grid.GridCell;
import repast.simphony.space.continuous.NdPoint;

public class Robot{
	private ContinuousSpace<Object> space;
	private Grid<Object> grid;
	private ArrayList<Patient> carry;
	private int id; 
	private ArrayList<RobotMessage> mailbox;
	private Context<Object> context; 
	private ArrayList<Salle> salles; 
	//private ArrayList<ArrayList<Patient>> patient;
	
	
	
	NdPoint cr = new NdPoint (7,3);
	Salle ConsultationRoom = new Salle("ConsultationRoom", cr, 4);
	
	NdPoint g = new NdPoint (3,8);
	Salle Gynecology = new Salle("Gynecology", g, 6);
	
	NdPoint or = new NdPoint (3,12);
	Salle OperatingRoom = new Salle("OperatingRoom", or, 12);
	
	NdPoint p = new NdPoint (3,15);
	Salle Psychology = new Salle("Psychology", p, 6);
	
	NdPoint sr = new NdPoint (10,15);
	Salle ShockRoom = new Salle("ShockRoom", sr, 8);
	
	NdPoint r = new NdPoint (10,10);
	Salle Radiography = new Salle("Radiograpgy", r, 4);
	
	NdPoint e = new NdPoint(12,5); 
	Salle Exit = new Salle("Exit",e,1); 
	
	
	
	public Robot(ContinuousSpace<Object> space, Grid<Object> grid, int id, Context<Object> context) { 
		this.space = space; 
		this.grid = grid;
		this.id = id;
		this.carry = new ArrayList<Patient>(); 
		this.mailbox = new ArrayList<RobotMessage>();
		this.context = context; 
		this.salles = new ArrayList<Salle>(); 
	
	}
	public Integer getId() {
		return this.id; 
	}

	public boolean isCarry() {
		return !carry.isEmpty(); 
	}
	public void take(Patient patientType) {
		if (!isCarry()) {
			/*grid ou context change*/ 
			this.carry.add(patientType);
			
		}
	}

	public void drop() {
		this.carry.remove(0); 
		}
	
	public void moveToCentralPoint() {
		NdPoint L = space.getLocation(this);
		if (L.getX()==7 && L.getY()==3) {
			space.moveByVector(this, 1,Math.PI);
			space.moveByVector(this,7,Math.PI/2);
		}
		if(L.getX()==3 && L.getY()==8) {
			space.moveByVector(this,3, 0);
			space.moveByVector(this,2,Math.PI/2);
		}
		if (L.getX()==3 && L.getY()==12) {
			space.moveByVector(this, 3,0);
			space.moveByVector(this,2 -Math.PI/2);
		}
		if (L.getX()==3 && L.getY()==15) {
			space.moveByVector(this, 3,0);
			space.moveByVector(this, 5,-Math.PI/2);
		}
		if (L.getX()==10 && L.getY()==15) {
			space.moveByVector(this, 4, Math.PI);
			space.moveByVector(this, 5,-Math.PI/2);
		}
		if (L.getX()==10 && L.getY()==10) {
			space.moveByVector(this, 4, Math.PI);
		}
		
	}
	
	public void moveFromCentralPointToSalle(Salle Salle) {
		NdPoint L = Salle.getLocalisation();
		Patient patient = this.carry.get(0); 
		if (L.getX()==7 && L.getY()==3) {
			space.moveByVector(this,7,-Math.PI/2);
			space.moveByVector(this, 1,0);
			space.moveByVector(patient,0,7);
			space.moveByVector(patient, 1,0);
		}
		if(L.getX()==3 && L.getY()==8) {
			space.moveByVector(this,2, -Math.PI/2);
			space.moveByVector(this,3, Math.PI);
			space.moveByVector(patient,2, -Math.PI/2);
			space.moveByVector(patient,3, Math.PI);
		}
		if (L.getX()==3 && L.getY()==12) {
			space.moveByVector(this,2, Math.PI/2);
			space.moveByVector(this, 3,Math.PI);
			space.moveByVector(patient,2, Math.PI/2);
			space.moveByVector(patient, 3,Math.PI);
		}
		if (L.getX()==3 && L.getY()==15) {
			space.moveByVector(this, 5,Math.PI/2);
			space.moveByVector(this, 3,Math.PI);
			space.moveByVector(patient, 5,Math.PI/2);
			space.moveByVector(patient, 3,Math.PI);
		}
		if (L.getX()==10 && L.getY()==15) {
			space.moveByVector(this, 5, Math.PI/2);
			space.moveByVector(this, 4, 0);
			space.moveByVector(patient, 5, Math.PI/2);
			space.moveByVector(patient, 4, 0);
		}
		if (L.getX()==10 && L.getY()==10) {
			space.moveByVector(this, 4, 0);
			space.moveByVector(patient, 4, 0);
		}
		NdPoint myPoint = space.getLocation(this);
		grid.moveTo(this, (int) myPoint.getX(), (int) myPoint.getY());
		grid.moveTo(patient, (int) myPoint.getX(), (int) myPoint.getY()); 
	}
	

	public void moveFromExitToEntry() {
		NdPoint myPoint = space.getLocation(this);
		//I drop the patient 
		Patient patient = carry.get(0); 
		this.drop();  
		//and I move to entry
		double angle = Math.PI;
		space.moveByVector(this,11,angle);  
		grid.moveTo(this,1,5);
		space.moveByVector(patient,11,angle);  
		grid.moveTo(patient,1,5);
	}	
	
	
	
	public void choosePatient() {
		double chance = Math.random(); 
		ArrayList<ArrayList<Patient>> patient = HospitalBuilder.getPatient(); 
		int sum = patient.get(0).size() + patient.get(1).size() + patient.get(2).size(); 
		//chance to be taken in care depending on my color/gravity
		double chanceRed = (patient.get(0).size() + 0.5*patient.get(2).size() + (1/3)*patient.get(1).size())/sum;
		double chanceYellow = 2*patient.get(1).size()/sum/3;
		/*double chanceGreen = patient.get(0)/sum/2;
		 * the sum of chanceRed, chanceYellow and chanceGreen is equal to 1*/ 
		//comparison with variable chance
		Context<Object> context = ContextUtils.getContext(this);
		if (chance<=chanceRed) {
			//if there is red patients  
			if (patient.get(0).size()!=0) {
				//I take a red patient
				this.take(patient.get(0).get(0));
				//il faut les enlever du context 
				HospitalBuilder.removePatient(0); 
			}
			else if(patient.get(1).size()!=0) {
				//I take a yellow patient 
				this.take(patient.get(1).get(0)); 
				HospitalBuilder.removePatient(1); 
			}
			else {
				//I take a green patient 
				this.take(patient.get(2).get(0)); 
				HospitalBuilder.removePatient(2);
			}
		}
			else if(chance<=chanceRed + chanceYellow) {
				//I take a yellow patient 
				this.take(patient.get(1).get(0)); 
				HospitalBuilder.removePatient(1); 
			}
			else
			//I take a green patient 
			this.take(patient.get(2).get(0));
			HospitalBuilder.removePatient(2); 
		
	}
	
	public void careScheduling(){
		if (this.carry.get(0) instanceof RedPatient){
			double chance = Math.random();
			if (chance<=0.5) {
				salles.add(ConsultationRoom); 
			}
			else if (chance<=0.6) {
				salles.add(Psychology);
			}
			else if (chance<=0.7) {
				salles.add(Gynecology);
			}
			else if (chance<=0.8) {
				salles.add(Radiography);
			}
			else if (chance<=0.9) {
				salles.add(OperatingRoom);
			}
			else if (chance<=1) {
				salles.add(ShockRoom);
			}
			chance = Math.random();
			if (chance<=1/5) {
					salles.add(Psychology);
				}
			else if (chance<=1/5) {
					salles.add(Gynecology);
				}
			else if (chance<=1/5) {
					salles.add(Radiography);
				}
			else if (chance<=1/5) {
					salles.add(OperatingRoom);
				}
			else if (chance<=1/5) {
					salles.add(ShockRoom);
				}
			chance = Math.random(); 
			if (chance<=1/5) {
					salles.add(Psychology);
				}
			else if (chance<=2/5) {
					salles.add(Gynecology);
				}
			else if (chance<=3/5) {
					salles.add(Radiography);
				}
			else if (chance<=4/5) {
					salles.add(OperatingRoom);
				}
			else if (chance<=1) {
					salles.add(ShockRoom);
				}
		}
		if (this.carry.get(0) instanceof YellowPatient){
			double chance = Math.random();
			if (chance<=0.5) {
				salles.add(ConsultationRoom); 
			}
			else if (chance<=0.65) {
				salles.add(Psychology);
			}
			else if (chance<=0.8) {
				salles.add(Gynecology);
			}
			else if (chance<=0.9) {
				salles.add(Radiography);
			}
			else if (chance<=1) {
				salles.add(OperatingRoom);
			}
			
			chance = Math.random();
			if (chance<=1/4) {
					salles.add(Psychology);
				}
			else if (chance<=2/4) {
					salles.add(Gynecology);
				}
			else if (chance<=3/4) {
					salles.add(Radiography);
				}
			else if (chance<=1) {
				salles.add(OperatingRoom);
			}
		}
		if (this.carry.get(0) instanceof GreenPatient){
			double chance = Math.random();
			if (chance<=0.6) {
				salles.add(ConsultationRoom); 
			}
			else if (chance<=0.7) {
				salles.add(Psychology);
			}
			else if (chance<=0.8) {
				salles.add(Gynecology);
			}
			else if (chance<=0.9) {
				salles.add(Radiography);
			}
			else if (chance<=1) {
				salles.add(OperatingRoom);
			}
		}
	}
	
	
	
	public void moveTowards(GridPoint pt) {
		// only move if we are not already in this grid location
		if (!pt.equals(grid.getLocation(this))) {
			NdPoint myPoint = space.getLocation(this);
			NdPoint otherPoint = new NdPoint(pt.getX(), pt.getY());
			double angle = SpatialMath.calcAngleFor2DMovement(space, myPoint, otherPoint);
			space.moveByVector(this, 2, angle, 0);
			myPoint = space.getLocation(this);
			grid.moveTo(this, (int) myPoint.getX(), (int) myPoint.getY());
		}
	}
	
	
	public void hello() {
		RobotMessage m = new RobotMessage(this.id, "hello: I have a patient", "inform");
		HashMap<String, Object> content = new HashMap<String, Object>();
		content.put("Location", this.grid.getLocation(this));
		m.setContent(content);
		send(m);
	}
	
	protected void send(RobotMessage m) {//Sends the message m to all other vehicles
		for (Object obj : context)
			if (obj instanceof Robot && ((Robot) obj).id != id)
				((Robot) obj).receive(m);
	}
			
			

	private void receive(RobotMessage m) {
		mailbox.add(m);
	}

	private RobotMessage read() {	//returns a message
		if (mailbox.size() > 0)
			return mailbox.remove(0);
		return null;
	}
	
	@ScheduledMethod(start = 1, interval = 1)
	private void makeDecision() {
		NdPoint myPoint = space.getLocation(this); 
		//If I am located at exit 
		if (myPoint.getX() == 12 && myPoint.getY() == 5) {
			this.moveFromExitToEntry(); 
		}
		else if (myPoint.getX() == 1 && myPoint.getY() == 5 && !isCarry()) {
			this.choosePatient();
			this.careScheduling();
		}
		else if (isCarry() && !this.salles.isEmpty()) {
			this.moveToCentralPoint();
			this.moveFromCentralPointToSalle(salles.get(0)); 
		}
		else if(this.salles.isEmpty()) {
			this.moveToCentralPoint(); 
			this.moveFromCentralPointToSalle(Exit);
		}
	}
}

