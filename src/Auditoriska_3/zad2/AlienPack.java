package Auditoriska_3.zad2;

public class AlienPack {
    private Alien[] aliens;

    public AlienPack(int numAliens) {
        aliens = new Alien[numAliens];
    }

    public void addAlien(Alien newAlien, int index) {
        aliens[index] = newAlien;
    }

    public Alien[] getAliens() {
        return aliens;
    }

    public void print(){
        for(Alien alien:aliens){
            System.out.println(alien);
        }
    }

    public int calculateDamage() {
        int damage = 0;
        for (Alien alien : aliens) {
           damage += alien.getDamage();
        }
        return damage;
    }

    public static void main(String[] args) {
        AlienPack pack = new AlienPack(4);
        Alien al1 = new SnakeAlien("S", 10);
        MarshmallowManAlien mal2 = new MarshmallowManAlien("MM", 12);
        OgreAlien oal3 = new OgreAlien("Shrek", 100);
        Alien mal4 = new MarshmallowManAlien("MMM", 1234);
        pack.addAlien(al1,0);
        pack.addAlien(mal2,1);
        pack.addAlien(oal3, 2);
        pack.addAlien(mal4,3);
        System.out.println(pack.calculateDamage());
        //pack.print();
    }

}
