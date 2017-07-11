package decision.tree;

//得到熵的过程
public class Entropy {
	
	//算概率
	public static double getProbability(double number, double total){
		return number / total;
	}
	
	//算log2
	public static double newLog(double x){
		double newLog = Math.log(x) / Math.log(2);
		return newLog;
	}
	
	//算信息熵
	public static double getInformationEntropy(double[] probability){
		if(probability == null){
			System.out.println("此处缺少概率");
			return -1;
		}
		else{
			double sum = 0.0;
			for(int i = 0; i < probability.length; i++){
				sum += probability[i] * newLog(probability[i]);
			}
			return sum;
		}
	}
	
	public static void main(String[] args){
		double result = getProbability(3,7);
		System.out.println(result);
	}
}
