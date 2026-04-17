package ru.softtrack.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ru.softtrack.dto.response.RecordResponse;
import ru.softtrack.entity.Record;

@Component
@RequiredArgsConstructor
public class RecordMapper {

    public RecordResponse toResponse(Record record) {
        if (record == null) {return null;}

        return new RecordResponse(
                record.getId(),
                record.getTitle(),
                record.getBehavior().getId(),
                record.getComment(),
                record.getCreatedAt()
        );
    }

    public Page<RecordResponse> toResponsePage(Page<Record> recordsPage) {
        if (recordsPage == null) {
            return Page.empty();
        }
        return recordsPage.map(this::toResponse);
    }
}
