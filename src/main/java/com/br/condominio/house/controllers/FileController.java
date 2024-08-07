package com.br.condominio.house.controllers;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.UploadFileModel;
import com.br.condominio.house.services.FileStorageService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/docs")
public class FileController {

    @Autowired
    private FileStorageService service;

    @PostMapping(value ="/v1")
    public UploadFileModel uploadFile(@RequestParam("file") MultipartFile file){
        String fileName = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/docs/downloadFile").path(fileName).toUriString();
        return new UploadFileModel(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    //upload de vários arquivos
    @PostMapping(value = "/vs1")
    public List<UploadFileModel> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files){
        return Arrays.asList(files)
        .stream()
        .map(file -> uploadFile(file))
        .collect(Collectors.toList());
    }

    // Requisição com ResponseEntity
    // @PostMapping("/v2")
    // public ResponseEntity<String> uploadFile1(@RequestParam("file") MultipartFile file) {
    //     try {
    //         String fileName = service.storeFile(file);
    //         return ResponseEntity.status(HttpStatus.OK).body("Arquivo armazenado com sucesso: " + fileName);
    //     } catch (FileStorageException e) {
    //         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao armazenar o arquivo: " + e.getMessage());
    //     }
    // }


}
