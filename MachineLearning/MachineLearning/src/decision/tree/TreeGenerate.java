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
	
	//选择最优的属性集,按照每一个属性分类
	public static Object[] chooseBestAttr(Map<Object, List<Sample>> samples, String[] attrName){
		
		double min = 1.0;
		int minAttr = 0;
		Map<Object, Map<Object, List<Sample>>> minSplits = null; // 最优分支方案 
		
		double allCount = 0.0;
		for (Map.Entry<Object, List<Sample>> entrySet : samples.entrySet()) {
			List<Sample> arr = entrySet.getValue();
			allCount += arr.size();
			System.out.println("总长度" + allCount);
		}
		
		for(int i = 0; i < attrName.length; i++){
			
			//属性-><分类--》样本分类>
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
				//输出新分类
				for(Entry<Object, Map<Object, List<Sample>>> newEntry : newSamp.entrySet()){
					System.out.println("输出新的分类" + "key:" + newEntry.getKey() + "value" + newEntry.getValue());
				}
			}
			System.out.println("--------------------------");
			
			int types = newSamp.keySet().size();
			System.out.println("有几类" + types);

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
				System.out.println("最终的熵为" + finalEnt);
				
				if(finalEnt < min){
					min = finalEnt;
					minAttr = i;
					minSplits = newSamp;
				}
				
			}
			
			
			
		}
		System.out.println("比较得知，最小的熵为" + min + "\t此时的选择为" + attrName[minAttr] + "\t划分为" + minSplits);
		System.out.println("====================================");
		
		return new Object[] {min, minAttr, minSplits};
	}
	
	//生成决策树
	public static Object generateTree(Map<Object, List<Sample>> samples, String[] attrName){
		
		//如果只有一个样本
		if(samples.size() == 1){
			return samples.keySet().iterator().next();
		}
		
		//属性为空,选择最多样本的分类
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
		
		//选择最优的测试属性
		Object[] bestAttr = chooseBestAttr(samples, attrName);
		
		Tree tree = new Tree(attrName[(Integer)bestAttr[1]]);
		
		String[] sub = new String[attrName.length - 1];
		
		//排除已经选过的属性
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
	
	//递归
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
