package decision.tree;

//�õ��صĹ���
public class Entropy {
	
	//�����
	public static double getProbability(double number, double total){
		return number / total;
	}
	
	//��log2
	public static double newLog(double x){
		double newLog = Math.log(x) / Math.log(2);
		return newLog;
	}
	
	//����Ϣ��
	public static double getInformationEntropy(double[] probability){
		if(probability == null){
			System.out.println("�˴�ȱ�ٸ���");
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
