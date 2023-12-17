import java.sql.Time;
import java.util.ArrayList;
import java.util.Objects;

public class Intersection {
    public static int interCount = 4;
    private int lightOffset = 25;
    protected static int[][] interCoords = {
            {170,325},
            {170,600},
            {545,600},
            {545,325}
    };

    private int[] interDirections = {
            //T,R,B,L
            0b1011,
            0b1111,
            0b1101,
            0b1101
    };
    public ArrayList<Lights> lights = new ArrayList<>();
    private static boolean isBlockedHor = true;
    private final int[] coords;
    private final int directions;
    public Intersection(int interNum){
           coords = interCoords[interNum];
           directions = interDirections[interNum];
           lightsInit();
    }
    private Lights.lightPos getPositionByNum(int i){
        switch (i){
            case 0 -> {
                return Lights.lightPos.RT;
            }
            case 1 -> {
                return Lights.lightPos.RB;
            }
            case 2 -> {
                return Lights.lightPos.LB;
            }
            case 3 -> {
                return Lights.lightPos.LT;
            }
        }
        return null;
    }

    public int[] getCoords() {
        return coords;
    }

    void doIntersectionWork(Time timeStamp, ArrayList<Car> carList){
        for(Lights li : lights){
            li.doLightsWork(timeStamp, carList);
        }

        int[] co = getCoords();
        for(Car car : carList){
            String dir = car.getDirection();
            if(isBlocked()){
                if(Objects.equals(dir, "TOP")){
                    if((co[0] - 60 <= car.getOffsetX() && co[0] + 60 >= car.getOffsetX()) && (co[1] + 120 >= car.getOffsetY() && co[1] + 60 <= car.getOffsetY())){
                        car.isStopped = true;
                    }
                }
                if(Objects.equals(dir, "BOTTOM")){
                    if((co[0] - 60 <= car.getOffsetX() && co[0] + 60 >= car.getOffsetX()) && (co[1] - 120 <= car.getOffsetY() && co[1] - 60 >= car.getOffsetY())){
                        car.isStopped = true;
                    }
                }
                if(Objects.equals(dir, "RIGHT")){
                    if((co[0] - 120 <= car.getOffsetX() && co[0] - 60 >= car.getOffsetX()) && (co[1] - 60 <= car.getOffsetY() && co[1] + 60 >= car.getOffsetY())){

                    }
                }
                if(Objects.equals(dir, "LEFT")){
                    if((co[0] + 120 >= car.getOffsetX() && co[0] + 60 <= car.getOffsetX()) && (co[1] - 60 >= car.getOffsetY() && co[1] + 60 <= car.getOffsetY())){

                    }
                }
            } else {
                if(Objects.equals(dir, "RIGHT")){
                    if((co[0] - 120 <= car.getOffsetX() && co[0] - 60 >= car.getOffsetX()) && (co[1] - 60 <= car.getOffsetY() && co[1] + 60 >= car.getOffsetY())){
                        car.isStopped = true;
                    }
                }
                if(Objects.equals(dir, "LEFT")){
                    if((co[0] + 120 >= car.getOffsetX() && co[0] + 60 <= car.getOffsetX()) && (co[1] - 60 >= car.getOffsetY() && co[1] + 60 <= car.getOffsetY())){
                        car.isStopped = true;
                    }
                }
                if(Objects.equals(dir, "TOP")){
                    if((co[0] + 20 <= car.getOffsetX() && co[0] + 60 >= car.getOffsetX()) && (co[1] - 60 >= car.getOffsetY() && co[1] + 60 <= car.getOffsetY())){
                        car.changeDir("LEFT");
                    }
                }
                if(Objects.equals(dir, "BOTTOM")){
                    if(((co[0] - 60 <= car.getOffsetX()) && co[0] + 60 >= car.getOffsetX()) && (co[1] + 10 <= car.getOffsetY() && co[1] - 10 >= car.getOffsetY())){
                        car.changeDir("LEFT");
                    }
                }
            }
        }
    }
    public static void toggleHorizontal(){
        isBlockedHor = !isBlockedHor;
    }
    public void lightsInit(){
        for(int i=0; i<4; i++){
            if(((1 << i) & directions) == 1 << i){
                if(i %2 == 0) {
                    lights.add(new Lights(Lights.light.GREEN ,getPositionByNum(i)));
                    continue;
                }
                lights.add(new Lights(Lights.light.RED,getPositionByNum(i)));
                continue;
            }
            lights.add(new Lights(Lights.light.NOLAMP,getPositionByNum(i)));
        }
    }

    public boolean isBlocked(){
        return isBlockedHor;
    }

}
