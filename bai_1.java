package Btap_IOStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class bai_1 {
	public static void main(String[] args) {
	
	try(FileInputStream fi = new FileInputStream("src/input.txt");
		FileOutputStream fo = new FileOutputStream("src/output.txt")){
	
		int data;
		while((data = fi.read()) != -1) {
			fo.write(data);
		}
		System.out.println("File được ghi thành công!!!");
	} catch(IOException e) {
		e.printStackTrace();
	}
  }
}
