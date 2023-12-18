import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Timer;

public class App {
    private final JFrame trafficFrame = new JFrame();
    private final RoadPanel trafficMap = new RoadPanel();
    Timer timer = new Timer();
    public App() throws IOException {
        trafficFrameInit();
        timer.schedule(new Drawer(), 0, 20);
        timer.schedule(new Trafficer(), 0, 10);
    }
    private class Drawer extends java.util.TimerTask{
        @Override
        public void run() {
            if(EventQueue.isDispatchThread()){
                EventQueue.invokeLater(this);
            }else{
                trafficFrame.repaint();
            }
        }
    }
    private class Trafficer extends  java.util.TimerTask{

        @Override
        public void run() {
            for (Intersection in :trafficMap.getInterList()){
                in.doIntersectionWork(new Time(System.currentTimeMillis()),trafficMap.carList,trafficMap.pedestrians);
            }
            Car.doCarWork(trafficMap.getCarList(), trafficMap.getCarDesire(), trafficMap.getInterList());
            Pedestrian.doPedestrianWork(trafficMap.pedestrians, trafficMap.getCarDesire(), trafficMap.getInterList());
        }
    }


    private void trafficFrameInit(){
        trafficFrame.setLayout(new BorderLayout());
        trafficFrame.setTitle("traffic");
        trafficFrame.setSize(RoadPanel.imageSize);
        trafficFrame.setResizable(false);
        trafficFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        trafficFrame.add(trafficMap);
        trafficFrame.setVisible(true);
    }
}