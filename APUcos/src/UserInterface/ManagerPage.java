package UserInterface;

import BackEnd.UserFeatures;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PrinterException;

import static javax.swing.BorderFactory.createMatteBorder;

public class ManagerPage extends Frame{

    private JPanel inputPanel;
    private JTextArea textArea;
    private JTextField foodNameTextField;
    private JButton addFoodButton, deleteFoodButton, paymentFeedbackButton, printButton, refreshButton;
    private JComboBox foodComboBox;
    private JComboBox customerComboBox;
    private UserFeatures userFeatures;


    public ManagerPage() {
        userFeatures = new UserFeatures();
        setInputPanelComponents();
        setActionListeners();
    }


    @Override
    public void setSize() {
        this.setSize(700, 550);
    }

    @Override
    public void setCenterPanel() {
        GridLayout gridLayout = new GridLayout(1, 2);
        gridLayout.setHgap(10);
        getCenterPanel().setLayout(gridLayout);

        setInputPanel();

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBackground(Color.WHITE);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        printButton = new JButton("Print");
        printButton.setFont(new Font("Arial", Font.BOLD, 20));
        printButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        printButton.setFocusPainted(false);
        outputPanel.add(printButton, BorderLayout.SOUTH);

        getCenterPanel().add(outputPanel);

    }

    @Override
    public BorderLayout getBorderLayout() {
        return new BorderLayout(40, 40);
    }

    public void setInputPanel() {
        inputPanel = new JPanel();
        inputPanel.setBackground(Color.WHITE);
        inputPanel.setLayout(new GridLayout(12, 1));
        getCenterPanel().add(inputPanel);
    }

    public void setInputPanelComponents() {
        JLabel label = new JLabel("Enter Food Name");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        inputPanel.add(label);

        foodNameTextField = new JTextField();
        foodNameTextField.setFont(new Font("Arial", Font.BOLD, 16));
        foodNameTextField.setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        inputPanel.add(foodNameTextField);


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

        refreshButton = new JButton(" Refresh Page");
        refreshButton.setBorder(null);
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));;
        refreshButton.setFont(new Font("Arial", Font.BOLD, 20));
        refreshButton.setFocusPainted(false);
        inputPanel.add(refreshButton);
    }

    @Override
    public void setActionListeners() {
        addFoodButton.addActionListener(e-> {

            String food = foodNameTextField.getText();

            if (food.equals("")) {
                JOptionPane.showMessageDialog(null, "Please Enter a valid name");
                return;
            }

            if (userFeatures.isFoodAlreadyExists(food)) {
                JOptionPane.showMessageDialog(null, "This Food Already Exists");
                return;
            }

            userFeatures.addFood(food);

            JOptionPane.showMessageDialog(null, "Food " + food + " Added to File Successfully");
            foodNameTextField.setText("");

            this.dispose();
            ManagerPage managerPage = new ManagerPage();
            managerPage.setVisible(true);

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
            JOptionPane.showMessageDialog(null, "Food " + food + " Removed From File Successfully");
            this.dispose();
            ManagerPage managerPage = new ManagerPage();
            managerPage.setVisible(true);
        });

        paymentFeedbackButton.addActionListener(e-> {
            String ID;

            try{
                ID = customerComboBox.getSelectedItem().toString().substring(0, 6);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "No Food to Delete");
                return;
            }

            textArea.setText(userFeatures.getCustomerInformation(ID));
        });

        printButton.addActionListener(e-> {
            try {
                textArea.print();
            } catch (PrinterException ex) {

            }
        });

        refreshButton.addActionListener(e-> {
            this.dispose();
            ManagerPage managerPage = new ManagerPage();
            managerPage.setVisible(true);
        });
    }
}
