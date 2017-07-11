package bayes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class Bayes {
	
	//���ݷ���Map<���<ArrayList<ArrayList<���ڸ����>>
	public static Map<String, ArrayList<ArrayList<String>>> classifyData(ArrayList<ArrayList<String>> data){
		Map<String, ArrayList<ArrayList<String>>> classification = new HashMap<String, ArrayList<ArrayList<String>>>();
		
		//��ȡdata����
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
			System.out.println("�����" + map.getKey() + ":" + map.getValue());
		}
		return classification;
	}
	
	/**
	 * Ԥ��
	 * @param dataSet
	 */
	
	/*
	public static void predict(ArrayList<ArrayList<String>> dataSet, ArrayList<String> testData){
		Map<String, ArrayList<ArrayList<String>>> doc = classifyData(dataSet);
		
		int allTimes = 0;
		for (ArrayList<String> sData : dataSet) {
			allTimes++;
		}
		System.out.println("�ܹ���"  + allTimes + "��ѵ����");
		
		double finalProb = 1.0;
		double min = 1.0;
		String minChoice = "";
		
		for (Map.Entry<String, ArrayList<ArrayList<String>>> elem : doc.entrySet()) {
			String type = elem.getKey();
			ArrayList<ArrayList<String>> d = doc.get(type);
			double baseProb = (double)d.size() / (double)allTimes;
			System.out.println("���ֵĻ�������Ϊ(���Ϻû�)" + type + " " + baseProb);
			double tempProb = 1.0;
			
			for (String testElem : testData) {
				int times = 0;
				for (ArrayList<String> arrayList : d) {
					arrayList.contains(testElem);
					times++;
				}
				double uniqueProb = (double)times / (double)d.size();
				System.out.println(testElem + "\t������" + times + "��");
				tempProb *= uniqueProb;
				finalProb = tempProb * baseProb;
				System.out.println(testElem + "���ֵĸ���Ϊ" + finalProb);
				
				
				if(finalProb < min){
					min = finalProb;
					minChoice = testElem;
				}
						
			}
			
			System.out.println("ѡ��" + minChoice + "����Ϊ" + min);
		}
		
	}
	*/
		
	public static void predict(ArrayList<ArrayList<String>> dataSet, ArrayList<String> testData){
		Map<String, ArrayList<ArrayList<String>>> doc = classifyData(dataSet);
		
		Set<String> label = doc.keySet();
		System.out.println("һ����" + label.size() + "��");
		
		double allLength = dataSet.size();
		System.out.println("�ܹ���Ԫ�ظ���=====" + allLength);
		
		double finalProb = 1.0;
		double allCount = 0.0;
		double maxProb = 0.0;
		String maxChoice = "";
		
		for(String l : label){
			ArrayList<ArrayList<String>> pos = doc.get(l);
			
			//ÿһ����ǩ�µ�����
			System.out.println("��ǩ" + l + "��");
			 
			double attrCount = 0.0;
			String str = "";
			double posProb = 1.0;
			
			for (int i = 0; i < testData.size(); i++) {
				
				//���Լ�
				attrCount = 0;
				for (ArrayList<String> arrayList : pos) {
					str = testData.get(i);
					if(arrayList.contains(str))
						attrCount++;
				}
				double p = attrCount / pos.size();
				System.out.println(str + " ���ִ���" + attrCount + " ����Ԫ�ظ���" + pos.size() + " ���ָ���" + p);
				
				posProb *= p;
				finalProb = posProb * (pos.size() / allLength);
				
			}
			System.out.println("��ǩ" + l + "�ĸ�����" + finalProb);
			if(finalProb > maxProb){
				maxProb = finalProb;
				maxChoice = l;
			}
			System.out.println("=======================");
			if(maxChoice == "1")
				System.out.println("���ĸ�����" + maxProb + "ѡ");
			else
				System.out.println("���ĸ�����" + maxProb + "��ѡ");
			
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
		//����תΪArrayList
		ArrayList<String> fate = new ArrayList<String>(Arrays.asList(test));
		
		predict(allData,fate);
	}
	
}
