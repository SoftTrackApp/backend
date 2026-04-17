package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softtrack.entity.Behavior;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.exception.AccessDeniedException;
import ru.softtrack.exception.EntityNotFoundException;
import ru.softtrack.repository.RecordRepository;
import ru.softtrack.entity.Record;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;

    public Record createRecord(String title,
                               UserEntity creator,
                               UserEntity receiver,
                               Behavior behavior,
                               String comment) {
        Record record = new Record();
        record.setTitle(title);
        record.setCreator(creator);
        record.setReceiver(receiver);
        record.setBehavior(behavior);
        record.setComment(comment);
        record.setCreatedAt(LocalDateTime.now());

        return recordRepository.save(record);
    }

    public void deleteRecord(Integer id, UserEntity currentUser) {
        Record record = recordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Record.class, id));;

        if (!record.getCreator().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException();
        }
        recordRepository.delete(record);
    }
}
