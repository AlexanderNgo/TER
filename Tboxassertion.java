package ter_combined;

public class Tboxassertion implements Assertion {
	//leaf1 inclu dans leaf2
	private Leaf leaf1;
	private Leaf leaf2;
	private Boolean or_equal;
	public Leaf getLeaf1() {
		return leaf1;
	}
	public void setLeaf1(Leaf leaf1) {
		this.leaf1 = leaf1;
	}
	public Leaf getLeaf2() {
		return leaf2;
	}
	public void setLeaf2(Leaf leaf2) {
		this.leaf2 = leaf2;
	}
	public Boolean getOr_equal() {
		return or_equal;
	}
	public void setOr_equal(Boolean or_equal) {
		this.or_equal = or_equal;
	}
	public Tboxassertion(Leaf leaf1, Leaf leaf2, Boolean or_equal) {
		super();
		this.leaf1 = leaf1;
		this.leaf2 = leaf2;
		this.or_equal = or_equal;
	}
	
	

}
