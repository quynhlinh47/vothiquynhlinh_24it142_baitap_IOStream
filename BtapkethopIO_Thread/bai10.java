package BtapkethopIO_Thread;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

//Bài tập 10: Tải dữ liệu từ URL bằng Thread
//Yêu cầu: Tạo Thread tải nội dung từ một URL và lưu vào file.
public class bai10 {
	public static void main(String[] args) {
		String fileURL = "https://example.com/sample.txt";
		String outputFile = "src/BtapkethopIO_Thread/bai10.txt";

		DownloadThread downloadThread = new DownloadThread(fileURL, outputFile);
		downloadThread.start();

		try {
			downloadThread.join();
		} catch (InterruptedException e) {
			System.out.println("Thread interrupted: " + e.getMessage());
		}

		System.out.println("Processing completed.");
	}
}

class DownloadThread extends Thread {
	private String url;
	private String outputFile;

	public DownloadThread(String url, String outputFile) {
		this.url = url;
		this.outputFile = outputFile;
	}

	@Override
	public void run() {
		try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
				FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {

			byte[] dataBuffer = new byte[1024];
			int bytesRead;
			while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
				fileOutputStream.write(dataBuffer, 0, bytesRead);
			}

			System.out.println("Download completed: " + outputFile);
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
