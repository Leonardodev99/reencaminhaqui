package com.clafaleo.reencaminhaqui.controllers;

import com.clafaleo.reencaminhaqui.entities.Anexo;
import com.clafaleo.reencaminhaqui.entities.dto.AnexoDTO;
import com.clafaleo.reencaminhaqui.mapper.AnexoMapper;
import com.clafaleo.reencaminhaqui.services.AnexoService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/anexos")
public class AnexoController {

    private final AnexoService service;

    public AnexoController(AnexoService service) {
        this.service = service;
    }

    // Upload: multipart form-data
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AnexoDTO> upload(@RequestPart("file") MultipartFile file,
                                           @RequestPart("mensagemId") Long mensagemId,
                                           @RequestPart(value = "urlOrigem", required = false) String urlOrigem) {
        Anexo salvo = service.salvar(file, mensagemId, urlOrigem);
        AnexoDTO dto = AnexoMapper.toDto(salvo);
        return ResponseEntity.created(URI.create("/api/anexos/" + salvo.getId())).body(dto);
    }

    // Get DTO
    @GetMapping("/{id}")
    public ResponseEntity<AnexoDTO> buscar(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(AnexoMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Download file
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable Long id, HttpServletRequest request) {
        Anexo a = service.buscarPorId(id).orElseThrow(() -> new RuntimeException("Anexo não encontrado com id: " + id));
        Path filePath = service.getFilePath(a);
        try {
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists() || !resource.isReadable()) {
                throw new RuntimeException("Arquivo não encontrado: " + filePath);
            }
            String contentType = a.getMimeType();
            if (contentType == null) contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            if (contentType == null) contentType = "application/octet-stream";
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao ler arquivo: " + ex.getMessage(), ex);
        }
    }

    // Listar por mensagem
    @GetMapping("/by-mensagem/{mensagemId}")
    public ResponseEntity<List<AnexoDTO>> listarPorMensagem(@PathVariable Long mensagemId) {
        List<AnexoDTO> list = service.listarPorMensagem(mensagemId).stream().map(AnexoMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(list);
    }

    // Deletar anexo
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}