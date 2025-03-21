package BtapkethopIO_Thread;
//Ghi log bằng Thread
//Yêu cầu: Tạo một Thread ghi thông tin log (thời gian + thông điệp) vào file.
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class bai5 {
    private static final BlockingQueue<String> logQueue = new LinkedBlockingQueue<>();
    private static final String LOG_FILE = "src/BtapkethopIO_Thread/bai5.txt";

    static class LogWriterThread extends Thread {
        @Override
        public void run() {
            try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
                while (true) {
                    String logMessage = logQueue.take(); // Lấy log từ queue
                    if (logMessage.equals("EXIT")) break; // Nếu nhận được "EXIT" thì thoát

                    writer.println(logMessage);
                    writer.flush();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void log(String message) {
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            logQueue.put(timeStamp + " - " + message); // Đưa log vào queue
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) {
        LogWriterThread logThread = new LogWriterThread();
        logThread.start(); 

        log("Ứng dụng khởi động");
        log("Kết nối thành công");
        log("Lỗi: Không thể truy cập cơ sở dữ liệu");
        log("Ứng dụng đóng");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logQueue.add("EXIT"); 
    }
}

