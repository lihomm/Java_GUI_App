package UserInterface;

import BackEnd.Order;
import BackEnd.UserFeatures;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static javax.swing.BorderFactory.createMatteBorder;

public class CustomerPage extends Frame{
    private JPanel centerPanel, inputPanel;
    private JComboBox foodItemComboBox, orderComboBox;
    private JButton addFoodButton, removeFoodButton, createOrderButton, paymentFeedbackButton, backButton;
    private JTextField feedBackTextField;
    private JTextArea textArea, priceArea, totalArea;
    private List<String> foods;
    private List<Float> prices;

    private String fileName;
    private UserFeatures userFeatures;

    public CustomerPage(String fileName) {
        this.fileName = fileName;
        userFeatures = new UserFeatures();
        foods = new ArrayList<>();
        prices = new ArrayList<>();
        setCenterPanel();
        setInputPanel();
        setActionListeners();
    }

    @Override
    public void setSize() {
        this.setSize(1000, 1000);
    }

    @Override
    public BorderLayout getBorderLayout() {return new BorderLayout(40, 40);}

    public void setCenterPanel() {
        centerPanel = new JPanel();
        getContainer().add(centerPanel, BorderLayout.CENTER);

        centerPanel.setLayout(new GridLayout(1, 2));

        inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(new GridLayout(15, 1));
        centerPanel.add(inputPanel);

        textArea = new JTextArea("\n\n        Food List\n\n");
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane textScrollPane = new JScrollPane(textArea);

        priceArea = new JTextArea("\n\n       Price\n\n");
        priceArea.setFont(new Font("Arial", Font.BOLD, 16));
        JScrollPane priceScrollPane = new JScrollPane(priceArea);

        centerPanel.add(textScrollPane);
        centerPanel.add(priceScrollPane);
    }

    public void setInputPanel() {

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

        removeFoodButton = new JButton("Remove Food");
        removeFoodButton.setBorder(null);
        removeFoodButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        removeFoodButton.setFont(new Font("Arial", Font.BOLD, 20));
        removeFoodButton.setFocusPainted(false);
        inputPanel.add(removeFoodButton);

        inputPanel.add(new JLabel());

        totalArea = new JTextArea("Total = ");
        totalArea.setBorder(null);
        totalArea.setFont(new Font("Arial", Font.BOLD, 20));
        inputPanel.add(totalArea);

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

        inputPanel.add(new JLabel());

        backButton = new JButton(" Back");
        backButton.setBorder(null);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setFocusPainted(false);
        inputPanel.add(backButton);
    }
    public void setActionListeners() {
        AtomicInteger priceNumber = new AtomicInteger(1);
        AtomicInteger foodNumber = new AtomicInteger(1);
        addFoodButton.addActionListener(e-> {
            String food;
            String foodNameIsolated;
            String priceString;
            float priceValue;

            try{
                food = foodItemComboBox.getSelectedItem().toString();
                foodNameIsolated = food.split(",")[0];
                priceString = food.split("RM")[1];
                priceValue = Float.parseFloat(priceString);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No Food to Add");
                return;
            }

            foods.add(foodNameIsolated);
            prices.add(priceValue);
            textArea.append("        No. " + foodNumber + "  " + foodNameIsolated + "    "+"\n");
            priceArea.append("  RM"+ priceString+"\n");
            totalArea.setText("Total = RM "+getTotal());
            foodNumber.set(foodNumber.addAndGet(1));
            priceNumber.set(priceNumber .addAndGet(1));
        });

        removeFoodButton.addActionListener(e->{
            int index;
            try {
                index = Integer.parseInt(JOptionPane.showInputDialog(null, "Please Enter the Serial No You want to Remove"));
                if (index <= 0 || index > foods.size()) {
                    throw new Exception();
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Please Enter Valid Index");
                return;
            }

            foods.remove(index - 1);
            prices.remove(index -1);

            textArea.setText("\n\n        Food List\n\n");
            priceArea.setText("\n\n    Price\n\n");

            int i = 1;
            for (String food : foods) {
                textArea.append("        No. " + i + "  " + food + "\n");
                i++;
            }

            for (float price : prices){
                priceArea.append("  RM"+ String.format("%.2f", price)+"\n");
            }

            totalArea.setText("Total = RM "+getTotal());

            foodNumber.set(foodNumber.get() - 1);
            priceNumber.set(priceNumber.get()-1);
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
            userFeatures.updateFeedback("order.txt", orderID, feedback);
            JOptionPane.showMessageDialog(null, "Payment and Feedback Updated Successfully");
            this.dispose();
            CustomerPage customerPage = new CustomerPage(fileName);
            customerPage.setVisible(true);
        });

        backButton.addActionListener(e->{
            this.dispose();
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }

    public String getTotal(){
        DecimalFormat df=new DecimalFormat("#.##");
        float sum = 0;
        for(int i = 0; i<prices.size(); i++){
            sum +=  prices.get(i);
            }
        return String.format("%.2f", sum);
    }
}
