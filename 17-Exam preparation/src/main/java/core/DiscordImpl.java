package core;

import models.Message;

import java.util.List;

public class DiscordImpl implements Discord {

    @Override
    public void sendMessage(Message message) {

    }

    @Override
    public boolean contains(Message message) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Message getMessage(String messageId) {
        return null;
    }

    @Override
    public void deleteMessage(String messageId) {

    }

    @Override
    public void reactToMessage(String messageId, String reaction) {

    }

    @Override
    public Iterable<Message> getChannelMessages(String channel) {
        return null;
    }

    @Override
    public Iterable<Message> getMessagesByReactions(List<String> reactions) {
        return null;
    }

    @Override
    public Iterable<Message> getMessageInTimeRange(Integer lowerBound, Integer upperBound) {
        return null;
    }

    @Override
    public Iterable<Message> getTop3MostReactedMessages() {
        return null;
    }

    @Override
    public Iterable<Message> getAllMessagesOrderedByCountOfReactionsThenByTimestampThenByLengthOfContent() {
        return null;
    }
}
