package decision.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import decision.tree.Entropy;

public class TreeGenerate {
	
	public static Map<Object, List<Sample>> readSamples(String[] attrNames){
		
		Object[][] rawData = new Object[][] {  
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
                { ">40  ", "Medium", "No ", "Excellent", "0" } };  

		Map<Object, List<Sample>> ret = new HashMap<Object, List<Sample>>();  
        for (Object[] row : rawData) {  
            Sample sample = new Sample();  
            int i = 0;  
            for (int n = row.length - 1; i < n; i++)  
                sample.setAttributes(attrNames[i], row[i]);  
            //i=row.length-1
            sample.setCategory(row[i]);  
            //0/1
            List<Sample> samples = ret.get(row[i]);  
            if (samples == null) {  
                samples = new ArrayList<Sample>();  
                ret.put(row[i], samples);  
            }  
            samples.add(sample);  
        }  
        
        
        return ret;  
	}
	
	//ѡ�����ŵ����Լ�,����ÿһ�����Է���
	public static Object[] chooseBestAttr(Map<Object, List<Sample>> samples, String[] attrName){
		
		double min = 1.0;
		int minAttr = 0;
		Map<Object, Map<Object, List<Sample>>> minSplits = null; // ���ŷ�֧���� 
		
		double allCount = 0.0;
		for (Map.Entry<Object, List<Sample>> entrySet : samples.entrySet()) {
			List<Sample> arr = entrySet.getValue();
			allCount += arr.size();
			System.out.println("�ܳ���" + allCount);
		}
		
		for(int i = 0; i < attrName.length; i++){
			
			//����-><����--����������>
			Map<Object, Map<Object, List<Sample>>> newSamp = new HashMap<Object, Map<Object, List<Sample>>>();
			for (Map.Entry<Object, List<Sample>> entry : samples.entrySet()) {
				Object category = entry.getKey();
				Set<Object> newSet = new HashSet<Object>();
				List<Sample> newValue = new ArrayList<Sample>();
				
				List<Sample> values = entry.getValue();
				
				for (Sample sample : values) {
					Object value = sample.getAttributes(attrName[i]);
					System.out.println(value);
					Map<Object, List<Sample>> temp = newSamp.get(value);
					if(temp == null){
						temp = new HashMap<Object, List<Sample>>();
						newSamp.put(value, temp);
					}
					List<Sample> tempSample = temp.get(category);
					if(tempSample == null){
						tempSample = new ArrayList<Sample>();
						temp.put(category, tempSample);
					}
					tempSample.add(sample);
					
				}
				//����·���
				for(Entry<Object, Map<Object, List<Sample>>> newEntry : newSamp.entrySet()){
					System.out.println("����µķ���" + "key:" + newEntry.getKey() + "value" + newEntry.getValue());
				}
			}
			System.out.println("--------------------------");
			
			int types = newSamp.keySet().size();
			System.out.println("�м���" + types);

			double finalEnt = 0.0;
			
			for (Map<Object, List<Sample>> newValue  : newSamp.values()) {
				double newValueCount = 0.0;
				double entropy = 0.0;
				for (List<Sample> val : newValue.values()) 
					newValueCount += val.size();
				for(List<Sample> val: newValue.values()){
					double valCount = val.size();
					double p = Entropy.getProbability(valCount, newValueCount);
					entropy -= p * (Math.log(p) / Math.log(2));
				}
				finalEnt += (newValueCount / allCount) * entropy;
				System.out.println("���յ���Ϊ" + finalEnt);
				
				if(finalEnt < min){
					min = finalEnt;
					minAttr = i;
					minSplits = newSamp;
				}
				
			}
			
			
			
		}
		System.out.println("�Ƚϵ�֪����С����Ϊ" + min + "\t��ʱ��ѡ��Ϊ" + attrName[minAttr] + "\t����Ϊ" + minSplits);
		System.out.println("====================================");
		
		return new Object[] {min, minAttr, minSplits};
	}
	
	//���ɾ�����
	public static Object generateTree(Map<Object, List<Sample>> samples, String[] attrName){
		
		//���ֻ��һ������
		if(samples.size() == 1){
			return samples.keySet().iterator().next();
		}
		
		//����Ϊ��,ѡ����������ķ���
		if(attrName.length == 0){
			Object maxCategory = null;
			int max = 0;
			for (Map.Entry<Object, List<Sample>> entry : samples.entrySet()) {
				maxCategory = entry.getKey();
				if(entry.getValue().size() > max){
					max = entry.getValue().size();
					maxCategory = entry.getKey();
				}
			}
			return maxCategory;
		}
		
		//ѡ�����ŵĲ�������
		Object[] bestAttr = chooseBestAttr(samples, attrName);
		
		Tree tree = new Tree(attrName[(Integer)bestAttr[1]]);
		
		String[] sub = new String[attrName.length - 1];
		
		//�ų��Ѿ�ѡ��������
		 for (int i = 0, j = 0; i < attrName.length; i++)  
	            if (i != (Integer) bestAttr[1])  
	                sub[j++] = attrName[i]; 
		 
		 @SuppressWarnings("unchecked")
		Map<Object, Map<Object, List<Sample>>> splits =  
			        (Map<Object, Map<Object, List<Sample>>>) bestAttr[2];  
		 
		 for (Entry<Object, Map<Object, List<Sample>>> entry : splits.entrySet()) {  
	            Object attrValue = entry.getKey();  
	            Map<Object, List<Sample>> split = entry.getValue();  
	            Object child = generateTree(split, sub);  
	            tree.setChildren(attrValue, child);  
	        }  
		
		return tree;
	}
	
	//�ݹ�
	public static void outputDecisionTree(Object obj, int level, Object from) {  
        for (int i = 0; i < level; i++)  
            System.out.print("|-----");  
        if (from != null)  
            System.out.printf("(%s):", from);  
        if (obj instanceof Tree) {  
            Tree tree = (Tree) obj;  
            String attrName = tree.getAttr();  
            System.out.printf("[%s = ?]\n", attrName);  
            for (Object attrValue : tree.getAttributeValues()) {  
                Object child = tree.getChildren(attrValue);  
                outputDecisionTree(child, level + 1, attrName + " = "  
                        + attrValue);  
            }  
        } else {  
            System.out.printf("[CATEGORY = %s]\n", obj);  
        }  
    }

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] attrNames = {"AGE", "INCOME", "STUDENT",  
                "CREDIT_RATING"} ;
		Map<Object, List<Sample>> ret = readSamples(attrNames);
		Set<Object> key = ret.keySet();
		Iterator<Object> it = key.iterator();
		while(it.hasNext()){
			Object str = it.next();
			System.out.println("key:" + str.toString() + " value:" + ret.get(str));
		}
		System.out.println(ret.size());
		
		Object decisionTree = generateTree(ret, attrNames);
		
		outputDecisionTree(decisionTree, 3, null);
	}

}
