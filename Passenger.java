public class Passenger {
    final boolean DEBUG = false;

    private int seat_index;     // where the passenger needs to be seated on the plane
    private int curr_row_index; // where the passenger currently is on the plane

    private int time_to_sit;    // total time it took to seat the passenger

    private boolean seated;     // true if passenger is seated

    /////////////////////////

    public Passenger(int seat_index){
        this.seat_index     = seat_index;
        this.curr_row_index = 0;

        this.seated         = false;
    }

    /////////////////////////

    public int getSeat(){
        return this.seat_index;
    }

    public int getRow(){
        return this.curr_row_index;
    }

    /////////////////////////

    public void sit(int time_to_sit){
        this.time_to_sit    = time_to_sit;
        this.seated         = true;
        if (DEBUG) System.out.println("Passenger : " + getSeat() + ", time to sit: " + this.time_to_sit);
    }

    /////////////////////////

    public void walk(){
        this.curr_row_index++;
        if (DEBUG) System.out.println("Passenger " + getSeat() + " walking to row " + curr_row_index);
    }

    /////////////////////////

    public void pause(int pause_time){
        // place holder method for doing nothing
        if (DEBUG) System.out.println("Passenger " + getSeat() + " paused for " + (pause_time) + " seconds");
    }
    
    /////////////////////////

    public String toString(){
        String s = "";
        
        s += "Passenger's seat: " + this.seat_index + " is ";
        s += (this.seated) ? "SEATED " : "NOT SEATED ";
        s += "with boarding time " + this.time_to_sit;

        return s;
    }
}
