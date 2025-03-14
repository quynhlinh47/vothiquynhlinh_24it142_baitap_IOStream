package Btap_IOStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class bai6 {
	private static final String OUTPUT_FILE = "src/Btap_IOStream/output_6.txt";
	private static final Object LOCK = new Object();
	
	public static void main(String[] args) {
		String[] inputFiles = {"src/Btap_IOStream/input6_1.txt", "src/Btap_IOStream/input6_2.txt", "src/Btap_IOStream/input6_3.txt"};
		
		List<Thread> threads = new ArrayList<>();
		
		for(String file : inputFiles) {
			Thread thread = new Thread(new FileReaderTask(file));
			
			threads.add(thread);
			thread.start();
		}
		  // Chờ tất cả các luồng đọc hoàn thành
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Đã hợp nhất dữ liệu vào file " + OUTPUT_FILE);
    }
	 // Luồng đọc file và ghi vào file output
    static class FileReaderTask implements Runnable {
        private final String fileName;

        public FileReaderTask(String fileName) {
            this.fileName = fileName;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }

                writeToFile(content.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // Ghi dữ liệu vào file output theo thứ tự, sử dụng synchronized để tránh xung đột
        private void writeToFile(String content) {
            synchronized (LOCK) {
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(OUTPUT_FILE, true))) {
                    bw.write("=== Nội dung từ " + fileName + " ===\n");
                    bw.write(content);
                    bw.write("\n");
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
