package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.GalleryCatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link GalleryCat} and its DTO {@link GalleryCatDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GalleryCatMapper extends EntityMapper<GalleryCatDTO, GalleryCat> {


    @Mapping(target = "galleries", ignore = true)
    @Mapping(target = "removeGallery", ignore = true)
    GalleryCat toEntity(GalleryCatDTO galleryCatDTO);

    default GalleryCat fromId(Long id) {
        if (id == null) {
            return null;
        }
        GalleryCat galleryCat = new GalleryCat();
        galleryCat.setId(id);
        return galleryCat;
    }
}
