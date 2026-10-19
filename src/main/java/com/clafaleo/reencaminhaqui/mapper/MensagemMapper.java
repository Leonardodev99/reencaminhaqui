package com.clafaleo.reencaminhaqui.mapper;

import com.clafaleo.reencaminhaqui.entities.Mensagem;
import com.clafaleo.reencaminhaqui.entities.Rota;
import com.clafaleo.reencaminhaqui.entities.dto.MensagemDTO;

import java.util.List;
import java.util.stream.Collectors;

public final class MensagemMapper {

    private MensagemMapper() {}

    public static Mensagem toEntity(MensagemDTO dto) {
        if (dto == null) return null;
        Mensagem m = new Mensagem();
        m.setId(dto.getId());
        m.setRemetenteOriginal(dto.getRemetenteOriginal());
        m.setConteudoTexto(dto.getConteudoTexto());
        m.setPayloadBruto(dto.getPayloadBruto());
        m.setRecebidoEm(dto.getRecebidoEm());
        m.setTipoConteudo(dto.getTipoConteudo());
        if (dto.getRotaId() != null) {
            Rota r = new Rota();
            r.setId(dto.getRotaId());
            m.setRota(r);
        }
        // não popula anexos/reencaminhamentos completos aqui — deixe endpoints específicos gerenciarem isso
        return m;
    }

    public static MensagemDTO toDto(Mensagem entity) {
        if (entity == null) return null;
        return new MensagemDTO(entity);
    }

    public static List<MensagemDTO> toDtoList(List<Mensagem> mensagens) {
        return mensagens == null ? List.of() : mensagens.stream().map(MensagemMapper::toDto).collect(Collectors.toList());
    }
}