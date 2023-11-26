package pe.edu.cibertec.dswiicl3diazguardamino.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/filesdoc")
public class DocFileController {

    private static final String UPLOAD_DIR = "Documentos";

    @PostMapping
    @PreAuthorize("hasRole('ROLE_Administrador')")
    public ResponseEntity<String> uploadDocFile(@RequestParam("file") MultipartFile file) {
        if (!file.getOriginalFilename().toLowerCase().endsWith(".doc")) {
            return ResponseEntity.badRequest().body("El archivo debe ser un documento Word");
        }

        if (file.getSize() > 2 * 1024 * 1024) {
            return ResponseEntity.badRequest().body("El tamaño del archivo no debe superar los 2 MB");
        }
        String fileName = storeFile(file, UPLOAD_DIR);
        return ResponseEntity.ok("Archivo subido correctamente :) Nombre de archivo: " + fileName);
    }

    private String storeFile(MultipartFile file, String uploadDir) {

        try {
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            Path filePath = dir.toPath().resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("Error al almacenar el archivo", e);
        }
    }

}
