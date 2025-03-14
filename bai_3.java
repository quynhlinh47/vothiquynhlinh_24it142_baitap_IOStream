package Btap_IOStream;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class bai_3 {
	public static void main(String[] args) {
		try(BufferedReader br  = new BufferedReader(new FileReader("src/input.txt"))){
			int count = 0;
			while(br.readLine() != null) {
				count ++;
			}
			System.out.println("Số dòng trong file: "+ count);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
