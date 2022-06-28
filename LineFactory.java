import java.util.*;

public class LineFactory {
    final boolean DEBUG = false;
    
    final int GROUPS    = 3;

    public LineFactory(){}

    public LinkedList<Passenger> createLine(Plane plane, String lineType){
        if (lineType.equalsIgnoreCase("Random"))
            return createRandomLine(plane);

        if (lineType.equalsIgnoreCase("AmericanFirst"))
            return createFirstGroupLine(plane);

        if (lineType.equalsIgnoreCase("AmericanLast"))
            return createLastGroupLine(plane);

        if (lineType.equalsIgnoreCase("Southwest"))
            return createSouthwestLine(plane);

        return null;
    }
    public LinkedList<Passenger> createRandomLine(Plane plane){
        LinkedList<Passenger> line = new LinkedList<>();
        for (int i = 0; i < plane.getSize(); i++){
            line.add(new Passenger(i));
        }

        Collections.shuffle(line);

        if (DEBUG) System.out.println(line);

        return line;
    }

    public LinkedList<Passenger> createFirstGroupLine(Plane plane){
        LinkedList<Passenger> line  = new LinkedList<>();

        int group_factor = ((plane.getSize() / (plane.getNumRows() * plane.getRowLen())) >= GROUPS)
                            ?
                            (plane.getSize() - (plane.getSize() % GROUPS))/GROUPS
                            : 
                            plane.getSize();

        if (group_factor == plane.getSize()){
            if (DEBUG) System.out.println("Creating random line...");
            return createRandomLine(plane);
        }
        
        if (DEBUG) System.out.println("Creating " + GROUPS + " grouped line...");

        ArrayList<Passenger> list = new ArrayList<>();
        for (int i = 0; i < plane.getSize(); i++){
            if (i != 0 && i % group_factor == 0 && plane.getSize() - i >= group_factor){
                Collections.shuffle(list);
                if (DEBUG) System.out.println("Split:\n" + list);
                for (Passenger p : list){
                    line.add(p);
                }
                list.clear();
            }

            list.add(new Passenger(i));
        }

        Collections.shuffle(list);
        if (DEBUG) System.out.println("Split:\n" + list);
        for (Passenger p : list){
            line.add(p);
        }

        if (DEBUG) System.out.println("Front to Back list:\n" + line);
        return line;
    }

    public LinkedList<Passenger> createLastGroupLine(Plane plane){
        LinkedList<Passenger> line = createFirstGroupLine(plane);

        Collections.reverse(line);
        
        if (DEBUG) System.out.println("Back to Front\n" + line);
        return line;
    }

    public LinkedList<Passenger> createSouthwestLine(Plane plane){
        LinkedList<Passenger> line = new LinkedList<>();

        for (int i = GROUPS-1; i >= 0; i--){
            int front_pointer = i*(plane.getSize()/GROUPS);

            if (i == GROUPS-1)
            for (int k = front_pointer; k < plane.getSize(); k++){
                line.add(new Passenger(k));
            }

            else
            for (int k = 0; k < plane.getSize()/GROUPS; k++){
                line.add(new Passenger(front_pointer+k));
            }

        }

        if (DEBUG) System.out.println("Southwest Airline:\n" + line);

        return line;
    }
}
