package BtapkethopIO_Thread;
// Ghi dữ liệu từ bàn phím vào file bằng Thread

//Yêu cầu: Tạo Thread ghi dữ liệu người dùng nhập từ bàn phím vào file.

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class bai7 {
	private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
	private static final String FILE_NAME = "src/BtapkethopIO_Thread/bai7.txt";

	static class FileWriterThread extends Thread {
		@Override
		public void run() {
			try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
				while (true) {
					String input = queue.take(); // Lấy dữ liệu từ hàng đợi
					if (input.equalsIgnoreCase("EXIT"))
						break; // Thoát nếu nhập "EXIT"

					writer.println(input);
					writer.flush(); // Đảm bảo dữ liệu được ghi ngay lập tức
				}
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		FileWriterThread fileWriterThread = new FileWriterThread();
		fileWriterThread.start();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Nhập dữ liệu (gõ 'EXIT' để thoát):");

		while (true) {
			String userInput = scanner.nextLine();
			try {
				queue.put(userInput); // Đưa dữ liệu vào hàng đợi
				if (userInput.equalsIgnoreCase("EXIT"))
					break; // Nếu nhập "EXIT" thì dừng
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			fileWriterThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Dữ liệu đã được lưu vào " + FILE_NAME);
	}
}