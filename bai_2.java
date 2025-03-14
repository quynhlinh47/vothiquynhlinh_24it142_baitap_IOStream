package Btap_IOStream;

import java.io.*;

public class bai_2 {
    public static void main(String[] args) {
        String outputFile = "src/Btap_IOStream/output_bai2.txt";

        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            System.out.println("Nhập nội dung (gõ 'exit' để kết thúc):");

            String line;
            while (!(line = br.readLine()).equalsIgnoreCase("exit")) {
                bw.write(line);
                bw.newLine();
            }

            System.out.println("Dữ liệu đã được lưu vào " + outputFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

