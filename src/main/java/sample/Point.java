package sample;

import com.sun.javafx.geom.Vec2d;
import toxi.geom.Vec2D;

import java.util.Random;

public class Point {
    Vec2D speed = new Vec2D();//speed
    Vec2D pos = new Vec2D();//position
    double m = 1;//mass

    public Point(){
        Random random = new Random();
        pos.x = 0;//random.nextDouble()*600;
        pos.y = 0;//random.nextDouble()*400;

        speed.x = random.nextFloat();
        speed.y = random.nextFloat();
    }
    public Point(Vec2D pos, Vec2D move){
        this.pos = pos;
        this.speed = move;
    }

    public void move(){
        pos.x += speed.x;
        pos.y += speed.y;
    }

    public Vec2D getSpeed() {
        return speed;
    }

    public void setSpeed(Vec2D speed) {
        this.speed = speed;
    }

    public Vec2D getPos() {
        return pos;
    }

    public void setPos(Vec2D pos) {
        this.pos = pos;
    }
    public double countDistance(Point p){
        return pos.distanceTo(p.getPos());
    }
    public boolean isCollide(Point p){
        return countDistance(p)<10;
    }
    public void changeDirection(Point p1){

        System.out.println("start: " + p1.pos + " " + this.pos + " " + (Math.hypot(p1.speed.x, p1.speed.y) + Math.hypot(this.speed.x, this.speed.y)));
        double R = Math.atan2(p1.pos.y-this.pos.y, p1.pos.x-this.pos.x);
        double A = Math.atan2(this.speed.y, this.speed.x);
        double B = R - A;
        double V1x = this.speed.x*Math.cos(B);
        double V1y = this.speed.y*Math.sin(B);

        double A_2 = Math.atan2(p1.speed.y, p1.speed.x);
        double B_2 = R - A_2;
        double V2x = p1.speed.x*Math.cos(B_2);
        double V2y = p1.speed.y*Math.sin(B_2);

        double V1x_ = V2x;
        double V1y_ = V1y;

        double Y = R - Math.PI/2;
        double X = Math.PI + R;

        double Vxx = Math.cos(X) * V1x_;//
        double Vxy = Math.sin(X) * V1x_;//
        double Vyx = Math.cos(Y) * V1y_;
        double Vyy = Math.sin(Y) * V1y_;

        this.speed = (new Vec2D((float) (Vxx+Vyx), (float) (Vxy+Vyy)));

        double V2x_ = V1x;
        double V2y_ = V2y;

        R = Math.atan2(this.pos.y- p1.pos.y, this.pos.x-p1.pos.x);

        double Y1 = R - Math.PI/2;
        double X1 = Math.PI + R;

        double Vxx1 = Math.cos(X1) * V2x_;//
        double Vxy1 = Math.sin(X1) * V2x_;//
        double Vyx1 = Math.cos(Y1) * V2y_;
        double Vyy1 = Math.sin(Y1) * V2y_;

        p1.speed = (new Vec2D((float) (Vxx1+Vyx1), (float) (Vxy1+Vyy1)));
        System.out.println("end: " + p1.pos + " " + this.pos + " " + (Math.hypot(p1.speed.x, p1.speed.y) + Math.hypot(this.speed.x, this.speed.y)));
    }
}
