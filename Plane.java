import java.util.Arrays;

public class Plane {
    final boolean DEBUG = false;

    private Passenger[] seats; // number of seats in the plane
    private  int   col_len;    // number of seats in one column (i.e. an aisle seat and a window seat row would have two seats)
    private  int   num_cols;   // number of columns in one row (i.e. num_cols = 2 -> an aisle seat and window seat on each side of the aisle)

    /////////////////////////

    public Plane(int plane_size, int col_len, int num_cols){
        this.seats      = new Passenger[plane_size];
        this.col_len    = col_len;
        this.num_cols   = num_cols;
    }

    /////////////////////////
    
    public int getSize(){
        return this.seats.length;
    }

    public int getRowLen(){
        return this.col_len;
    }

    public int getNumRows(){
        return this.num_cols;
    }

    /////////////////////////

    public int sit(Passenger passenger, int boarding_time){
        int time_to_sit = 1; // time to stow a bag

        for (int i = passenger.getSeat() % this.col_len; i > 0; i--){
            if (this.seats[passenger.getSeat() - i] != null){
                if (DEBUG) System.out.println("Passenger " + passenger.getSeat() + " collision with " + (this.seats[passenger.getSeat() - i].getSeat()));
                time_to_sit += 1; // added time from passengers moving in and out (collision of sitting passengers)
            }
        } 

        this.seats[passenger.getSeat()] = passenger;
        passenger.sit(time_to_sit + boarding_time);

        if (DEBUG) System.out.println("Plane Seat: " + passenger.getSeat() + ", time to sit: " + time_to_sit);

        return time_to_sit;
    }

    /////////////////////////

    public boolean atSeat(Passenger passenger){
        return passenger.getSeat()/(this.col_len * this.num_cols) == passenger.getRow();
    }

    /////////////////////////

    public void clear(){
        Arrays.fill(this.seats, null);
    }

    /////////////////////////

    public String toString(){
        String s = "";

        for (int i = 0; i < this.seats.length; i++){
            if (this.seats[i] != null) s += this.seats[i].toString() + "\n";
        }

        if (s.length() == 0) return "This plane has no passengers.";
        return s;
    }
}
