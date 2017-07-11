package clustering;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	//¶ÁÈëÎÄ¼þ
	public static List<ArrayList<Double>> fileInput() throws IOException{
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream("../MachineLearning/cluster.txt")));
		
		String str = "";
		List<ArrayList<Double>> data = new ArrayList<ArrayList<Double>>();
		try {
			while((str = reader.readLine()) != null){
				String[] fields = str.split(",");
				ArrayList<Double> tmpList = new ArrayList<Double>();
				for(int i = 0; i < fields.length; i++){
					tmpList.add(Double.parseDouble(fields[i]));
				}
				data.add(tmpList);
			}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			reader.close();
		}
		/*
		for (ArrayList<Double> temp : data) {
			System.out.println(temp);
		}
		*/
		return data;
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		fileInput();
	}

}
