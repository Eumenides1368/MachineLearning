package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 希望能作为一个读取ArrayList和写出ArrayList的util
 * @author Administrator
 *
 */

public class FileIO {

	public static ArrayList<ArrayList<String>> fileToArrayList() throws IOException{
		//读取相对路径
//		BufferedReader reader = new BufferedReader(new FileReader("../MachineLearning/data.txt"));
		BufferedReader reader = new BufferedReader(new FileReader("..\\MachineLearning\\data.txt"));
		ArrayList<String> arrayTemp = new ArrayList<String>();
		
		System.out.println("------------------");
		String line = "";
		try {
			while((line = reader.readLine()) !=null)
				arrayTemp.add(line);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			reader.close();
		}
		
		ArrayList<String> array = new ArrayList<String>();
		ArrayList<ArrayList<String>> arrayAll = new ArrayList<ArrayList<String>>();
		for (String string : arrayTemp) {
//			System.out.println(string);
			//仿佛是一个正则表达式
			String[] str = string.split("\\s+");
			array = new ArrayList<String>(Arrays.asList(str));
			arrayAll.add(array);
		}
		
		for (ArrayList<String> arrayList : arrayAll) {
			System.out.println(arrayList);
		}
		
		return arrayAll;
	}
	
	public static void ArrayListToFile(ArrayList<ArrayList<String>> array) throws IOException{
		File file = new File("..\\MachineLearning\\data1.txt");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file)); 
		
		for (ArrayList<String> arrayList : array) {
			for (String string : arrayList) {
				bw.write(string + " ");
				//不要忘了这句话
				bw.flush();
//				System.out.println(string);
			}
			bw.newLine();
		}
		
	}
	
	public static void ArrayToTxt() throws IOException {
	        File f=new File("..\\MachineLearning\\data1.txt");
	        ArrayList<String> al=new ArrayList<String>();
	        al.add("first");
	        al.add("second");
	        al.add("third");
	        BufferedWriter bw=new BufferedWriter(new FileWriter(f));
	        for(int i=0;i<al.size();i++){
	            bw.write(al.get(i));
	            bw.newLine();
	        }
	        bw.close();
	    }
	
	public static void main(String[] args) throws IOException{
		ArrayList<ArrayList<String>> array = fileToArrayList();
		ArrayListToFile(array);
		
//		ArrayToTxt();
	}
}
