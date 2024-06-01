package ter_combined;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
	

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String path = new String("C:/Users/wissal/Desktop/output.txt");
		File file = new File(path);
		FileReader fr= new FileReader(file);
		BufferedReader reader = new BufferedReader(fr);
		String line;
		ArrayList<Assertion> assertions= new ArrayList<Assertion>();
		while((line=reader.readLine())!=null) {
			assertions.add(new Aboxassertion(line));
		}
		reader.close();
		fr.close();
		for(Assertion a : assertions) {
			System.out.println(a);
		}
		
		try {
			Arbre v=new Arbre("!(GoodRC>0.2S1|GoodTR<0.2S1)&BadRC>0.4S1");
			System.out.println(v);
			v.nnf(v.node,true);
			System.out.println(v);
			v.setAr(assertions);
			v.calling_reasonate();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}