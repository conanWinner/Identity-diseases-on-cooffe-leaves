package com.identify.leavescoffee.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/predict")
public class ControllerForModelAI {

    @PostMapping
    public ResponseEntity<Map<String, Object>> predict(@RequestParam("file") MultipartFile file) {

        try {
            // Lưu file ảnh tạm thời
            Path tempFile = Files.createTempFile("upload", file.getOriginalFilename());
            Files.write(tempFile, file.getBytes());

            // Gọi script Python để dự đoán
            //"src/main/java/com/identify/leavescoffee/modelAI/predict.py"
            ProcessBuilder processBuilder = new ProcessBuilder("python", System.getProperty("user.dir") + "\\leavescoffee\\src\\main\\java\\com\\identify\\leavescoffee\\modelAI\\predict.py", tempFile.toString());
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Đọc kết quả từ script Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String result = "";  // Đọc dòng kết quả (predicted_class,confidence)

            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }

            // Tách kết quả
            String[] resultArray = result.split("=======");

            String[] tmpAns =  resultArray[1].split(",");

            String predictedClass = tmpAns[0];
            float confidence = Float.parseFloat(tmpAns[1]);

            // Xóa file tạm
            Files.delete(tempFile);

            // Trả kết quả dự đoán về client
            Map<String, Object> response = new HashMap<>();
            response.put("class", predictedClass);
            response.put("confidence", confidence);

            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

