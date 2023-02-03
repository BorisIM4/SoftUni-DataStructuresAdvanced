package core;

import models.Message;

import java.util.*;
import java.util.stream.Collectors;

public class DiscordImpl implements Discord {

    private final Map<String, Message> messageByID;

    private final Map<String, Set<Message>> messageByChannel;

    public DiscordImpl() {
        this.messageByID = new LinkedHashMap<>();

        this.messageByChannel = new LinkedHashMap<>();
    }

    @Override
    public void sendMessage(Message message) {
        String messageId = message.getId();
        this.messageByID.put(messageId, message);

        String channel = message.getChannel();
        if (!this.messageByChannel.containsKey(channel)) {
            this.messageByChannel.put(channel, new LinkedHashSet<>());
        }

        Set<Message> messages = this.messageByChannel.get(channel);
        messages.add(message);
        this.messageByChannel.put(channel, messages);
    }

    @Override
    public boolean contains(Message message) {
        return this.messageByID.containsKey(message.getId());
    }

    @Override
    public int size() {
        return this.messageByID.size();
    }

    @Override
    public Message getMessage(String messageId) {
        Message message = this.messageByID.get(messageId);

        if (message == null) {
            throw new IllegalArgumentException();
        }

        return message;
    }

    @Override
    public void deleteMessage(String messageId) {
        Message result = this.messageByID.remove(messageId);

        if (result == null) {
            throw new IllegalArgumentException();
        }

        this.messageByChannel.get(result.getChannel()).remove(result);
    }

    @Override
    public void reactToMessage(String messageId, String reaction) {
        Message message = this.getMessage(messageId);

        List<String> reactions = message.getReactions();

        reactions.add(reaction);
    }

    @Override
    public Iterable<Message> getChannelMessages(String channel) {
        Set<Message> messages = this.messageByChannel.get(channel);

        if (messages == null) {
            throw new IllegalArgumentException();
        }

        return messages;
    }

    @Override
    public Iterable<Message> getMessagesByReactions(List<String> reactions) {
        List<Message> allMessagesContainsReaction = new LinkedList<>();

        for (Message message : this.messageByID.values()) {
            List<String> currentReactions = message.getReactions();

            if (currentReactions.containsAll(reactions)) {
                allMessagesContainsReaction.add(message);
            }
        }

        return allMessagesContainsReaction.stream()
                .sorted((l, r) -> {
                    if (l.getReactions().size() != r.getReactions().size()) {
                        return r.getReactions().size() - l.getReactions().size();
                    }
                    return l.getTimestamp() - r.getTimestamp();
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getMessageInTimeRange(Integer lowerBound, Integer upperBound) {
        return  this.messageByID.values()
                .stream()
                .filter(message -> message.getTimestamp() >= lowerBound && message.getTimestamp() <= upperBound)
                .sorted((l, r) -> {
                    int lChanelMessagesCount = this.messageByChannel.get(l.getChannel()).size();
                    int rChanelMessagesCount = this.messageByChannel.get(r.getChannel()).size();

                    return rChanelMessagesCount - lChanelMessagesCount;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getTop3MostReactedMessages() {
        return this.messageByID.values()
                .stream()
                .sorted((l, r) -> {
                    int lMessageReactionCount = l.getReactions().size();
                    int rMessageReactionCount = r.getReactions().size();

                    return rMessageReactionCount - lMessageReactionCount;
                })
                .limit(3)
                .collect(Collectors.toList());
    }

    @Override
    public Iterable<Message> getAllMessagesOrderedByCountOfReactionsThenByTimestampThenByLengthOfContent() {
        return this.messageByID
                .values()
                .stream()
                .sorted((l,r) -> {
                    if (l.getReactions().size() != r.getReactions().size()) {
                        return r.getReactions().size() - l.getReactions().size();
                    }

                    if (l.getTimestamp() != r.getTimestamp()) {
                        return l.getTimestamp() - r.getTimestamp();
                    }

                    return l.getContent().length() - r.getTimestamp();
                })
                .collect(Collectors.toList());
    }
}



















