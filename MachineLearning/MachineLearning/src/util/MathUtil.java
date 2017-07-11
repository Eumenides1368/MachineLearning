package util;

public class MathUtil {
	
	//求平均数
	public static double getAverage(double[] data){
		double average = 0.0;
		double sum = 0.0;
		
		if(data == null)
			System.out.println("数据有误!");
		for(int i = 0; i < data.length; i++){
			sum += data[i];
		}
		average = sum / data.length;
		System.out.println("这组数据的平均数为" + average);
		
		return average;
	}
	
	//求方差
	public static void getVariance(double[] data, double avg){
		double sum = 0.0;
		double var = 0.0;
		
		if(data == null)
			System.out.println("数据真的有误！");
		for(int i = 0; i < data.length; i ++){
			double temp = data[i] - avg;
			sum += temp * temp;
		}
		var = sum / (data.length - 1);
		
		double standard = Math.sqrt(var);
		System.out.println("标准差是" + standard);
	}
	
	//求向量距离
	public static void getDistance(double[] c1, double[] c2){
		if(c1.length != c2.length)
			System.out.println("输入有误");
		else{
			double distance = 0.0;
			for(int i = 0; i < c1.length; i++){
				double temp = Math.pow((c1[i] - c2[i]), 2);
				distance += temp;
			}
			distance = Math.sqrt(distance);
			System.out.println("向量之间的距离是" + distance);
		}
			
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[] goodDensity = {0.697, 0.774, 0.634, 0.608, 0.556, 0.403, 0.481, 0.437};
		
		double avg = getAverage(goodDensity);
		getVariance(goodDensity, avg);
		
		double[] c1 = {0.697, 0.460}; 
		double[] c2 = {0.403, 0.237};
		getDistance(c1, c2);
	}

}
