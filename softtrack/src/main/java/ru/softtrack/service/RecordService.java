package ru.softtrack.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.softtrack.dto.response.RecordResponse;
import ru.softtrack.entity.Behavior;
import ru.softtrack.entity.UserEntity;
import ru.softtrack.exception.AccessDeniedException;
import ru.softtrack.exception.EntityNotFoundException;
import ru.softtrack.exception.ValidationException;
import ru.softtrack.mapper.RecordMapper;
import ru.softtrack.repository.BehaviorRepository;
import ru.softtrack.repository.RecordRepository;
import ru.softtrack.entity.Record;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final BehaviorRepository behaviorRepository;
    private final RecordMapper recordMapper;

    public Record createRecord(String title,
                               UserEntity creator,
                               UserEntity receiver,
                               Integer behaviorId,
                               String comment) {
        if (creator.getId().equals(receiver.getId())) {
            throw new ValidationException("You cannot create a record for yourself");
        }
        Behavior behavior = behaviorRepository.findById(behaviorId)
                .orElseThrow(() -> new EntityNotFoundException(Behavior.class));
        Record record = new Record();
        record.setTitle(title);
        record.setCreator(creator);
        record.setReceiver(receiver);
        record.setBehavior(behavior);
        record.setComment(comment);
        record.setCreatedAt(LocalDateTime.now());

        return recordRepository.save(record);
    }

    public Page<RecordResponse> getRecordByCreatorAndReceiver(UserEntity creator, UserEntity receiver, Pageable pageable) {
        Page<Record> recordsPage = recordRepository.findByCreatorAndReceiver(creator,receiver,pageable);
        return recordsPage.map(recordMapper::toResponse);
    }

    public void deleteRecord(Integer id, UserEntity currentUser) {
        Record record = recordRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Record.class));;

        if (!record.getCreator().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException();
        }
        recordRepository.delete(record);
    }
}
