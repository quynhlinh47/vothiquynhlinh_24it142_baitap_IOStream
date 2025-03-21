package BtapkethopIO_Thread;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

//Tạo hai Thread, mỗi Thread ghi một chuỗi khác nhau vào cùng một file.

public class bai2 {
	public static void main(String[] args) {
		String filepath = "src/BtapkethopIO_Thread/bai2.txt";
		Thread t1 = new FileThread(filepath, "Dữ liệu thread 1.");
		Thread t2 = new FileThread(filepath, "Dữ liệu thread 2.");
		t1.start();
		t2.start();
	}
}
class FileThread extends Thread {
	private String filepath;
	private String content;
	private static final ReentrantLock lock = new ReentrantLock(); // Khóa đồng bộ

	public FileThread(String filepath, String content) {
		this.filepath = filepath;
		this.content = content;
	}
	@Override
	public void run() {
		lock.lock();
		try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){ // Mở chế dộ append
			bw.write(content);
			bw.newLine();
			System.out.println(Thread.currentThread().getName() + " đã ghi xong.");
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();// giải phóng khoá
		}
	}
}
