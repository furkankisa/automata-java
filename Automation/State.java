import java.util.*;;

public class State {
	Hashtable table  = new Hashtable();
	private int x, y;
	String stateName;
	String kind;
	
	public String getKind() {
		return kind;
	}
	public void setKind(String type) {
		this.kind = type;
	}
	public State(String name) {
		this.stateName = name;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String name) {
		this.stateName = name;
	}
	public void addState(String name, char symbol){
		Vector v = (Vector) table.get(new Character(symbol));
		
		if(v == null){
			v = new Vector();
			v.add(name);
			table.put(new Character(symbol), v);
		
		}else
			v.add(name);
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
