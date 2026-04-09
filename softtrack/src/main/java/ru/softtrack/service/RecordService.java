package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.repository.RecordRepository;
import ru.softtrack.entity.Record;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public Record createRecord(String title,
                               UserEntity creator,
                               UserEntity receiver,
                               Integer behaviorId,
                               String comment) {
        Record record = new Record();
        record.setTitle(title);
        record.setCreator(creator);
        record.setReceiver(receiver);
        record.setBehaviorId(behaviorId);
        record.setComment(comment);
        record.setCreatedAt(LocalDateTime.now());

        return recordRepository.save(record);
    }
}
