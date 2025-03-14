package Btap_IOStream;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class bai7 {
    private static final String INPUT_FILE = "src/Btap_IOStream/large_file.txt";
    private static final String OUTPUT_FILE = "src/Btap_IOStream/output_7.txt"; 
    private static final int CHUNK_SIZE = 1024 * 1024; // 1MB mỗi phần
    private static final int THREAD_COUNT = 4; // Số luồng đọc đồng thời

    public static void main(String[] args) {
        File file = new File(INPUT_FILE);
        long fileSize = file.length();
        int numChunks = (int) Math.ceil((double) fileSize / CHUNK_SIZE);

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        for (int i = 0; i < numChunks; i++) {
            long start = i * CHUNK_SIZE;
            long end = Math.min(start + CHUNK_SIZE, fileSize);
            executor.execute(new FileReaderTask(i, start, end));
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            // Chờ tất cả các luồng hoàn thành
        }

        mergeFiles(numChunks);
        System.out.println("Đọc và ghép file hoàn tất!");
    }

    static class FileReaderTask implements Runnable {
        private final int chunkIndex;
        private final long start;
        private final long end;

        public FileReaderTask(int chunkIndex, long start, long end) {
            this.chunkIndex = chunkIndex;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            try (RandomAccessFile raf = new RandomAccessFile(INPUT_FILE, "r")) {
                raf.seek(start); // Di chuyển đến vị trí bắt đầu
                byte[] buffer = new byte[(int) (end - start)];
                raf.readFully(buffer);

                try (FileOutputStream fos = new FileOutputStream("chunk_" + chunkIndex + ".part")) {
                    fos.write(buffer);
                }

                System.out.println("Đọc xong phần " + chunkIndex);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Gộp các file theo đúng thứ tự
    private static void mergeFiles(int numChunks) {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(OUTPUT_FILE))) {
            for (int i = 0; i < numChunks; i++) {
                File partFile = new File("chunk_" + i + ".part");
                try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(partFile))) {
                    byte[] buffer = new byte[CHUNK_SIZE];
                    int bytesRead;
                    while ((bytesRead = bis.read(buffer)) != -1) {
                        bos.write(buffer, 0, bytesRead);
                    }
                }
                partFile.delete(); // Xóa file tạm
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
