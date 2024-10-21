package com.warehouse.message.repository;

import com.warehouse.commonassets.identificator.MessageId;
import com.warehouse.message.domain.model.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageRepositoryImpl implements MessageRepository {

    private final MessageReadRepository repository;

    public MessageRepositoryImpl(final MessageReadRepository repository) {
        this.repository = repository;
    }

    @Override
    public Message findByTitle(final String title) {
        return repository.findByTitle(title)
                .map(Message::from)
                .orElse(Message.empty());
    }

    @Override
    public List<Message> findBySender(final String sender) {
        return repository.findBySender(sender)
                .stream()
                .map(Message::from)
                .collect(Collectors.toList());
    }

    @Override
    public Message findByMessageId(final MessageId messageId) {
        return repository.findById(messageId.getValue())
                .map(Message::from)
                .orElse(Message.empty());
    }
}
