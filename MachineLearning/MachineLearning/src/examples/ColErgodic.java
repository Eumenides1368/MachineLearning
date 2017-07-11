package examples;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColErgodic {
	
	//判断两个ArrayList是否相等
	public static boolean isEqual(List<Double> u1, List<Double> u2) {
		
		if(u1.size() != u2.size()){
			System.out.println("数据大小有误!");
			return false;
		}else{
			int length = u1.size();
			double[] d1 = new double[length];
			
			for(int i = 0; i < length; i++){
				System.out.println(u1.get(i));
				d1[i] = u1.get(i);
			}
			
			double[] d2 = new double[length];
			
			for(int i = 0; i < length; i++){
				System.out.println(u2.get(i));
				d2[i] = u2.get(i);
			}
			
			System.out.println("=====================");
			for(int i = 0; i < length; i++){
				double sub = Math.abs(u1.get(i) - u2.get(i));
				if(sub > 0.01)
					return false;
			}
			 
		
		}
		
		return true;
		
	}
	
	public static boolean isEqualList(List<ArrayList<Double>> u1, List<ArrayList<Double>> u2){
		if(u1.size() != u2.size()){
			System.out.println("数据大小有误!");
			return false;
		}else{
			boolean[] flag = new boolean[u1.size()];
			
			for(int i = 0; i < u1.size(); i++){
				if(u1.get(i).size() != u2.get(i).size())
					return false;
				flag[i] = isEqual(u1.get(i), u2.get(i));
				System.out.println(flag[i]);
			}
			System.out.println("======================");
			for(int i = 0; i < flag.length; i++){
				System.out.println(flag[i] + "");
				if(flag[i] == false)
					break;
				return true;
			}
			return false;
		}
		
	}


	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] d1 = {1.003, 2.338, 4.344};
		double[] d11 = {1.003, 2.338, 4.344};
		double[] d2 = {1.004, 2.835, 4.343};
		double[] d22 = {1.004, 2.335, 4.343};
		
		ArrayList<Double> u1 = new ArrayList<Double>();
		ArrayList<Double> u11 = new ArrayList<Double>();
		ArrayList<Double> u2 = new ArrayList<Double>();
		ArrayList<Double> u22 = new ArrayList<Double>();
		
		List<ArrayList<Double>> du1 = new ArrayList<ArrayList<Double>>();
		List<ArrayList<Double>> du2 = new ArrayList<ArrayList<Double>>();
		
		for(int i = 0; i < 3; i++){
			u1.add(d1[i]);
			u11.add(d11[i]);
			u2.add(d2[i]);
			u22.add(d22[i]);
		}
		du1.add(u1);
		du1.add(u11);
		du2.add(u2);
		du2.add(u22);
		
		for (ArrayList<Double> arrayList : du1) {
			System.out.println("du1" + arrayList);
		}
		
		for (ArrayList<Double> arrayList : du2) {
			System.out.println("du2" + arrayList);
		}
		
		System.out.println(isEqualList(du1, du2));
	}
		

}
