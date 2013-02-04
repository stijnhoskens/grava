package graph;

public class Node {
	
	private String id;
	
	public Node() {
		
	}
	
	public Node(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}
	
	public void printId() {
		System.out.println(id);
	}
	
	@Override
	public String toString() {
		return id;
	}

}
