package UserInterface;

import BackEnd.UserFeatures;


import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;
import java.lang.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import static javax.swing.BorderFactory.createMatteBorder;

public class ManagerPage extends Frame{
    private JPanel centerPanel, inputPanel, outputPanel;
    private JTextArea textArea,reportArea;

    private JTextField foodNameTextField, priceTextField;
    private JButton addFoodButton, deleteFoodButton, paymentFeedbackButton, printButton, reportButton, backButton;
    private JComboBox foodComboBox;
    private JComboBox customerComboBox;
    private UserFeatures userFeatures;
    private static final DecimalFormat df = new DecimalFormat("0.00");

    public ManagerPage() {
        userFeatures = new UserFeatures();
        setCenterPanel();
        setInputPanelComponents();
        setActionListeners();
    }


    @Override
    public void setSize() {
        this.setSize(1000, 750);
    }

    @Override
    public BorderLayout getBorderLayout() {
        return new BorderLayout(40, 40);
    }

    public void setCenterPanel() {
        centerPanel = new JPanel();
        getContainer().add(centerPanel, BorderLayout.CENTER);

        centerPanel.setBackground(Color.WHITE);

        GridLayout gridLayout = new GridLayout(1, 2);
        gridLayout.setHgap(10);
        centerPanel.setLayout(gridLayout);

        inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(new GridLayout(15, 1));
        centerPanel.add(inputPanel);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBackground(Color.WHITE);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);

        outputPanel.add(scrollPane, BorderLayout.CENTER);
        outputPanel.add(scrollPane);

        printButton = new JButton("Print");
        printButton.setFont(new Font("Arial", Font.BOLD, 20));
        printButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        printButton.setFocusPainted(false);
        outputPanel.add(printButton, BorderLayout.SOUTH);

        centerPanel.add(outputPanel);

    }
    public void setInputPanelComponents() {
        JLabel label = new JLabel("Enter Food Details");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(label);

        foodNameTextField = new JTextField("Name");
        foodNameTextField.setFont(new Font("Arial", Font.BOLD, 16));
        foodNameTextField.setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        inputPanel.add(foodNameTextField);

        priceTextField = new JTextField("Price");
        priceTextField.setFont(new Font("Arial", Font.BOLD, 16));
        priceTextField.setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        inputPanel.add(priceTextField);

        addFoodButton = new JButton("Add Food");
        addFoodButton.setBorder(null);
        addFoodButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addFoodButton.setFont(new Font("Arial", Font.BOLD, 20));
        addFoodButton.setFocusPainted(false);
        inputPanel.add(addFoodButton);

        inputPanel.add(new JLabel());

        label = new JLabel("Select Food");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(label);

        foodComboBox = new JComboBox(userFeatures.getFoodList());
        foodComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(foodComboBox);


        deleteFoodButton = new JButton("Delete Food");
        deleteFoodButton.setBorder(null);
        deleteFoodButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        deleteFoodButton.setFont(new Font("Arial", Font.BOLD, 20));
        deleteFoodButton.setFocusPainted(false);
        inputPanel.add(deleteFoodButton);

        inputPanel.add(new JLabel());

        label = new JLabel("Select Customer");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(label);

        customerComboBox = new JComboBox(userFeatures.getCustomerIDs());
        customerComboBox.setFont(new Font("Arial", Font.BOLD, 16));
        inputPanel.add(customerComboBox);

        paymentFeedbackButton = new JButton(" See Payment and FeedBack");
        paymentFeedbackButton.setBorder(null);
        paymentFeedbackButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        paymentFeedbackButton.setFont(new Font("Arial", Font.BOLD, 20));
        paymentFeedbackButton.setFocusPainted(false);
        inputPanel.add(paymentFeedbackButton);

        inputPanel.add(new JLabel());

        reportButton = new JButton(" Report");
        reportButton.setBorder(null);
        reportButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        reportButton.setFont(new Font("Arial", Font.BOLD, 20));
        reportButton.setFocusPainted(false);
        inputPanel.add(reportButton);

        backButton = new JButton(" Back");
        backButton.setBorder(null);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        backButton.setFont(new Font("Arial", Font.BOLD, 20));
        backButton.setFocusPainted(false);
        inputPanel.add(backButton);
    }

    public void setActionListeners() {
        addFoodButton.addActionListener(e-> {

            String food = foodNameTextField.getText();
            String sprice = priceTextField.getText();

            if (food.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter a valid name");
                return;
            }

            if (sprice.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter a valid price");
                return;
            }

            if (userFeatures.isFoodAlreadyExists(food)) {
                JOptionPane.showMessageDialog(null, "This Food Already Exists");
                return;
            }

            try{
                float fprice = Float.valueOf(sprice);
                BigDecimal value = new BigDecimal(fprice).setScale(2, RoundingMode.HALF_UP);
                userFeatures.addFood(food, String.valueOf(value));
            }catch (NumberFormatException exception){
                JOptionPane.showMessageDialog(null, "Invalid Format for Price!");
            }
            JOptionPane.showMessageDialog(null, "Food " + food + " Added to File Successfully");
            foodNameTextField.setText("");

            this.dispose();
            ManagerPage managerPage = new ManagerPage();
            managerPage.setVisible(true);

        });

        priceTextField.addActionListener(e-> {
            priceTextField.setText("");

        });

        deleteFoodButton.addActionListener(e-> {
            String food;
            try{
                food = foodComboBox.getSelectedItem().toString();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No Food to Delete");
                return;
            }
            userFeatures.removeFood(food);
            JOptionPane.showMessageDialog(null, "Food " + food.split(",")[0] + " Removed From File Successfully");
            this.dispose();
            ManagerPage managerPage = new ManagerPage();
            managerPage.setVisible(true);
        });

        paymentFeedbackButton.addActionListener(e-> {
            String ID;

            try{
                ID = customerComboBox.getSelectedItem().toString().substring(0, 6);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No Customer to See Payment");
                return;
            }
            textArea.setText(userFeatures.getCustomerInformation(ID));
        });

        printButton.addActionListener(e-> {
            try {
                textArea.print();
            } catch (PrinterException ex) {
                JOptionPane.showMessageDialog(null, "Error Printing Report!");
            }
        });

        reportButton.addActionListener(e-> {
            textArea.setText(userFeatures.salesReport());
        });

        backButton.addActionListener(e-> {
            this.dispose();
            LoginPage loginPage = new LoginPage();
            loginPage.setVisible(true);
        });
    }
}
