package buildings;

import java.util.ArrayList;

public class Building extends Facility{
	
	protected String name;
	protected int minFloor;
	protected int maxFloor;
	protected int capacity;
	protected ArrayList<Room> rooms = new ArrayList<>();

	public Building(String string, int i, int j) {
		
		if(i > j) {
			throw new IllegalArgumentException();
		}
		
		this.name = string;
		this.minFloor = i;
		this.maxFloor = j;
		this.capacity = 0;
	}
	
	@Override
	public String getName() {
		
		return this.name;
	}

	public int getMinFloor() {
		
		return this.minFloor;
	}

	public int getMaxFloor() {
		
		return this.maxFloor;
	}
	
	@Override
	public String toString() {
		
		return "Building(" + this.name + ")";
	}

	public Object getCapacity() {
		
		return this.capacity;
	}
	
	@Override
	public boolean canEnter(User u1) {
		
		for(int i = 0; i < rooms.size(); i++ ) {
			for(int j = 0; j < rooms.get(i).users.size(); j++) {
				
				if(rooms.get(i).users.get(j).equals(u1)) {
					return true;
				}
			}
		}
		return false;
	
	}

}
