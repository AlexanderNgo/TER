package ter_combined;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Leaf extends Node implements Assertion,Cloneable{
	static String regex = "(\\w+)([><]=?)(\\d*\\.?\\d+)(\\w+)";
	static Pattern pattern= Pattern.compile(regex);
	private String concept;
	private String service;
	private String op;
	private float probability;


	public Leaf(String expression) throws Exception {
		super();
		Matcher matcher =pattern.matcher(expression);
		if(matcher.matches()) {
			this.concept=matcher.group(1);
			this.op=matcher.group(2);
			this.probability=Float.parseFloat(matcher.group(3));
			this.service=matcher.group(4);
		}else {
			throw new Exception("creating leaf wasnt possible");
		}
	}
	//constructor for tbox assertion only
	public Leaf(String concept, String op, float probability) {
		super();
		this.concept = concept;
		this.op = op;
		this.probability = probability;
		this.service=null;
	}
	

	public Leaf(String concept, String service, String op, float probability) {
		super();
		this.concept = concept;
		this.service = service;
		this.op = op;
		this.probability = probability;
	}
	public String toString() {
		return concept+op+probability+service;
	}
	public void invertop() {
		switch(this.op) {
		case ">":
			this.op="<=";
			break;
		case "<":
			this.op=">=";
			break;
		case "<=":
			this.op=">";
			break;
		case ">=":
			this.op="<";
			break;
		}
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getOp() {
		return op;
	}
	public void setOp(String op) {
		this.op = op;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	protected Leaf clone() throws CloneNotSupportedException {
        // Call the super class's clone method
        return (Leaf) super.clone();
    }
	public Boolean same_as_onlyfor_tbox(Leaf leaf) {
		if(this.concept.equals(leaf.getConcept())) {
			switch(this.op) {
			case "<":
				if((leaf.getOp().equals("<"))&&(leaf.getProbability()<=this.probability)) {
					return true;
				}
				if((leaf.getOp().equals("<="))&&(leaf.getProbability()<this.probability)) {
					return true;
				}
				break;
			case "<=":
				if((leaf.getOp().equals("<"))&&(leaf.getProbability()<=this.probability)) {
					return true;
				}
				if((leaf.getOp().equals("<="))&&(leaf.getProbability()<=this.probability)) {
					return true;
				}
				break;
			case ">":
				if((leaf.getOp().equals(">"))&&(leaf.getProbability()>=this.probability)) {
					return true;
				}
				if((leaf.getOp().equals(">="))&&(leaf.getProbability()>this.probability)) {
					return true;
				}
				break;
			case ">=":
				if((leaf.getOp().equals(">"))&&(leaf.getProbability()>=this.probability)) {
					return true;
				}
				if((leaf.getOp().equals(">="))&&(leaf.getProbability()>=this.probability)) {
					return true;
				}
				break;
			}
			return false;
		}
		return false;
	}
}
