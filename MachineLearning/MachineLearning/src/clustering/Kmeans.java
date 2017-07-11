package clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Kmeans {
	
	//生成随机初始向量
	public static List<ArrayList<Double>> randChoose(List<ArrayList<Double>> data){
		
		System.out.println("请输入要聚类的簇个个数");
		Scanner scan = new Scanner(System.in); 
		int number = scan.nextInt();
		System.out.println("要聚类的簇的个数为" + number);
		
		Random rand = new Random();
		
		List<ArrayList<Double>> init = new ArrayList<ArrayList<Double>>();
		
		System.out.println("一共有" + data.size() + "个类");
		
		for(int i = 0; i < number; i++){
			int choice = rand.nextInt(data.size());
			System.out.println("选取的随机数为" + choice);
			init.add(data.get(choice));
		}
		
		System.out.println("选取的聚类的簇为：");
		for (ArrayList<Double> arrayList : init) {
			System.out.println(arrayList);
		}
		
		return init;
		
	}

	//最原始的计算距离
	public static double getDistance(ArrayList<Double> arr1, ArrayList<Double> arr2){
		
		double distance = 0.0;
		if(arr1.size() != arr2.size())
			return -1;
		else{
			double sub = 0.0;
			double dis = 0.0;
			for(int i = 1; i < arr1.size(); i++){
				sub = arr1.get(i) - arr2.get(i);
				dis += sub *sub;
			}
			distance = Math.sqrt(dis);
		}
			
		return distance;
	}
	
	
	//划分簇
	public static Map<Integer, List<ArrayList<Double>>> divideCluster(List<ArrayList<Double>> data, 
			List<ArrayList<Double>> init){
		Map<Integer, List<ArrayList<Double>>> initResult = new HashMap<Integer, List<ArrayList<Double>>>();
		int keySetNumber = init.size();
		System.out.println("本Map有" + keySetNumber + "个key值");
		
		//每一簇之下的数据集合
		
		
		//遍历所有数据
		for (ArrayList<Double> singleData : data) {
			//此数据在Map中聚类簇的编号
			int index = 0;
			double minDistance = 1000000.0;
			
			for(int i = 0; i < init.size(); i++){
				//以下针对的是每一个聚类簇
				ArrayList<Double> initTemp = init.get(i);
				double distanceTemp = getDistance(singleData, initTemp);
				if(distanceTemp < minDistance){
					minDistance = distanceTemp;
					index = i;
				}
			}
//			System.out.println("最小值为" + minDistance + "属于哪一类" + index);
			
			
			if(initResult.get(index) == null){
				List<ArrayList<Double>> clusterData = new ArrayList<ArrayList<Double>>();
				clusterData.add(singleData);
				initResult.put(index, clusterData);
			}else{
				List<ArrayList<Double>> clusterData = initResult.get(index);
				clusterData.add(singleData);
				initResult.put(index, clusterData);
			}
			
		}
		
		System.out.println("一轮初始化后的元素Map");
		for (Map.Entry<Integer, List<ArrayList<Double>>> entry : initResult.entrySet()) {
			System.out.println(entry);
		}
		
		return initResult;
	}
	
	
	
	//更新初始向量
	public static List<ArrayList<Double>> updateCluster(Map<Integer, List<ArrayList<Double>>>  initResult,
			List<ArrayList<Double>> initMean){
		
		List<ArrayList<Double>> updateMean = new ArrayList<ArrayList<Double>>();
				
		for (int index = 0; index < initResult.size(); index++) {
		//得到每一簇的元素
			List<ArrayList<Double>> clusterValue = initResult.get(index);
			//转成一个数组
			int row = clusterValue.size(); //行数
			int col = clusterValue.get(0).size(); //列数
			System.out.println("数组的行" + row + "数组的列" + col);
			double[][] value = new double[row][col];

			//ArrayList转为数组
			System.out.println("-----ArrayList转为数组之后------");
			for(int i = 0; i < row; i++){
				for(int j = 0; j < col; j++){
					value[i][j] = clusterValue.get(i).get(j);
//					System.out.print(value[i][j] + ",");
				}
//				System.out.println();	
			}
			
			//更新均值向量
			double[] newMean = new double[col];
	 
			for(int k = 0; k < col; k++){
				double sum = 0.0;
				for(int l = 0; l < row; l++){
					sum += value[l][k];
				}
				double temp = sum / row;
				newMean[k] = temp;
			}
			//输出新的均值向量，顺道转为ArrayList（累不累啊真是！）
			System.out.println("簇" + index + "新的均值向量为");
			ArrayList<Double> newMeanList = new ArrayList<Double>();
			for(int m = 0; m < newMean.length; m++){
				System.out.print(newMean[m] + " ");
				newMeanList.add(newMean[m]);
			}
			updateMean.add(newMeanList);
		}
		
		System.out.println();
		System.out.println("更新后的初始值向量为:");
		for (ArrayList<Double> array : updateMean) {
			System.out.println(array);
		}
		
		return updateMean;
	}
	
	
	//判断两个ArrayList是否相等
	public static boolean isEqual(List<Double> u1, List<Double> u2){
		
		if(u1.size() != u2.size()){
			System.out.println("数据大小有误!");
			return false;
		}else{
			for(int i = 0; i < u1.size(); i++){
				double sub = Math.abs(u1.get(i) - u2.get(i));
				if(sub > 0.01)
					return false;
			}
		}
		return true;
	}
	 
	//判断两个List<ArrayList<Double>>是否相等
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
//				System.out.println(flag[i]);
			}
			System.out.println("======================");
			for(int i = 0; i < flag.length; i++){
//				System.out.println(flag[i] + "");
				if(flag[i] == false)
					break;
				return true;
			}
			return false;
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//输入最原始的数据
		List<ArrayList<Double>> data = FileUtil.fileInput();
		//随机的选取均值向量
		List<ArrayList<Double>> initMean = randChoose(data);
		//根据均值向量划分簇
		Map<Integer, List<ArrayList<Double>>> cluster = divideCluster(data, initMean);
		
		
		//第一次更新簇
		List<ArrayList<Double>> firstCluster = updateCluster(cluster, initMean);
		for (ArrayList<Double> arrayList : firstCluster) {
			System.out.println("第一次更新簇的结果:" + arrayList);
		}
		
		//接着划分Map,所有数据
		Map<Integer, List<ArrayList<Double>>> nextDivide = divideCluster(data, firstCluster);
		//再次更新簇
		List<ArrayList<Double>> nextCluster = updateCluster(nextDivide, firstCluster);
		while(!isEqualList(firstCluster, nextCluster)){
			firstCluster = new ArrayList<ArrayList<Double>>(nextCluster);
			nextDivide = divideCluster(data, firstCluster);
			nextCluster = updateCluster(nextDivide, firstCluster);
			System.out.println("===========================更新中========================");
			for (ArrayList<Double> arrayList : nextCluster) {
				System.out.println(arrayList);
			}
		}
		System.out.println("==========================最终结果为:========================");
		for (ArrayList<Double> arrayList : nextCluster) {
			System.out.println(arrayList);
		}
		for(Map.Entry<Integer, List<ArrayList<Double>>> divider :nextDivide.entrySet()){
			System.out.println(divider);
		}
		
	}

}
