package Kolokvium_II_Ispit;
import java.util.*;

class DuplicateNumberException extends Exception{
    public DuplicateNumberException(String message) {
        super(message);
    }
}
class Contact implements Comparable<Contact>{
    private String name;
    private String phone;

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString(){
        return name+" "+phone;
    }

    @Override
    public int compareTo(Contact o) {
        int res = this.name.compareTo(o.name);
        if(res == 0){
            return this.phone.compareTo(o.phone);
        }else{
            return res;
        }
    }
}
class PhoneBook{
    Set<String> phoneNumbers;
    Map<String, Set<Contact>> contactsBySubString;
    Map<String, Set<Contact>> contactsByName;
    public PhoneBook() {
        this.phoneNumbers = new HashSet<>();
        contactsBySubString = new HashMap<>();
        contactsByName = new HashMap<>();
    }

    private List<String> getSubstring(String phone){
        List<String> res = new ArrayList<>();
        for(int length = 3;length<=phone.length();length++){
            for(int i = 0;i<=phone.length()-length;i++){
                res.add(phone.substring(i,i+length));
            }
        }
        return res;
    }

    public void addContact(String name, String phone) throws DuplicateNumberException {

        if(phoneNumbers.contains(phone)){
            throw new DuplicateNumberException(String.format("Duplicate number: %s", phone));
        }else{
            phoneNumbers.add(phone);
            Contact contact = new Contact(name, phone);
            List<String> subStrings = getSubstring(phone);
            for(String string:subStrings){
                contactsBySubString.putIfAbsent(string, new TreeSet<>());
                contactsBySubString.get(string).add(contact);
            }
            contactsByName.putIfAbsent(name, new TreeSet<>());
            contactsByName.get(name).add(contact);
        }


    }

    public void contactsByNumber(String phone){
        Set<Contact> contacts = contactsBySubString.get(phone);
        if (contacts==null){
            System.out.println("NOT FOUND");
            return;
        }
        contacts.forEach(System.out::println);
    }

    public void contactsByName(String name){
        Set<Contact> contacts = contactsByName.get(name);
        if (contacts==null){
            System.out.println("NOT FOUND");
            return;
        }
        contacts.forEach(System.out::println);
    }
}
public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }

}



