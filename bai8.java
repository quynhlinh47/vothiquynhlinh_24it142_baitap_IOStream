package Btap_IOStream;

import java.io.*;
import java.util.concurrent.*;

public class bai8 {
    private static final String[] FILES = {"src/Btap_IOStream/input.txt", "src/Btap_IOStream/large_file.txt", "src/Btap_IOStream/input6_1.txt"};
    private static final String KEYWORD = "chào"; // Từ khóa cần tìm
    private static final int THREAD_COUNT = 3; // Số luồng tối đa

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        ConcurrentHashMap<String, Integer> resultMap = new ConcurrentHashMap<>();

        for (String file : FILES) {
            executor.execute(() -> {
                int count = searchKeywordInFile(file, KEYWORD);
                resultMap.put(file, count);
            });
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Chờ tất cả các luồng hoàn thành
        }

        // Hiển thị kết quả
        System.out.println("Kết quả tìm kiếm:");
        resultMap.forEach((file, count) -> System.out.println(file + ": " + count + " lần"));
    }

    // Phương thức đếm số lần xuất hiện của từ khóa trong file
    private static int searchKeywordInFile(String fileName, String keyword) {
        int count = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                count += line.split(keyword, -1).length - 1; // Đếm số lần xuất hiện của keyword trong dòng
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }
}

