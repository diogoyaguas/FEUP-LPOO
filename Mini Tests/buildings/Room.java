package buildings;

import java.util.ArrayList;

public class Room extends Facility{
	
	protected Building b;
	protected String number;
	protected int floor;
	protected int capacity;
	protected ArrayList<User> users = new ArrayList<>();

	public Room(Building b, String string, int i, int j) throws DuplicateRoomException {
		
		if(b.maxFloor < i) {
			throw new IllegalArgumentException();
		}
		this.b = b;
		for(int k = 0; k < this.b.rooms.size(); k++) {
			if(this.b.rooms.get(k).getNumber() == string) {
				throw new DuplicateRoomException();
			}
		}
		this.number = string;
		this.floor = i;
		this.capacity = j;
		this.b.capacity+=j;
		this.b.rooms.add(this);
	}

	public Room(Building b2, String string, int i) throws DuplicateRoomException {
		
		if(b2.maxFloor < i) {
			throw new IllegalArgumentException();
		}
		this.b = b2;
		
		for(int k = 0; k < this.b.rooms.size(); k++) {
			if(this.b.rooms.get(k).getNumber() == string) {
				throw new DuplicateRoomException();
			}
		}
		this.number = string;
		this.floor = i;
		this.b.rooms.add(this);
	}

	public Building getBuilding() {
		
		return this.b;
	}

	public String getNumber() {
		
		return this.number;
	}

	@Override
	public String getName() {
		
		return this.b.getName()+this.getNumber();
	}

	public int getFloor() {
		
		return this.floor;
	}
	
	@Override
	public String toString() {
		
		return "Room(" + this.b.getName() + "," + this.number + ")";
	}

	public int getCapacity() {
		
		return this.capacity;
	}

	public void authorize(User u1) {
	
		this.users.add(u1);		
	}
	
	@Override
	public boolean canEnter(User u1) {
		
		for(User s: users) {
			if(s.equals(u1)) {
				return true;
			}
		}
		return false;
	}

}
