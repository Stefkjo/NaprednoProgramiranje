package Kolokvium_II_Ispit;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

class PartitionDoesNotExistsException extends Exception {
    public PartitionDoesNotExistsException(String message) {
        super(message);
    }
}

class UnsupportedOperationException extends Exception {
    public UnsupportedOperationException(String message) {
        super(message);
    }
}

class PartitionAssigner {
    public static Integer assignPartition(Message message, int partitionCount) {
        return (Math.abs(message.getKey().hashCode()) % partitionCount) + 1;
    }
}

class Message implements Comparable<Message> {
    private LocalDateTime timestamp;
    private String message;
    private Integer partition;
    private String key;

    public Message(LocalDateTime timestamp, String message, String key) {
        this.message = message;
        this.key = key;
        this.timestamp = timestamp;
    }

    public Message(LocalDateTime timestamp, String message, Integer partition, String key) {
        this.timestamp = timestamp;
        this.message = message;
        this.partition = partition;
        this.key = key;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public Integer getPartition() {
        return partition;
    }

    public String getKey() {
        return key;
    }

    @Override
    public int compareTo(Message o) {
        return this.timestamp.compareTo(o.timestamp);
    }

    @Override
    public String toString() {
        return "Message{" +
                "timestamp=" + timestamp +
                ", message='" + message + '\'' +
                '}';
    }

}

class Partition {
    private Integer number;
    private TreeSet<Message> messages;

    public Partition(int number) {
        this.number = number;
        messages = new TreeSet<>();
    }

    public void addMessage(Message message) {
        if (message.getTimestamp().isBefore(MessageBroker.MINIMUM_DATE)) {
            return;
        }
        if (messages.size() == MessageBroker.CAPACITY_PER_TOPIC) {
            messages.removeFirst();
        }

        messages.add(message);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%2d : Count of messages:%6d\n", number, messages.size()));
        builder.append("Messages:\n");
        String messagesStr = messages.stream().
                map(Message::toString)
                .collect(Collectors.joining("\n"));
        builder.append(messagesStr);
        return builder.toString();
    }
}

class Topic {
    private String name;
    private int partitionsCount;
    private TreeMap<Integer, Partition> partitions;

    public Topic(String name, int partitionsCount) {
        this.name = name;
        this.partitionsCount = partitionsCount;

        partitions = new TreeMap<>();
        for (int i = 1; i <= partitionsCount; i++) {
            partitions.put(i, new Partition(i));
        }
    }

    public void addMessage(Message message) throws PartitionDoesNotExistsException {
        Integer partition = message.getPartition();
        if (partition == null) {
            partition = PartitionAssigner.assignPartition(message, partitionsCount);
        }

        if (!partitions.containsKey(partition)) {
            throw new PartitionDoesNotExistsException(String.format("The topic %s doesnt have a partition with number %d", name, partition));
        }
        partitions.get(partition).addMessage(message);
    }

    public void changeNumberOfPartitions(int newPartitionNumber) throws UnsupportedOperationException {
        if (newPartitionNumber < partitionsCount) {
            throw new UnsupportedOperationException("");
        } else if (newPartitionNumber > partitionsCount) {
            for (int i = partitionsCount + 1; i <= newPartitionNumber; i++) {
                partitions.put(i, new Partition(i));
            }
            partitionsCount = newPartitionNumber;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Topic: %10s Partitions: %5d", name, partitionsCount));
        String partitionStr = partitions.values()
                .stream()
                .map(Partition::toString)
                .collect(Collectors.joining("\n"));
        builder.append(partitionStr);
        return builder.toString();
    }
}

class MessageBroker {
    public static int CAPACITY_PER_TOPIC;
    public static LocalDateTime MINIMUM_DATE;
    private Map<String, Topic> topics;

    public MessageBroker(LocalDateTime minimumDate, Integer capacityPerTopic) {
        MINIMUM_DATE = minimumDate;
        CAPACITY_PER_TOPIC = capacityPerTopic;
        topics = new TreeMap<>();
    }

    public void addTopic(String topic, int partitionsCount) {
        if (!topics.containsKey(topic)) {
            topics.put(topic, new Topic(topic, partitionsCount));
        }
    }

    public void addMessage(String topic, Message message) throws PartitionDoesNotExistsException {
        topics.get(topic).addMessage(message);
    }

    public void changeTopicSettings(String topic, int partitionsCount) throws UnsupportedOperationException {
        topics.get(topic).changeNumberOfPartitions(partitionsCount);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Broker with %2d topics: \n", topics.size()));
        String topicsStr = topics.values()
                .stream()
                .map(Topic::toString)
                .collect(Collectors.joining("\n"));
        builder.append(topicsStr);
        return builder.toString();
    }


}

public class MessageBrokersTest {
    public static void main(String[] args) {

    }
}
