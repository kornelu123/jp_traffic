import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

public class Car {
    public boolean isStopped;
    private String direction;
    private final static String[] carUrls = {
            "img/car_left.png",
            "img/car_down.png"
    };
    private int carIndex;


    public void setCarIndex(int carIndex) {
        this.carIndex = carIndex;
    }

    final static int[][] respPoints = {
            {155, 0},
            {0,613},
            {555,0}
    };

    public int getOffsetY() {
        return offsetY;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public String getCarUrl() {
        return carUrls[carIndex];
    }

    private static final String[] dir = {
        "LEFT",
        "RIGHT",
        "TOP",
        "BOTTOM"
    };
    final static String[] respDir = {
            "BOTTOM",
            "RIGHT",
            "BOTTOM"
    };
    private int offsetX, offsetY;
    public Car(int ind, String direct){
        offsetX = respPoints[ind][0];
        offsetY = respPoints[ind][1];
        direction = direct;
        setCarIndex(direction);
    }

    private void setCarIndex(String direct){
        switch (direct){
            case "TOP", "BOTTOM":{
                carIndex = 1;
                return;
            }
        }
        carIndex = 0;
    }
    private void moveCar(){
        System.out.println(offsetX + "| " + offsetY );
        if(isStopped){
            return;
        }
        switch (direction){
            case "TOP" -> offsetY--;
            case "LEFT" -> offsetX--;
            case "RIGHT" -> offsetX++;
            case "BOTTOM" -> offsetY++;
        }
    }
    public static void doCarWork(ArrayList<Car> cars, int desValue){
        int count = 0;
        Random rand = new Random();
        if(cars.isEmpty()){
            for(;count < desValue;count++){
                int random = rand.nextInt(3);
                cars.add(new Car(random, respDir[random]));
            }
            return;
        }
        for(Car car : cars){
            if(car.getOffsetY() < 0 || car.getOffsetX() < 0){
                cars.remove(car);
                continue;
            }
            
            if(car.isStopped){
                continue;
            }
            car.moveCar();
            count ++;
        }
        for(;count < desValue;count++){
            int random = rand.nextInt(3);
            cars.add(new Car(random, respDir[random]));
        }
    }
    public void changeDir(String direct){
        direction = direct;
    }
}
