package clustering;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Kmeans {
	
	//���������ʼ����
	public static List<ArrayList<Double>> randChoose(List<ArrayList<Double>> data){
		
		System.out.println("������Ҫ����Ĵظ�����");
		Scanner scan = new Scanner(System.in); 
		int number = scan.nextInt();
		System.out.println("Ҫ����Ĵصĸ���Ϊ" + number);
		
		Random rand = new Random();
		
		List<ArrayList<Double>> init = new ArrayList<ArrayList<Double>>();
		
		System.out.println("һ����" + data.size() + "����");
		
		for(int i = 0; i < number; i++){
			int choice = rand.nextInt(data.size());
			System.out.println("ѡȡ�������Ϊ" + choice);
			init.add(data.get(choice));
		}
		
		System.out.println("ѡȡ�ľ���Ĵ�Ϊ��");
		for (ArrayList<Double> arrayList : init) {
			System.out.println(arrayList);
		}
		
		return init;
		
	}

	//��ԭʼ�ļ������
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
	
	
	//���ִ�
	public static Map<Integer, List<ArrayList<Double>>> divideCluster(List<ArrayList<Double>> data, 
			List<ArrayList<Double>> init){
		Map<Integer, List<ArrayList<Double>>> initResult = new HashMap<Integer, List<ArrayList<Double>>>();
		int keySetNumber = init.size();
		System.out.println("��Map��" + keySetNumber + "��keyֵ");
		
		//ÿһ��֮�µ����ݼ���
		
		
		//������������
		for (ArrayList<Double> singleData : data) {
			//��������Map�о���صı��
			int index = 0;
			double minDistance = 1000000.0;
			
			for(int i = 0; i < init.size(); i++){
				//������Ե���ÿһ�������
				ArrayList<Double> initTemp = init.get(i);
				double distanceTemp = getDistance(singleData, initTemp);
				if(distanceTemp < minDistance){
					minDistance = distanceTemp;
					index = i;
				}
			}
//			System.out.println("��СֵΪ" + minDistance + "������һ��" + index);
			
			
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
		
		System.out.println("һ�ֳ�ʼ�����Ԫ��Map");
		for (Map.Entry<Integer, List<ArrayList<Double>>> entry : initResult.entrySet()) {
			System.out.println(entry);
		}
		
		return initResult;
	}
	
	
	
	//���³�ʼ����
	public static List<ArrayList<Double>> updateCluster(Map<Integer, List<ArrayList<Double>>>  initResult,
			List<ArrayList<Double>> initMean){
		
		List<ArrayList<Double>> updateMean = new ArrayList<ArrayList<Double>>();
				
		for (int index = 0; index < initResult.size(); index++) {
		//�õ�ÿһ�ص�Ԫ��
			List<ArrayList<Double>> clusterValue = initResult.get(index);
			//ת��һ������
			int row = clusterValue.size(); //����
			int col = clusterValue.get(0).size(); //����
			System.out.println("�������" + row + "�������" + col);
			double[][] value = new double[row][col];

			//ArrayListתΪ����
			System.out.println("-----ArrayListתΪ����֮��------");
			for(int i = 0; i < row; i++){
				for(int j = 0; j < col; j++){
					value[i][j] = clusterValue.get(i).get(j);
//					System.out.print(value[i][j] + ",");
				}
//				System.out.println();	
			}
			
			//���¾�ֵ����
			double[] newMean = new double[col];
	 
			for(int k = 0; k < col; k++){
				double sum = 0.0;
				for(int l = 0; l < row; l++){
					sum += value[l][k];
				}
				double temp = sum / row;
				newMean[k] = temp;
			}
			//����µľ�ֵ������˳��תΪArrayList���۲��۰����ǣ���
			System.out.println("��" + index + "�µľ�ֵ����Ϊ");
			ArrayList<Double> newMeanList = new ArrayList<Double>();
			for(int m = 0; m < newMean.length; m++){
				System.out.print(newMean[m] + " ");
				newMeanList.add(newMean[m]);
			}
			updateMean.add(newMeanList);
		}
		
		System.out.println();
		System.out.println("���º�ĳ�ʼֵ����Ϊ:");
		for (ArrayList<Double> array : updateMean) {
			System.out.println(array);
		}
		
		return updateMean;
	}
	
	
	//�ж�����ArrayList�Ƿ����
	public static boolean isEqual(List<Double> u1, List<Double> u2){
		
		if(u1.size() != u2.size()){
			System.out.println("���ݴ�С����!");
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
	 
	//�ж�����List<ArrayList<Double>>�Ƿ����
	public static boolean isEqualList(List<ArrayList<Double>> u1, List<ArrayList<Double>> u2){
		if(u1.size() != u2.size()){
			System.out.println("���ݴ�С����!");
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
		//������ԭʼ������
		List<ArrayList<Double>> data = FileUtil.fileInput();
		//�����ѡȡ��ֵ����
		List<ArrayList<Double>> initMean = randChoose(data);
		//���ݾ�ֵ�������ִ�
		Map<Integer, List<ArrayList<Double>>> cluster = divideCluster(data, initMean);
		
		
		//��һ�θ��´�
		List<ArrayList<Double>> firstCluster = updateCluster(cluster, initMean);
		for (ArrayList<Double> arrayList : firstCluster) {
			System.out.println("��һ�θ��´صĽ��:" + arrayList);
		}
		
		//���Ż���Map,��������
		Map<Integer, List<ArrayList<Double>>> nextDivide = divideCluster(data, firstCluster);
		//�ٴθ��´�
		List<ArrayList<Double>> nextCluster = updateCluster(nextDivide, firstCluster);
		while(!isEqualList(firstCluster, nextCluster)){
			firstCluster = new ArrayList<ArrayList<Double>>(nextCluster);
			nextDivide = divideCluster(data, firstCluster);
			nextCluster = updateCluster(nextDivide, firstCluster);
			System.out.println("===========================������========================");
			for (ArrayList<Double> arrayList : nextCluster) {
				System.out.println(arrayList);
			}
		}
		System.out.println("==========================���ս��Ϊ:========================");
		for (ArrayList<Double> arrayList : nextCluster) {
			System.out.println(arrayList);
		}
		for(Map.Entry<Integer, List<ArrayList<Double>>> divider :nextDivide.entrySet()){
			System.out.println(divider);
		}
		
	}

}
