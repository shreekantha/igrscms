package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.GalleryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gallery} and its DTO {@link GalleryDTO}.
 */
@Mapper(componentModel = "spring", uses = {GalleryCatMapper.class})
public interface GalleryMapper extends EntityMapper<GalleryDTO, Gallery> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.name", target = "categoryName")
    GalleryDTO toDto(Gallery gallery);

    @Mapping(source = "categoryId", target = "category")
    Gallery toEntity(GalleryDTO galleryDTO);

    default Gallery fromId(Long id) {
        if (id == null) {
            return null;
        }
        Gallery gallery = new Gallery();
        gallery.setId(id);
        return gallery;
    }
}
