package UserInterface;

import BackEnd.Order;
import BackEnd.UserFeatures;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.BorderFactory.createMatteBorder;

public class CustomerPage extends Frame{

    private JComboBox foodItemComboBox, orderComboBox;
    private JButton addFoodButton, createOrderButton, paymentFeedbackButton;
    private JPanel inputPanel;
    private JTextField feedBackTextField;
    private List<String> foods;

    private String fileName;
    private UserFeatures userFeatures;

    public CustomerPage(String fileName) {
        this.fileName = fileName;
        userFeatures = new UserFeatures();
        foods = new ArrayList<>();
        setInputPanelComponents();
        setActionListeners();
    }

    @Override
    public void setSize() {
        this.setSize(450, 600);
    }

    @Override
    public void setCenterPanel() {
        getCenterPanel().setLayout(new BorderLayout());
        setInputPanel();
    }

    public void setInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(new GridLayout(11, 1));
        getCenterPanel().add(inputPanel);
    }


    @Override
    public BorderLayout getBorderLayout() {
        return new BorderLayout(40, 40);
    }


    public void setInputPanelComponents() {
        JLabel label = new JLabel("Select Food");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(label);

        foodItemComboBox = new JComboBox(userFeatures.getFoodList());
        foodItemComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(foodItemComboBox);

        addFoodButton = new JButton("Add Food");
        addFoodButton.setBorder(null);
        addFoodButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        addFoodButton.setFont(new Font("Arial", Font.BOLD, 20));
        addFoodButton.setFocusPainted(false);
        inputPanel.add(addFoodButton);

        inputPanel.add(new JLabel());

        createOrderButton = new JButton("Create Order");
        createOrderButton.setBorder(null);
        createOrderButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        createOrderButton.setFont(new Font("Arial", Font.BOLD, 20));
        createOrderButton.setFocusPainted(false);
        inputPanel.add(createOrderButton);

        inputPanel.add(new JLabel());

        label = new JLabel("Select Order");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(label);

        orderComboBox = new JComboBox(userFeatures.getOrderIDs(fileName));
        orderComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(orderComboBox);

        label = new JLabel("Enter Feedback");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(label);

        feedBackTextField = new JTextField();
        feedBackTextField.setFont(new Font("Arial", Font.BOLD, 16));
        feedBackTextField.setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        inputPanel.add(feedBackTextField);

        paymentFeedbackButton = new JButton("Payment and FeedBack");
        paymentFeedbackButton.setBorder(null);
        paymentFeedbackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        paymentFeedbackButton.setFont(new Font("Arial", Font.BOLD, 20));
        paymentFeedbackButton.setFocusPainted(false);
        inputPanel.add(paymentFeedbackButton);
    }

    @Override
    public void setActionListeners() {
        addFoodButton.addActionListener(e-> {
            String food;

            try{
                food = foodItemComboBox.getSelectedItem().toString();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No Food to Add");
                return;
            }

            foods.add(food);
            JOptionPane.showMessageDialog(null, food + " is added to your order.");
        });

        createOrderButton.addActionListener(e-> {
            if (foods.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No Food Added to Create Order");
                return;
            }

            Order order = new Order("O"+UserFeatures.getRandomNumber());
            order.setPaid("Not Paid");
            order.setFeedback("No Feedback");
            order.setFoods(foods);

            userFeatures.addOrderToFile(fileName, order);
            JOptionPane.showMessageDialog(null,  "Order is created successfully.");

            this.dispose();
            CustomerPage customerPage = new CustomerPage(fileName);
            customerPage.setVisible(true);
        });

        paymentFeedbackButton.addActionListener(e-> {
            String orderID;

            try{
                orderID = orderComboBox.getSelectedItem().toString();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No Order to Pay");
                return;
            }

            String feedback = feedBackTextField.getText();

            if (feedback.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter Feedback");
                return;
            }

            userFeatures.updateFeedback(fileName, orderID, feedback);
            JOptionPane.showMessageDialog(null, "Payment and Feedback Updated Successfully");
            this.dispose();
            CustomerPage customerPage = new CustomerPage(fileName);
            customerPage.setVisible(true);
        });
    }
}
