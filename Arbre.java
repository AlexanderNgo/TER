package ter_combined;

import java.util.ArrayList;
import java.util.Stack;
public class Arbre {
	Node node;
	ArrayList<Tboxassertion> tbox=new ArrayList<Tboxassertion>();
	ArrayList<Assertion> ar=new ArrayList<Assertion>();
	Stack<Node> stack = new Stack<>();
	Boolean bool=false;
	Integer inte=0;

	public Arbre(String str) throws Exception {
		this.node=construct(str);
	}
	
	public void setTbox(ArrayList<Tboxassertion> tbox) {
		this.tbox=tbox;
	}
	public ArrayList<Assertion> getAr() {
		return ar;
	}


	public void setAr(ArrayList<Assertion> ar) {
		this.ar = ar;
	}


	public int findOp(String str) {
		int count=0;
		char x;
		for(int i=0;i<str.length();i++) {
			x=str.charAt(i);
			switch(x) {
			case '(':
				count++;
				break;
			case ')':
				count--;
				break;
			case '&':
				if(count==0) {return i;}
				break;
			case '|':
				if(count==0) {return i;}
				break;
			}
		}
		if(str.charAt(0)=='!') {
			return 0;
		}else {
			return -1;
		}
	}
	public String correctparenthesis(String str) {
		if((str.charAt(0)!='(')||(str.charAt(str.length()-1)!=')')){
			return str;
		}else {
			int count=0;
			for(int i =0;i<str.length();i++) {
				char x=str.charAt(i);
				if(x=='(') {
					count++;
				}else {
					if(x==')') {
						count--;
					}
				}
				if((count==0)&&(i<str.length()-1)){
					return str;
				}
			}
			return str.substring(1,str.length()-1);
		}
	}
	/*
	public Node negate(String str) throws Exception {
		char x=str.charAt(i);
		
		if((x=='&')||(x=='|')) {
			MiddleNode node= new MiddleNode(x);
			node.add(negate(str.substring(0,i)));
			node.add(negate(str.substring(i+1)));
		}
		return new Leaf("");
	}
	
	
	*/

	
	public Node construct(String str) throws Exception {
		int opIndex = findOp(str);
		if(opIndex>-1) {
			MiddleNode mn=null;
			char op =str.charAt(opIndex);
			if((op=='&')||(op=='|')) {
				mn= new MiddleNode(op);
				mn.add(construct(correctparenthesis(str.substring(0,opIndex))));
				mn.add(construct(correctparenthesis(str.substring(opIndex+1))));
			}else {
				if(op=='!'){
					mn = new MiddleNode(op);
					mn.add(construct(correctparenthesis(str.substring(1))));
				}
			}
			return mn;
		}else {
			return new Leaf(correctparenthesis(str));
		}  
	}
	
	
	public Node nnf(Node node,Boolean bool) {
		if(node instanceof MiddleNode){
			MiddleNode mnode= (MiddleNode) node;
			ArrayList<Node> x = new ArrayList<Node>();
			if (mnode.getOperator()==Logic_operator.not) {
				return nnf(mnode.getChildren().get(0),!bool);
			}else {
				if(bool) {
					mnode.invertLOP();
					}
				for(Node n:mnode.getChildren()) {
					x.add(nnf(n,bool));
				}
				mnode.setChildren(x);
				return mnode;
			}
		}else {
			Leaf leaf = (Leaf) node;
			if (bool) {
				leaf.invertop();
			}
			return leaf;
			
		}
	}
	
	public String toString() {
		return node.toString();
		
	}
	public void calling_reasonate() {
		reasonate(this.node);
		if(bool) {
			System.out.println("une branche a été satisfaite avec la negation du but");
		}else {
			System.out.println("toutes les branches sont inconsistante, but prouvé");
		}
	}
	
	public Boolean checkconsistency() {
		for(int i=0;i<ar.size();i++) {
			 if(ar.get(i) instanceof Leaf) {
				 Leaf leaf=(Leaf) ar.get(i);
				 for(int j=0;j<ar.size();j++) {
					if(ar.get(j) instanceof Aboxassertion) {
						Aboxassertion abass = (Aboxassertion) ar.get(j);
						if(abass.getIdService().equals(leaf.getService())) {
							if(abass.getConceptName().equals(leaf.getConcept())){
								switch(leaf.getOp()) {
								case ">":
									if(abass.getProbability()<=leaf.getProbability()) {
										return false;
									}
									break;
								case "<":
									if(abass.getProbability()>=leaf.getProbability()) {
										return false;
									}
									break;
								case "<=":
									if(abass.getProbability()>leaf.getProbability()) {
										return false;
									}
									break;
								case ">=":
									if(abass.getProbability()<leaf.getProbability()) {
										return false;
									}
									break;
								}
							}
						}
					}	
				}
			 }
		}
		return true;
	}
	public void add_to_KB(Leaf leaf) {
		ar.add(leaf);
		infer_from_Tbox(leaf);
	}
	public void infer_from_Tbox(Leaf leaf){
		if (tbox!=null) {
			for (int i = 0; i < tbox.size(); i++) {
				Tboxassertion ta = tbox.get(i);
				if (ta.getLeaf1().same_as_onlyfor_tbox(leaf)) {
					add_to_KB(new Leaf(ta.getLeaf2().getConcept(), leaf.getService(), ta.getLeaf2().getOp(),
							ta.getLeaf2().getProbability()));
				}
			} 
		}
	}
	public void reasonate(Node node){
		if(bool) {
			return ;
		}else {
			//pas de branche satisfiable
			if(node instanceof Leaf) {
				add_to_KB((Leaf) node);
				if(stack.isEmpty()) {
					bool=checkconsistency();
				}else {
					reasonate(stack.pop());
				}
			}else {
				//dans un connecteur
				MiddleNode x = (MiddleNode) node;
				if (x.getOperator()==Logic_operator.and) {
					stack.push(x.getChildren().get(1));
					reasonate(x.getChildren().get(0));
				}else {
					Stack<Node> stackcloned =(Stack<Node>) stack.clone();
					Integer integer=ar.size();
					reasonate(x.getChildren().get(0));
					//stack=(Stack<Node>) stackcloned.clone();
					stack=stackcloned;
					for(int i=integer;i<ar.size();i++) {
						ar.remove(i);
					}
					reasonate(x.getChildren().get(1));
				}
			}
			
			
		}
		return ;
	}
	
}
