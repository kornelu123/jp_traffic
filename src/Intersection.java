import java.sql.Time;
import java.util.ArrayList;

public class Intersection {
    public static int interCount = 4;
    private int lightOffset = 25;
    protected static int[][] interCoords = {
            {172,321},
            {168,604},
            {545,592},
            {547,329}
    };

    private int[] interDirections = {
            //T,R,B,L
            0b0111,
            0b1111,
            0b1101,
            0b1101
    };
    public ArrayList<Lights> lights = new ArrayList<>();
    private static boolean isBlockedHor = false;
    private int[] coords;
    private int directions;
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

    public int getDirections() {
        return directions;
    }
    void doIntersectionWork(Time timeStamp){
        for(Lights li : lights){
            li.doLightsWork(timeStamp);
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

}
