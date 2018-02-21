package pip.lab4.orm;

import org.eclipse.persistence.annotations.PrimaryKey;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "points")
public class Point {
    @Id
    private int id;

    @Column(name = "x")
    private double x;

    @Column(name = "y")
    private double y;

    @Column(name = "radius")
    private double radius;

    public Point(){}

    public Point(double x, double y, double radius){
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadius() {
        return radius;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }
}
