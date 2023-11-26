package pe.edu.cibertec.dswiicl3diazguardamino.controller;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/filespdf")
public class filesPdf {

    private static final String UPLOAD_DIR = "Documentos";

    @PostMapping
    @PreAuthorize("hasRole('ROLE_Supervisor')")
    public ResponseEntity<String> uploadPdfFile(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().toLowerCase().endsWith(".pdf")) {
            return ResponseEntity.badRequest().body("El archivo debe ser un PDF :)");
        }

        String fileName = storeFile(file, UPLOAD_DIR);
        return ResponseEntity.ok("El archivo PDF fue subido correctamente :) Nombre de archivo: " + fileName);
    }

    private String storeFile(MultipartFile file, String uploadDir) {
        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File newFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(newFile,
                    file.getBytes());
            return newFile.getName();
        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el archivo", e);
        }
    }
}
