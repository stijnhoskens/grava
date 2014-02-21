package node;

public class NumberedNode implements Node {

	private final int number;

	public NumberedNode(int number) {
		this.number = number;
	}

	public int getNumber() {
		return number;
	}

	public String toString() {
		return Integer.toString(number);
	}

	@Override
	public int hashCode() {
		return number;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NumberedNode other = (NumberedNode) obj;
		return number == other.number ? true : false;
	}

}
