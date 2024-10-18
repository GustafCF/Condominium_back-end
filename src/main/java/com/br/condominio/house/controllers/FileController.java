package com.br.condominio.house.controllers;

import org.springframework.http.HttpHeaders;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.condominio.house.models.UploadFileModel;
import com.br.condominio.house.services.FileStorageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/docs")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private FileStorageService service;

    @Operation(summary = "Upload de um arquivo",
            description = "Faz o upload de um arquivo e retorna as informações do arquivo armazenado.",
            tags = {"Arquivos"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo enviado com sucesso",
                content = @Content(schema = @Schema(implementation = UploadFileModel.class))),
        @ApiResponse(responseCode = "400", description = "Requisição inválida ou erro no envio do arquivo")
    })
    @PostMapping(value = "/v1")
    public UploadFileModel uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = service.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/downloadFile/").path(fileName).toUriString();
        return new UploadFileModel(fileName, fileDownloadUri, file.getContentType(), file.getSize());
    }

    //upload de vários arquivos
    @Operation(summary = "Upload de múltiplos arquivos",
            description = "Faz o upload de vários arquivos e retorna as informações de cada arquivo.",
            tags = {"Arquivos"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivos enviados com sucesso"),
        @ApiResponse(responseCode = "400", description = "Requisição inválida ou erro no envio dos arquivos")
    })
    @PostMapping(value = "/vs1")
    public List<UploadFileModel> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    //download de arquivos
    @Operation(summary = "Download de arquivo",
            description = "Faz o download de um arquivo armazenado no servidor.",
            tags = {"Arquivos"})
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Arquivo baixado com sucesso",
                content = @Content(mediaType = "application/octet-stream")),
        @ApiResponse(responseCode = "404", description = "Arquivo não encontrado")
    })
    @GetMapping(value = "/vs1/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = service.loadFileAsResource(fileName);

        String contentType = null;

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.info("Não foi possível determinar o tipo de arquivo.");
        }

        if (contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
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
