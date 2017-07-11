package decision.tree;

import java.util.Comparator;

public class Comparison implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		String str1 = (String)o1;
		String str2 = (String)o2;
		return str1.compareTo(str2);
	}

}
