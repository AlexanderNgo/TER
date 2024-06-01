package ter_combined;

import java.util.ArrayList;

public class MiddleNode extends Node implements Cloneable{
	private Logic_operator operator;
	private ArrayList<Node> children;
	public Logic_operator getOperator() {
		return operator;
	}
	public void setOperator(Logic_operator operator) {
		this.operator = operator;
	}
	public ArrayList<Node> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}
	public MiddleNode(char x) throws Exception {
		super();
		if(x=='&') {
			this.operator=Logic_operator.and;
		}
		else {
			if(x=='|') {
				this.operator=Logic_operator.or;
			}
			else {
				if(x=='!') {
					this.operator=Logic_operator.not;
					}else {throw new Exception("cant create a middlenode with a char other than {& , | , !}");}
				}
			}
		this.children = new ArrayList<Node>();
	}
	public void add(Node arbre) {
		this.children.add(arbre);
	}
	public String toString() {
		return this.operator.toString()+ this.children.toString();
	}
	public void invertLOP() {
		if(this.operator==Logic_operator.and) {
			this.operator=Logic_operator.or;
		}else {
			if(this.operator==Logic_operator.or) {
				this.operator=Logic_operator.and;
			}
		}
	}
	
	protected MiddleNode clone() throws CloneNotSupportedException {
        // Call the super class's clone method
        return (MiddleNode) super.clone();
    }
}
