public class Coord {
    public int x;
    public int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coord(Coord old) {
        this.x = old.x;
        this.y = old.y;
    }
}
