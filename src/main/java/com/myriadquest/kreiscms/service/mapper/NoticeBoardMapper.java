package com.myriadquest.kreiscms.service.mapper;


import com.myriadquest.kreiscms.domain.*;
import com.myriadquest.kreiscms.service.dto.NoticeBoardDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link NoticeBoard} and its DTO {@link NoticeBoardDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface NoticeBoardMapper extends EntityMapper<NoticeBoardDTO, NoticeBoard> {



    default NoticeBoard fromId(Long id) {
        if (id == null) {
            return null;
        }
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setId(id);
        return noticeBoard;
    }
}
