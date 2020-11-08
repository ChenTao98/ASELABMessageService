package com.chentao.aselab.message.repository;

import com.chentao.aselab.message.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface MessageRepository extends CrudRepository<Message, Long> {
    Message findMessageByMessageId(Long messageId);
    Set<Message> findMessagesByUserId(Long userId);

}
