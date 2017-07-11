package foil.way.two;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MyComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		// TODO Auto-generated method stub
		@SuppressWarnings("unchecked")
		List<Double> l1= (ArrayList<Double>)o1;
		@SuppressWarnings("unchecked")
		List<Double> l2= (ArrayList<Double>)o2;
		if(l1.size() != l2.size())
			return 0;
		else{
			int length = l1.size() - 1;
			if(l1.get(length) > l2.get(length))
				return 1;
			else
				return -1;
		}
	}

}
