import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Pedestrian {
    private int offsetX, offsetY;
    public boolean isStopped = false;
    public String url = "img/guy.png";
    private String direction;
    private static int[][] spawnPoints = {
            {100, 0},
            {0, 300},
            {600, 0}
    };

    public void movePedestrian() {
        switch (direction) {
            case "TOP" -> offsetY--;
            case "BOTTOM" -> offsetY++;
            case "RIGHT" -> offsetX++;
            case "LEFT" -> offsetX--;
        }
    }

    public Pedestrian(String dir, int[] spawnpoint) {
        offsetX = spawnpoint[0];
        offsetY = spawnpoint[1];
        direction = dir;
    }

    public int getOffsetX() {
        return offsetX;
    }

    public int getOffsetY() {
        return offsetY;
    }

    public static void doPedestrianWork(ArrayList<Pedestrian> pedestrians, int desValue, ArrayList<Intersection> interList) {
        Random rand = new Random();
        int count = 0;
        if (pedestrians.isEmpty()) {
            for (; count < desValue; count++) {
                int random = rand.nextInt(3);
                pedestrians.add(new Pedestrian(Car.respDir[random], spawnPoints[random]));
            }
        }
        for (Iterator<Pedestrian> it = pedestrians.iterator(); it.hasNext(); ) {
            Pedestrian ped = it.next();
            if (ped.getOffsetX() < 0 || ped.getOffsetX() > 944 || ped.getOffsetY() < 0 || ped.getOffsetY() > 845) {
                it.remove();
                count--;
                continue;
            }
            if (!ped.isStopped) {
                ped.movePedestrian();
            }
            count++;
        }
        for (; count < desValue; count++) {
            int random = rand.nextInt(3);
            pedestrians.add(new Pedestrian(Car.respDir[random], spawnPoints[random]));
        }
    }

    public String getDirection() {
        return direction;
    }
}
