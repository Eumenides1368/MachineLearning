package foil.way.two;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import foil.way.two.MyComparator;

public class KNN {
	public static final int MAX_VALUE = 1000000;
	
	//读取文件中的数据
	public static List<ArrayList<Double>> readData(String filePath) throws IOException{
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(filePath)));
		String str = "";
		List<String> arr = new ArrayList<String>();
		List<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		try {
			while((str = reader.readLine()) != null){
				String[] field = str.split(" ");
				List<Double> temp = new ArrayList<Double>();
				for(int i = 0; i < field.length; i++)
					temp.add(Double.parseDouble(field[i]));
				data.add((ArrayList<Double>) temp);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			reader.close();
		}
		
		for (ArrayList<Double> arrayList : data) {
			System.out.println(arrayList);
		}
		return data;
	}
	
	public static double calDistance(ArrayList<Double> train, ArrayList<Double> test){
		if(train.size() != (test.size() + 1))
			System.out.println("数据有误！");
		double distance = 0.0;
		for(int i = 0; i < test.size(); i++){
			double temp = train.get(i) - test.get(i);
			distance += temp * temp;
		}
		distance = Math.sqrt(distance);
		return distance;
	}
	
	
	@SuppressWarnings("unchecked")
	public static void getAllDistance(List<ArrayList<Double>> trainData, List<ArrayList<Double>> testData, int k){
		
		for (ArrayList<Double> test : testData) {
			
			for (ArrayList<Double> train : trainData) {
				double dis = calDistance(train, test);
				train.add(dis);
			}
			
			MyComparator mc = new MyComparator();
			Collections.sort(trainData, mc);
			
			System.out.println("对于测试数据" + test + "排序后的值为");
			for (int i = 0; i < trainData.size(); i++) {
				System.out.println(trainData.get(i));
			}
			
			for (ArrayList<Double> train : trainData) {
				double dis = train.get(train.size()-1);
				train.remove(dis);
			}
			
			System.out.println("=========训练数据为=============");
			for (int i = 0; i < trainData.size(); i++) {
				System.out.println(trainData.get(i));
			}
			
			String type = "";
			List<Double> times = new ArrayList<Double>();
			for (int i = 0; i < k; i++) {
				List<Double> train = trainData.get(i);
				times.add(train.get(train.size() - 1));
			}
			Collections.sort(times);
			int maxTimes = 0;
			double maxType = 0.0;
			int curTimes = 0;
			double curType = 0.0;
			for (int i = 0; i < times.size(); i++) {
				if((i > 0) && (times.get(i) != times.get(i-1))){
					if(curTimes > maxTimes){
						maxTimes = curTimes;
						maxType =curType;
					}
					curTimes = 0;
				}else{
					curTimes++;
					curType = times.get(i);
				}
				
			}
			System.out.println("测试集" + test + "属于的类型为" + maxType);
		}
	}
	
	public static void main(String[] args) throws IOException {
		String trainFile = "../MachineLearning/二向箔.txt";
		System.out.println("==================训练数据=================");
		List<ArrayList<Double>> trainData = readData(trainFile);
		System.out.println("------------------测试数据-----------------");
		String testFile = "../MachineLearning/二向箔测试.txt";
		List<ArrayList<Double>> testData = readData(testFile);
		System.out.println("输入k:");
		Scanner scan = new Scanner(System.in);
		int k = scan.nextInt();
		getAllDistance(trainData, testData, k);
	}
}
