import java.awt.*;
import java.awt.geom.Point2D;
import java.sql.Time;
import java.util.ArrayList;

public class Lights {
    private Point2D coords;
    public static String[] urls = {
            "img/red.png",
            "img/yellow.png",
            "img/green.png"
    };
    public enum light{
        RED,
        YELLOW,
        GREEN,
        NOLAMP
    };
    private light curLight;
    private light lastLight;
    private int urlIndex;
    private Time deadLine;
    public enum lightPos{
        LT,
        RT,
        LB,
        RB
    }
    private lightPos position;
    public Lights(light startLight, lightPos pos){
        reevaluateLights(startLight);
        position = pos;
    }
    public int getOffsetX(){
        switch (position){
            case LB,LT -> {
               return -70;
            }
            case RB,RT -> {
                return 35;
            }
        }
        return -1;
    }
    public int getOffsetY(){
        switch (position){
            case LT,RT -> {
                return -130;
            }
            case LB,RB -> {
                return -10;
            }
        }
        return -1;
    }
    public void reevaluateLights(light type){
        lastLight = curLight;
        curLight = type;
        switch (type) {
            case RED -> urlIndex = 0;
            case GREEN -> urlIndex = 2;
            case YELLOW -> urlIndex = 1;
        }
    }
    public String getUrl() {
        return urls[urlIndex];
    }
    public void doLightsWork(Time timeStamp, ArrayList<Car> carList, ArrayList<Pedestrian> pedList){
        if(deadLine == null){
            deadLine = new Time(timeStamp.getTime() + 10000);
        }
        if(timeStamp.compareTo(deadLine) >= 0){
            switch (curLight){
                case GREEN, RED -> {
                    deadLine.setTime(timeStamp.getTime() + 2000);
                    reevaluateLights(light.YELLOW);
                }
                case YELLOW -> {
                    deadLine.setTime(timeStamp.getTime() + 10000);
                    if(lastLight == light.RED) {
                        reevaluateLights(light.GREEN);
                        Intersection.toggleHorizontal();
                    }
                    if(lastLight == light.GREEN){
                        reevaluateLights(light.RED);
                        Intersection.toggleHorizontal();
                    }
                    for(Car car : carList){
                        car.isStopped = false;
                    }
                    for(Pedestrian ped: pedList){
                        ped.isStopped = false;
                    }
                }
            }
        }
    }

    public light getCurLight() {
        return curLight;
    }
}