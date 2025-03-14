package Btap_IOStream;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class bai_4 {
	public static void main(String[] args) {
		String filename = "src/Btap_IOStream/bai4.txt";
		int [] nums = {10, 2, 9, 20, 50};

		try(DataOutputStream dos = new DataOutputStream(new FileOutputStream(filename))){
			for(int num : nums) {
				dos.writeInt(num);
			}
			System.out.println("Ghi danh sách vào file thành công!");
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		try(DataInputStream dis = new DataInputStream(new FileInputStream(filename))){
			System.out.println("Đọc danh sách số nguyên từ file:");
			while(dis.available() > 0) {
				int num = dis.readInt();
				System.out.print(num+" ");
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
}
