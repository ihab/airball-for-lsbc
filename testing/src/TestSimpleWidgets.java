import org.schmivits.airball.widget.Container;
import org.schmivits.airball.widget.FilledPolygon;
import org.schmivits.airball.widget.Rectangle;
import org.schmivits.airball.widget.StrokedPolygon;
import org.schmivits.airball.widget.Text;
import org.schmivits.airball.widget.Widget;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TestSimpleWidgets {
    public static void main(String[] argv) {
        final JFrame f = new JFrame();
        final Widget widget = makeWidget();
        f.setSize(300, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().setLayout(new GridLayout(1, 1));
        f.getContentPane().add(new JPanel() {
            protected void paintComponent(Graphics g) {
                widget.moveTo(0, 0);
                // widget.sizeTo((float) f.getContentPane().getWidth(), (float) f.getContentPane().getHeight());
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHints(new RenderingHints(
                        RenderingHints.KEY_TEXT_ANTIALIASING,
                        RenderingHints.VALUE_TEXT_ANTIALIAS_ON));
                g2.setRenderingHints(new RenderingHints(
                        RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON));
                widget.draw((Graphics2D) g);
            }
        });
        f.setVisible(true);
    }

    private static void add(Container c, Widget w) {
        w.moveTo(10, c.getHeight() + 10);
        w.sizeTo(c.getWidth() - 20, 50);
        c.sizeTo(c.getWidth(), c.getHeight() + 70);
        c.getChildren().add(w);
    }

    private static Widget makeWidget() {
        Container c = new Container() {
            @Override
            protected void drawContents(Graphics2D g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.YELLOW);
                g2.fill(g2.getClip().getBounds2D());
                super.drawContents(g);
            }
        };
        c.sizeTo(100, 0);
        add(c, new Rectangle(Color.BLUE));
        add(c, new FilledPolygon(
                new float[][] {
                        { 0.00f, 0.00f, },
                        { 0.25f, 0.25f, },
                        { 0.00f, 1.00f, },
                        { 1.00f, 1.00f, },
                        { 0.75f, 0.75f, },
                        { 1.00f, 0.00f, },
                },
                Color.GREEN));
        add(c, new StrokedPolygon(
                new float[][] {
                        { 0.00f, 0.00f, },
                        { 0.25f, 0.25f, },
                        { 0.00f, 1.00f, },
                        { 1.00f, 1.00f, },
                        { 0.75f, 0.75f, },
                        { 1.00f, 0.00f, },
                },
                Color.BLACK,
                3.0f));
        add(c, new Text(
                "HhEeLlOoWwOoRrLlDd",
                50f,
                Color.BLACK,
                Font.decode(Font.MONOSPACED)));
        return c;
    }
}