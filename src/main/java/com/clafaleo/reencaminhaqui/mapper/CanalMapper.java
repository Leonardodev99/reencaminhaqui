package com.clafaleo.reencaminhaqui.mapper;

import com.clafaleo.reencaminhaqui.entities.dto.CanalDTO;
import com.clafaleo.reencaminhaqui.entities.Canal;

public final class CanalMapper {
    private CanalMapper() {}

    public static Canal toEntity(CanalDTO dto) {
        if (dto == null) return null;
        Canal c = new Canal();
        c.setId(dto.getId());
        c.setNome(dto.getNome());
        c.setProvedor(dto.getProvedor());
        c.setStatusCanal(dto.getStatusCanal());
        return c;
    }

    public static CanalDTO toDto(Canal c) {
        if (c == null) return null;
        return new CanalDTO(c.getId(), c.getNome(), c.getProvedor(), c.getStatusCanal());
    }
}