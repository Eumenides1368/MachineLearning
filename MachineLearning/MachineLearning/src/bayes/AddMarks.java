package bayes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import util.FileIO;

public class AddMarks {
	
	public static void readData() throws IOException{
		ArrayList<ArrayList<String>> array = FileIO.fileToArrayList();
//		return array;
		
		String[][] str;
		for(int i = 0; i < 14; i++){
			str = new String[14][5];
			for(int j = 0; j < 5; j++){
				str[i][j] = "\" + array.get(i).get(j) + \"";
//				System.out.println("\"array.get(i).get(j)\"");
				System.out.println(str[i][j]);
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		readData();
	}

}
