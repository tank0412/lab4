package pip.lab4.orm;

import javax.persistence.*;

@Entity
@NamedQueries(value = {
        @NamedQuery(name = "getPoints", query =
                "SELECT points FROM Point points"),
})
@Table(name = "Point")
public class Point {
    private static final long serialVersionUID = 1L;
    private int id;
    private double x;
    private double y;
    private double r;
    private boolean inside;

    public Point(){}
    public Point(double x, double y, double radius, boolean inside){
        this.x = x;
        this.y = y;
        this.r = radius;
        this.inside = inside;
    }

    @Id @GeneratedValue @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "x", nullable = false)
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    @Column(name = "y", nullable = false)
    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Column(name = "r", nullable = false)
    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    @Column(name = "inside", nullable = false)
    public boolean isInside() {
        return inside;
    }

    public void setInside(boolean inside) {
        this.inside = inside;
    }
}
