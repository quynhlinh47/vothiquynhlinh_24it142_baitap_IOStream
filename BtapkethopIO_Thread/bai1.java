package BtapkethopIO_Thread;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//sử dụng Thread để đọc từng dòng từ một file văn bản và in ra màn hình.

public class bai1 {
	public static void main(String[] args) {
		String filepath = "src/BtapkethopIO_Thread/bai1.txt";
		FileReaderThread fileReader = new FileReaderThread(filepath);
		fileReader.start();
		
	}
}
class FileReaderThread extends Thread {
	private String filepath;
	
	public FileReaderThread(String filepath) {
		this.filepath = filepath;
	}
	@Override
	public void run() {
		try(BufferedReader br = new BufferedReader(new FileReader(filepath))){
			String line;
			while((line = br.readLine())!= null) {
				 System.out.println(Thread.currentThread().getName() + ": " + line);
	                Thread.sleep(500);
			}
			
		}catch(IOException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.err.println("Lỗi khi đọc file: " + e.getMessage());
		}
	}
}
