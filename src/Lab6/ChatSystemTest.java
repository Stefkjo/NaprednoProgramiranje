package Lab6;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.TreeSet;

class NoSuchRoomException extends Exception{
    public NoSuchRoomException(String roomName) {
        super(String.format("No room with name %s", roomName));
    }
}

class NoSuchUserException extends Exception{
    public NoSuchUserException(String userName) {
        super(String.format("No user with username %s",userName));
    }
}

class ChatRoom {
    private String name;
    private Set<String> users;

    public ChatRoom(String name){
        this.name = name;
        users = new TreeSet<>();
    }

    public void addUser(String username){
        users.add(username);
    }

    public void removeUser(String username){
        if(users.contains(username)){
            users.remove(username);
        }else return;
    }

    public boolean hasUser(String username){
        return users.contains(username);
    }

    public int numUsers(){
        return users.size();
    }

    @Override
    public String toString(){
        if(!users.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append(name).append("\n");
            users.forEach(sb::append);
            return sb.toString();
        }
        return name + "\n" + "EMPTY";
    }

}

class ChatSystem{
    private Map<String, ChatRoom> rooms;
    private Set<String> allUsers;

    public ChatSystem(){
        this.rooms = new TreeMap<>();
        this.allUsers = new TreeSet<>();
    }

    public void addRoom(String roomName){
        rooms.put(roomName, new ChatRoom(roomName));
    }

    public void removeRoom(String roomName){
        rooms.remove(roomName);
    }

    public ChatRoom getRoom(String roomName) throws NoSuchRoomException {
        if(rooms.containsKey(roomName)){
            return rooms.get(roomName);
        }else{
            throw new NoSuchRoomException(roomName);
        }
    }

    public void register(String userName) {
        allUsers.add(userName);
    }

    public void registerAndJoin(String userName, String roomName){
        allUsers.add(userName);
        rooms.get(roomName).addUser(userName);
    }

    public void joinRoom(String userName, String roomName) throws NoSuchUserException, NoSuchRoomException {
        if(!allUsers.contains(userName)){
            throw new NoSuchUserException(userName);
        }
        if(!rooms.containsKey(roomName)){
            throw new NoSuchRoomException(roomName);
        }
        rooms.get(roomName).addUser(userName);
    }

    public void leaveRoom(String userName, String roomName){

    }

}


public class ChatSystemTest {

    public static void main(String[] args) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchRoomException {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) {
            ChatRoom cr = new ChatRoom(jin.next());
            int n = jin.nextInt();
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr.addUser(jin.next());
                if (k == 1) cr.removeUser(jin.next());
                if (k == 2) System.out.println(cr.hasUser(jin.next()));
            }
            System.out.println("");
            System.out.println(cr.toString());
            n = jin.nextInt();
            if (n == 0) return;
            ChatRoom cr2 = new ChatRoom(jin.next());
            for (int i = 0; i < n; ++i) {
                k = jin.nextInt();
                if (k == 0) cr2.addUser(jin.next());
                if (k == 1) cr2.removeUser(jin.next());
                if (k == 2) cr2.hasUser(jin.next());
            }
            System.out.println(cr2.toString());
        }
        if (k == 1) {
            ChatSystem cs = new ChatSystem();
            Method mts[] = cs.getClass().getMethods();
            while (true) {
                String cmd = jin.next();
                if (cmd.equals("stop")) break;
                if (cmd.equals("print")) {
                    System.out.println(cs.getRoom(jin.next()) + "\n");
                    continue;
                }
                for (Method m : mts) {
                    if (m.getName().equals(cmd)) {
                        String params[] = new String[m.getParameterTypes().length];
                        for (int i = 0; i < params.length; ++i) params[i] = jin.next();
                        m.invoke(cs, params);
                    }
                }
            }
        }
    }

}
