import org.schmivits.airball.airdata.FlightData;
import org.schmivits.airball.display.AirballAnalog;
import org.schmivits.airball.display.AirballPFD;
import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.Widget;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;

public class TestAirballs {
    public static void main(String[] argv) {
        final JFrame f = new JFrame();
        FlightData flightData = new FakeFlightData();
        final Widget widget = makeWidget(flightData);
        f.setSize(800, 1200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new GridLayout(1, 1));
        f.getContentPane().add(new JPanel() {
            protected void paintComponent(Graphics g) {
                widget.moveTo(0, 0);
                Graphics2D g2 = (Graphics2D) g;
                /*
                g2.setRenderingHints(new RenderingHints(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
                g2.setRenderingHints(new RenderingHints(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON));
                */
                widget.draw((Graphics2D) g);
            }
        });
        flightData.addUpdateListener(new Runnable() {
            @Override
            public void run() {
                f.getContentPane().paint(f.getContentPane().getGraphics());
            }
        });
        f.setVisible(true);
    }

    private static Widget makeWidget(FlightData flightData) {
        Container c = new Container();
        c.setClip(false);

        {
            Widget aa = new AirballAnalog(flightData, 300f, 400f);
            aa.moveTo(0f, 0f);
            c.getChildren().add(aa);
        }

        {
            Widget ap = new AirballPFD(flightData, 640f, 480f);
            ap.moveTo(0f, 400f);
            c.getChildren().add(ap);
        }

        return c;
    }
}