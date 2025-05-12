package Zbirka_Zadaci;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

class InvalidArchiveOpenException extends Exception {
    public InvalidArchiveOpenException(String message) {
        super(message);
    }
}

class NonExistingItemException extends Exception {
    public NonExistingItemException(String message) {
        super(message);
    }
}

abstract class Archive {
    int id;
    LocalDate dateArchived;

    public void archive(LocalDate date) {
        this.dateArchived = date;
    }

    public abstract LocalDate open(LocalDate date)
            throws InvalidArchiveOpenException;

    public int getId() {
        return id;
    }
}

class LockedArchive extends Archive {
    LocalDate dateToOpen;

    public LockedArchive(int id, LocalDate dateToOpen) {
        this.id = id;
        this.dateToOpen = dateToOpen;
    }

    @Override
    public LocalDate open(LocalDate date) throws InvalidArchiveOpenException {
        if (date.isBefore(dateToOpen)) {
            throw new InvalidArchiveOpenException(String.format("Item %d cannot be opened before %s", id, dateToOpen));
        }
        return date;
    }
}

class SpecialArchive extends Archive {
    int maxOpen;
    int counterOpen;

    public SpecialArchive(int id, int maxOpen) {
        this.id = id;
        this.maxOpen = maxOpen;
        this.counterOpen = 0;
    }

    @Override
    public LocalDate open(LocalDate date) throws InvalidArchiveOpenException {
        if (counterOpen >= maxOpen) {
            throw new InvalidArchiveOpenException(String.format("Item %d cannot be opened more that %d times", id, maxOpen));
        }
        counterOpen++;
        return date;
    }
}

class ArchiveStore {
    List<Archive> archives;
    StringBuilder log;

    public ArchiveStore() {
        archives = new ArrayList<>();
        log = new StringBuilder();
    }

    public void archiveItem(Archive item, LocalDate date) {
        item.archive(date);
        archives.add(item);
        log.append(String.format("Item %d archived at %s\n", item.getId(), date.toString()));
    }

    public Archive openItem(int id, LocalDate date) throws NonExistingItemException {
        for (Archive archive : archives) {
            if (archive.getId() == id) {
                try {
                    archive.open(date);
                } catch (InvalidArchiveOpenException e) {
                    System.out.println(e.getMessage());
                    return archive;
                }
            }
            log.append(String.format("Item %d opened at %s\n", id, date));
            return archive;
        }
        throw new NonExistingItemException(String.format("Doesnt exists an item with id %d",id));
    }

    public String getLog(){
        return log.toString();
    }
}

public class ArchiveStoreTest {
    public static void main(String[] args) {
        ArchiveStore store = new ArchiveStore();
        LocalDate date = LocalDate.of(2013, 10, 7);
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        int n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        int i;
        for (i = 0; i < scanner.nextInt(); i++) {
            int id = scanner.nextInt();
            long days = scanner.nextLong();
            LocalDate dateToOpen = date.atStartOfDay()
                    .plusSeconds(days * 24 * 60 * 60).toLocalDate();
            LockedArchive lockedArchive = new LockedArchive(id, dateToOpen);
            store.archiveItem(lockedArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        n = scanner.nextInt();
        scanner.nextLine();
        scanner.nextLine();
        for (i = 0; i < n; i++) {
            int id = scanner.nextInt();
            int maxOpen = scanner.nextInt();
            SpecialArchive specialArchive = new SpecialArchive(id, maxOpen);
            store.archiveItem(specialArchive, date);
        }
        scanner.nextLine();
        scanner.nextLine();
        while (scanner.hasNext()) {
            int open = scanner.nextInt();
            try {
                store.openItem(open, date);
            } catch (NonExistingItemException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println(store.getLog());
    }

}
