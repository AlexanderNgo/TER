package ter_combined;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aboxassertion implements Assertion{
	private String conceptName;
	private Float probability;
	private String idService;
	private static final Pattern pattern=Pattern.compile("(\\w+)\\((\\w+)\\,(\\d+(\\.\\d+)?)\\)");

	public Aboxassertion(String fileline) {
		Matcher matcher = pattern.matcher(fileline);
		if (matcher.matches()) {
			this.conceptName = matcher.group(1).trim();
			this.idService = matcher.group(2).trim();
			this.probability = Float.parseFloat(matcher.group(3).trim());
		}
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public Float getProbability() {
		return probability;
	}

	public void setProbability(Float probability) {
		this.probability = probability;
	}

	public String getIdService() {
		return idService;
	}

	public void setIdService(String idService) {
		this.idService = idService;
	}

	public static Pattern getPattern() {
		return pattern;
	}

	@Override
	public String toString() {
		return "Aboxassertion [conceptName=" + conceptName + ", probability=" + probability + ", idService=" + idService
				+ "]";
	}
	protected Aboxassertion clone() throws CloneNotSupportedException {
        // Call the super class's clone method
        return (Aboxassertion) super.clone();
    }
	
}
