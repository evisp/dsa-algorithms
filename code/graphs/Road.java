public class Road {
    public City from;
    public City to;
    public int distanceKm;
    public boolean isBidirectional;

    public Road(City from, City to, int distanceKm, boolean isBidirectional) {
        this.from = from;
        this.to = to;
        this.distanceKm = distanceKm;
        this.isBidirectional = isBidirectional;
    }

    @Override public String toString() { return "Road to " + to.name + " (" + distanceKm + "km)"; }
}