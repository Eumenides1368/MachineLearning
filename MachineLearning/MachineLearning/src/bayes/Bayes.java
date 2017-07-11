package bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Bayes {
	
	//数据分类Map<类别，<ArrayList<ArrayList<属于该类别>>
	public static Map<String, ArrayList<ArrayList<String>>> classifyData(ArrayList<ArrayList<String>> data){
		Map<String, ArrayList<ArrayList<String>>> classification = new HashMap<String, ArrayList<ArrayList<String>>>();
		
		//读取data数据
		for (ArrayList<String> arrayList : data) {
			int n = arrayList.size() - 1;
			String type = arrayList.get(n);
			if(!classification.keySet().contains(type)){
				ArrayList<ArrayList<String>> curArr = new ArrayList<ArrayList<String>>();
				curArr.add(arrayList);
				classification.put(type, curArr);
			}else{
				ArrayList<ArrayList<String>> curArr = classification.get(type);
				curArr.add(arrayList);
				classification.put(type, curArr);
			}
				
		}
		
		for (Map.Entry<String, ArrayList<ArrayList<String>>> map : classification.entrySet()) {
			System.out.println("结果是" + map.getKey() + ":" + map.getValue());
		}
		return classification;
	}
	
	/**
	 * 预测
	 * @param dataSet
	 */
	
	/*
	public static void predict(ArrayList<ArrayList<String>> dataSet, ArrayList<String> testData){
		Map<String, ArrayList<ArrayList<String>>> doc = classifyData(dataSet);
		
		int allTimes = 0;
		for (ArrayList<String> sData : dataSet) {
			allTimes++;
		}
		System.out.println("总共有"  + allTimes + "个训练集");
		
		double finalProb = 1.0;
		double min = 1.0;
		String minChoice = "";
		
		for (Map.Entry<String, ArrayList<ArrayList<String>>> elem : doc.entrySet()) {
			String type = elem.getKey();
			ArrayList<ArrayList<String>> d = doc.get(type);
			double baseProb = (double)d.size() / (double)allTimes;
			System.out.println("出现的基本概率为(西瓜好坏)" + type + " " + baseProb);
			double tempProb = 1.0;
			
			for (String testElem : testData) {
				int times = 0;
				for (ArrayList<String> arrayList : d) {
					arrayList.contains(testElem);
					times++;
				}
				double uniqueProb = (double)times / (double)d.size();
				System.out.println(testElem + "\t出现了" + times + "次");
				tempProb *= uniqueProb;
				finalProb = tempProb * baseProb;
				System.out.println(testElem + "出现的概率为" + finalProb);
				
				
				if(finalProb < min){
					min = finalProb;
					minChoice = testElem;
				}
						
			}
			
			System.out.println("选择" + minChoice + "概率为" + min);
		}
		
	}
	*/
		
	public static void predict(ArrayList<ArrayList<String>> dataSet, ArrayList<String> testData){
		Map<String, ArrayList<ArrayList<String>>> doc = classifyData(dataSet);
		
		Set<String> label = doc.keySet();
		System.out.println("一共有" + label.size() + "类");
		
		double allLength = dataSet.size();
		System.out.println("总共有元素个数=====" + allLength);
		
		double finalProb = 1.0;
		double allCount = 0.0;
		double maxProb = 0.0;
		String maxChoice = "";
		
		for(String l : label){
			ArrayList<ArrayList<String>> pos = doc.get(l);
			
			//每一个标签下的数据
			System.out.println("标签" + l + "下");
			 
			double attrCount = 0.0;
			String str = "";
			double posProb = 1.0;
			
			for (int i = 0; i < testData.size(); i++) {
				
				//测试集
				attrCount = 0;
				for (ArrayList<String> arrayList : pos) {
					str = testData.get(i);
					if(arrayList.contains(str))
						attrCount++;
				}
				double p = attrCount / pos.size();
				System.out.println(str + " 出现次数" + attrCount + " 此类元素个数" + pos.size() + " 出现概率" + p);
				
				posProb *= p;
				finalProb = posProb * (pos.size() / allLength);
				
			}
			System.out.println("标签" + l + "的概率是" + finalProb);
			if(finalProb > maxProb){
				maxProb = finalProb;
				maxChoice = l;
			}
			System.out.println("=======================");
			if(maxChoice == "1")
				System.out.println("最大的概率是" + maxProb + "选");
			else
				System.out.println("最大的概率是" + maxProb + "不选");
			
		}
		
	}
		
	public static double getLog(double p){
		double prob = Math.log(p) / Math.log(2);
		return prob;
	}
	
	public static void main(String[] args) {
		String[][] rawData = new String[][] {  
                { "<30  ", "High  ", "No ", "Fair     ", "0" },  
                { "<30  ", "High  ", "No ", "Excellent", "0" },  
                { "30-40", "High  ", "No ", "Fair     ", "1" },  
                { ">40  ", "Medium", "No ", "Fair     ", "1" },  
                { ">40  ", "Low   ", "Yes", "Fair     ", "1" },  
                { ">40  ", "Low   ", "Yes", "Excellent", "0" },  
                { "30-40", "Low   ", "Yes", "Excellent", "1" },  
                { "<30  ", "Medium", "No ", "Fair     ", "0" },  
                { "<30  ", "Low   ", "Yes", "Fair     ", "1" },  
                { ">40  ", "Medium", "Yes", "Fair     ", "1" },  
                { "<30  ", "Medium", "Yes", "Excellent", "1" },  
                { "30-40", "Medium", "No ", "Excellent", "1" },  
                { "30-40", "High  ", "Yes", "Fair     ", "1" },  
                { "30-40", "Medium", "No ", "Excellent", "0" } };  
		
		ArrayList<ArrayList<String>> allData = new ArrayList<ArrayList<String>>();
		for(int i = 0; i < rawData.length; i++){
			ArrayList<String> str = new ArrayList<String>();
			for(int j = 0; j < rawData[i].length; j++){
				str.add(rawData[i][j]);
			}
			allData.add(str);
		}
		
//		classifyData(allData);
		
		String[] test = { "30-40", "Medium", "Yes", "Excellent"};
		//数组转为ArrayList
		ArrayList<String> fate = new ArrayList<String>(Arrays.asList(test));
		
		predict(allData,fate);
	}
	
}
