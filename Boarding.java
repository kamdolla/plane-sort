import java.util.*;

public class Boarding{
    final boolean DEBUG = false;

    private Plane plane;            // plane to be boarded
    private Queue<Passenger> line;  // line the passengers come onto the plane

    private Passenger[] boarding_rows;    // records which row Passengers are at on the plane (but not seated)
    private int[]       stowing_rows;     // records how long a Passenger needs to stow their bag on the given column

    private int boarding_time;  // total time passed during boarding
    private int stow_time;      // saves the most previous passenger stow/seating time
  
    /////////////////////////

    public Boarding(Plane plane){
        this.plane  = plane;        
    }

    /////////////////////////

    public int board(Queue<Passenger> line){
        this.line   = line;

        int num_rows = (plane.getSize()/(plane.getRowLen() * plane.getNumRows())) + 1;
        this.boarding_rows    = new Passenger[num_rows];
        this.stowing_rows     = new int[num_rows];

        this.boarding_time  = 0;
        this.stow_time      = 0;

        plane.clear();

        int boarding_time = boardAll();

        // System.out.println("\nTime to board all passengers: " + boarding_time + " seconds.");
        return boarding_time;
    }

    /////////////////////////

    public void boardNext(){
        if (DEBUG) System.out.println("\nBoarding time: " + boarding_time);
        
        if (boarding_rows[0] == null && stowing_rows[0] == 0){
            Passenger next = line.poll();

            if (next != null) {
                if (DEBUG) System.out.println("Passenger " + next.getSeat() + " is now boarding");
                boarding_rows[0] = next;
            }
        }

        else{
            if (DEBUG) System.out.println("No passenger will be boarded for " + stowing_rows[0] + " seconds");
            if (stowing_rows[0] != 0){
                stowing_rows[0]--;
            }
        }
    }

    /////////////////////////

    public boolean isBoardEmpty(){
        for (Passenger p : boarding_rows.clone()){
            if (p != null)
                return false;
        }

        for (int i = 0; i < stowing_rows.length; i++){
            if (stowing_rows[i] != 0)
                return false;
        }

        return true;
    }

    /////////////////////////

    /*
    EDGE CASES

    If Column 0 is stowing, no one can board the plane                  YES
    If a Passenger is stowing, no one behind them can walk forward      YES
    If a Passenger is stowing, passengers in front of them can walk     YES
    Multiple Passengers should be able to sit                           YES
    Multiple Passengers should be able to pause (with different times)  YES

    PLANE INPUTS

    Does the board work with an aisle seat, window seat configuration?  YES
    Does the board work with an aisle, window, and middle seat?         YES
    Does the board work with any amount of aisle/middle/window seats?   YES
    Does the board work with multiple aisles (i.e. num_cols > 2)?       NO

    */

    public int boardAll(){

        while(!line.isEmpty() || !isBoardEmpty()){
            boarding_time++;

            boardNext();

            for (int i = boarding_rows.length-1; i >= 0; i--){
                Passenger passenger = boarding_rows[i];
                
                if (passenger == null){
                    if (stowing_rows[i] != 0)
                        stowing_rows[i]--;
                    continue;
                }

                if (plane.atSeat(passenger)){
                    this.sit(passenger);
                }
                else
                if (boarding_rows[i + 1] == null && stowing_rows[i + 1] == 0){
                    this.walk(passenger);
                }
                else
                {
                    this.pause(passenger);
                }
            }
        }

        return boarding_time;
    }

    /////////////////////////

    public void sit(Passenger passenger){
        stow_time = plane.sit(passenger, boarding_time);

        boarding_rows[passenger.getRow()] = null;
        stowing_rows[passenger.getRow()]  = stow_time;
    }

    /////////////////////////

    public void walk(Passenger passenger){
        boarding_rows[passenger.getRow()] = null;
        
        boarding_rows[passenger.getRow() + 1] = passenger;
        passenger.walk();
    }

    /////////////////////////

    public void pause(Passenger passenger){
        for (int i = passenger.getRow()+1; i < stowing_rows.length; i++)
            if (stowing_rows[i] != 0){
                passenger.pause(stowing_rows[passenger.getRow() + 1]);
                break;
            }
    }


}