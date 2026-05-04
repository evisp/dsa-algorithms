public class City {
	public String name;
    public String type;
    public double lat, lon;   
    
    public City(String name, String type, double lat, double lon) {  // ← updated constructor
        this.name = name;
        this.type = type;
        this.lat  = lat;
        this.lon  = lon;
    }

    @Override public String toString() { return name + " (" + type + ")"; }

    @Override public boolean equals(Object o) {
        if (!(o instanceof City)) return false;
        return this.name.equals(((City) o).name);
    }

    @Override public int hashCode() { return name.hashCode(); }
}