package UserInterface;

import javax.swing.*;
import java.awt.*;

/**
 * This is the parent class Frame
 * for all UserInterface classes.
 * This class is abstract because it
 * has some abstract methods and the
 * body of these abstract methods will
 * be in the child class.
 *
 * Inheritance and Abstraction OOP concepts
 * are used Here.
 */
public abstract class Frame extends JFrame {

    private Container container;
    private Panel centerPanel;

    /**
     * Frame Constructor sets
     * all necessary components.
     */
    public Frame() {
        setFrame();
        setContainer();
    }

    /**
     * This method sets the
     * basic features of a JFrame.
     */
    public void setFrame() {
        this.setTitle("APU Cafeteria Ordering System");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize();
        this.setLocationRelativeTo(null);
    }

    /**
     * This is an abstract method
     * that sets the size of the JFrame.
     */
    public abstract void setSize();

    /**
     * This method sets the container
     * to add various components.
     */
    public void setContainer() {
        container = this.getContentPane();
        container.setBackground(Color.WHITE);
        container.setLayout(getBorderLayout());

        container.add(new JLabel(), BorderLayout.EAST);
        container.add(new JLabel(), BorderLayout.WEST);
        container.add(new JLabel(), BorderLayout.NORTH);
        container.add(new JLabel(), BorderLayout.SOUTH);

        centerPanel = new Panel();
        setCenterPanel();
        container.add(centerPanel, BorderLayout.CENTER);

    }

    /**
     * This is an abstract method
     * that sets the centerPanel
     * of the JFrame.
     */
    public abstract void setCenterPanel();

    /**
     * This method gets the container.
     * @return Container
     */
    public Container getContainer() {
        return container;
    }

    /**
     * This method gets the center panel.
     * @return Center Panel
     */
    public Panel getCenterPanel() {
        return centerPanel;
    }

    /**
     * This is an abstract method
     * that gets the border layout
     * with required vertical and
     * horizontal gap.
     * @return BorderLayout
     */
    public abstract BorderLayout getBorderLayout();

    public abstract void setActionListeners();
}
