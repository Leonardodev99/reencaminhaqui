package com.clafaleo.reencaminhaqui.services;

import com.clafaleo.reencaminhaqui.entities.Anexo;
import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.repositories.AnexoRepository;
import com.clafaleo.reencaminhaqui.repositories.MensagemRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.clafaleo.reencaminhaqui.exceptions.NegocioException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AnexoService {

    private final AnexoRepository anexoRepository;
    private final MensagemRepository mensagemRepository;
    private final Path storageLocation;

    public AnexoService(AnexoRepository anexoRepository,
                        MensagemRepository mensagemRepository,
                        @Value("${spring.file.storage.location:./uploads}") String storageLocationStr) {
        this.anexoRepository = anexoRepository;
        this.mensagemRepository = mensagemRepository;
        this.storageLocation = Paths.get(storageLocationStr).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.storageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível criar diretório de storage: " + this.storageLocation, e);
        }
    }

    @Transactional
    public Anexo salvar(MultipartFile file, Long mensagemId, String urlOrigem) {
        if (mensagemId == null) {
            throw new IllegalArgumentException("mensagemId é obrigatório");
        }
        Mensagem mensagem = mensagemRepository.findById(mensagemId)
                .orElseThrow(() -> new RuntimeException("Mensagem não encontrada com id: " + mensagemId));

        // salva arquivo no disco
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf('.'));
        }
        String filename = UUID.randomUUID().toString() + extension;
        Path target = storageLocation.resolve(filename);
        try {
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar arquivo: " + filename, e);
        }

        Anexo a = new Anexo();
        a.setUrlOrigem(urlOrigem);
        // urlLocalS3 guarda aqui um path relativo para download (p.ex. /uploads/<filename>) - adaptável para S3
        a.setUrlLocalS3(target.toString());
        a.setMimeType(file.getContentType());
        a.setMensagem(mensagem);

        return anexoRepository.save(a);
    }

    @Transactional(readOnly = true)
    public Optional<Anexo> buscarPorId(Long id) {
        return anexoRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Anexo> listarPorMensagem(Long mensagemId) {
        return anexoRepository.findByMensagemId(mensagemId);
    }

    @Transactional
    public void excluir(Long id) {
        Anexo a = anexoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Anexo não encontrado com id: " + id));
        // tenta apagar arquivo físico se existir
        if (a.getUrlLocalS3() != null) {
            try {
                Path p = Paths.get(a.getUrlLocalS3());
                Files.deleteIfExists(p);
            } catch (Exception ex) {
                // log e seguir com exclusão do registro
            }
        }
        anexoRepository.deleteById(id);
    }

    public Path getFilePath(Anexo a) {
        if (a.getUrlLocalS3() == null) {
            throw new RuntimeException("Arquivo físico não associado ao anexo: " + a.getId());
        }
        Path p = Paths.get(a.getUrlLocalS3());
        if (!p.isAbsolute()) {
            p = storageLocation.resolve(p);
        }
        return p.normalize();
    }
}