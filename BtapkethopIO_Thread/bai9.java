package BtapkethopIO_Thread;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Đếm ký tự trong file bằng Thread
//Yêu cầu: Tạo Thread đếm số ký tự trong file và ghi kết quả vào file khác.
public class bai9 {
	public static void main(String[] args) {
		String inputFile = "src/BtapkethopIO_Thread/bai9_input.txt";
		String outputFile = "src/BtapkethopIO_Thread/bai9_output.txt";

		CharCountThread countThread = new CharCountThread(inputFile, outputFile);
		countThread.start();

		try {
			countThread.join();
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted: " + e.getMessage());
		}

		System.out.println("Processing completed.");
	}
}

class CharCountThread extends Thread {
	private String inputFile;
	private String outputFile;

	public CharCountThread(String inputFile, String outputFile) {
		this.inputFile = inputFile;
		this.outputFile = outputFile;
	}

	@Override
	public void run() {
		try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
				BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

			int charCount = 0;
			int ch;
			while ((ch = reader.read()) != -1) {
				charCount++;
			}

			writer.write("Character count: " + charCount + "\n");
			System.out.println("Character count written to " + outputFile);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
