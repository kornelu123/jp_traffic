import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;

public class RoadPanel extends JPanel implements MouseListener {
    private Image bgImage;
    public static Dimension imageSize = new Dimension(944, 849);
    private ArrayList<Intersection> interList = new ArrayList<>();
    public ArrayList<Car> carList = new ArrayList<>();

    public ArrayList<Car> getCarList() {
        return carList;
    }

    private int carDesire = 2;

    public RoadPanel() throws IOException {
        bgImage = ImageIO.read(new File("img/map.png"));
        initIntersections();
        addMouseListener(this);
    }

    public int getCarDesire() {
        return carDesire;
    }

    public void setCarDesire(int carDesire) {
        this.carDesire = carDesire;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(bgImage, 0, 0, this);
        try {
            drawLights(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            drawCars(g);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initIntersections() {
        for (int i = 0; i < 4; i++) {
            interList.add(new Intersection(i));
        }
    }

    private void drawLights(Graphics g) throws IOException {
        for (Intersection in : interList) {
            int[] interPos = in.getCoords();
            for (Lights li : in.lights) {
                if (li.getCurLight() == Lights.light.NOLAMP) continue;
                g.drawImage(ImageIO.read((new File(li.getUrl()))), interPos[0] + li.getOffsetX(), interPos[1] + li.getOffsetY(), null);
            }
        }
    }

    private void drawCars(Graphics g) throws IOException {
        for (Car car : carList) {
            g.drawImage(ImageIO.read((new File(car.getCarUrl()))), car.getOffsetX(), car.getOffsetY(), null);
        }
    }

    public ArrayList<Intersection> getInterList() {
        return interList;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("coords : " + e.getX() + "," + e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}