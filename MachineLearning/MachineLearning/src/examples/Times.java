package examples;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Times {
	
	
	public void generate(){
		Random rand = new Random();
		List<Integer> arr = new ArrayList<Integer>();
		
		for(int i = 0; i <100; i++){
			int num = rand.nextInt(100);
			System.out.print(num + " ");
			arr.add(num);
		}
		
		/*
		List<Integer> times = new ArrayList<Integer>();
		for (Integer integer : arr) {
			if(!times.contains(integer)){
				times.add(integer);
			}
		}
		System.out.println();
		System.out.println("长度" + times.size());
		*/
		
		Set<Integer> times = new HashSet<Integer>();
		for (Integer integer : arr) {
			times.add(integer);
		}
		System.out.println();
		System.out.println("长度" + times.size());
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Times t = new Times();
		t.generate();
	}

}
