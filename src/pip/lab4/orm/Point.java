package pip.lab4.orm;

import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "resultPoints")
public class Point {
    private static final long serialVersionUID = 1L;
    private int id;
    private double x;
    private double y;
    private double radius;

    public Point(){}
    public Point(double x, double y, double radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
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

    @Column(name = "radius", nullable = false)
    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
