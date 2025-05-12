package Zbirka_Zadaci;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

class InvalidSpotNumberException extends Exception{
    public InvalidSpotNumberException(String message) {
        super(message);
    }
}

class SpotTakenException extends Exception{
    public SpotTakenException(String message) {
        super(message);
    }
}

class CarNotFoundException extends Exception{
    public CarNotFoundException(String message) {
        super(message);
    }
}

class NoSuchSectorException extends Exception{
    public NoSuchSectorException(String message) {
        super(message);
    }
}
class ParkingSector{
    private String code;
    private int count;
    private Map<Integer, String> spots;
    private Map<String, Integer> index;

    public ParkingSector(String code, int count){
        this.code = code;
        this.count = count;
        this.spots = new HashMap<>();
        this.index = new HashMap<>();
    }

    public void addCar(int spotNumber, String registrationNumber) throws InvalidSpotNumberException, SpotTakenException {
       if(spotNumber<=0 || spotNumber > count){
           throw new InvalidSpotNumberException("Invalid spot number");
       }
       if(spots.containsKey(spotNumber)){
           throw new SpotTakenException("Spot is alreay taken");
       }
       spots.put(spotNumber, registrationNumber);
       index.put(registrationNumber, spotNumber);
    }

    public int findSpotNumber(String registrationNumber) throws CarNotFoundException {
        if(index.containsKey(registrationNumber)){
            return index.get(registrationNumber);
        }
        throw new CarNotFoundException(String.format("Car with RN %s not found", registrationNumber));
    }

    public void clearSpot(int spotNumber){
        spots.remove(spotNumber);
    }

    public String getCode(){
        return this.code;
    }

    @Override
    public String toString(){
        return String.format("%s : %d/%d", code, spots.size(), count);
    }

}
class CityParking{
    String name;
    private Map<String, ParkingSector> sectors;
    private Map<String, ParkingSector> index;

    public CityParking(String name){
        this.name = name;
        sectors = new TreeMap<>();
        index = new HashMap<>();
    }

    public void createSectors(String [] sectorNames, int [] counts){
        for(int i = 0;i<sectorNames.length;i++){
            ParkingSector parkingSector = new ParkingSector(sectorNames[i], counts[i]);
            sectors.put(sectorNames[i], parkingSector);
        }
    }

    public void addCar(String sectorName, int spotNumber, String registrationNumber) throws InvalidSpotNumberException, SpotTakenException, NoSuchSectorException {
        ParkingSector sector = sectors.get(sectorName);
        if(sector!=null){
            sector.addCar(spotNumber, registrationNumber);
            index.put(registrationNumber, sector);
        }else{
            throw new NoSuchSectorException(String.format("No sector with name %s", sectorName));
        }
    }

    public void findCar(String registrationNumber) throws CarNotFoundException {
        if(index.containsKey(registrationNumber)){
            ParkingSector sector = index.get(registrationNumber);
            int spotNumber = sector.findSpotNumber(registrationNumber);
            System.out.printf("%s %d%n", sector.getCode(), spotNumber);
        }else{
            throw new CarNotFoundException(String.format("Car with RN %s not found", registrationNumber));
        }
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        for(Map.Entry<String, ParkingSector> sector : sectors.entrySet()){
            sb.append(sector.getValue().toString()).append("\n");
        }

        return sb.toString();
    }


}

public class ParkingTest {

}
