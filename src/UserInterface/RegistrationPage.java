package UserInterface;

import BackEnd.UserFeatures;

import javax.swing.*;
import java.awt.*;

import static javax.swing.BorderFactory.createMatteBorder;

/**
 * This is SignUpPage.
 *  Method Overriding concept is used here.
 */
public class RegistrationPage extends Frame {

    // Private Data Members
    private JPanel centerPanel;
    private JTextField nameTextField, mobileNumberTextField, usernameTextField;
    private JPasswordField passwordField;
    private JButton registerButton;
    private JRadioButton managerRadioButton, customerRadioButton;
    private final UserFeatures userFeatures;

    /**
     * RegistrationPage Constructor
     */
    public RegistrationPage() {
        userFeatures = new UserFeatures();
        setCenterPanel();
        setButtons();
        setActionListeners();
    }

    /**
     * This is the body of the abstract method
     * that gets the border layout with required
     * vertical and horizontal gap.
     * @return BorderLayout
     */
    @Override
    public BorderLayout getBorderLayout() {
        return new BorderLayout(40, 60);
    }

    /**
     * This is the body of the abstract method
     * that sets the size of the JFrame.
     */
    @Override
    public void setSize() {
        this.setSize(500, 700);
    }


    /**
     * This method sets the JFrame components.
     */
    public void setCenterPanel() {
        centerPanel = new JPanel();
        getContainer().add(centerPanel, BorderLayout.CENTER);

        GridLayout gridLayout = new GridLayout(13, 1);
        gridLayout.setVgap(5);
        centerPanel.setLayout(gridLayout);


        JLabel label = new JLabel("Registration Page");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 25));
        centerPanel.add(label);

        centerPanel.add(new JLabel());

        label = new JLabel("Name");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(label);

        nameTextField = new JTextField();
        nameTextField.setFont(new Font("Arial", Font.BOLD, 16));
        nameTextField.setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        centerPanel.add(nameTextField);

        label = new JLabel("Mobile Number");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(label);

        mobileNumberTextField = new JTextField();
        mobileNumberTextField .setFont(new Font("Arial", Font.BOLD, 16));
        mobileNumberTextField .setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        centerPanel.add(mobileNumberTextField );

        label = new JLabel("Username");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(label);

        usernameTextField = new JTextField();
        usernameTextField.setFont(new Font("Arial", Font.BOLD, 16));
        usernameTextField.setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        centerPanel.add(usernameTextField);

        label = new JLabel("Password");
        label.setFont(new Font("Arial", Font.BOLD, 18));
        centerPanel.add(label);

        passwordField = new JPasswordField();
        passwordField.setForeground(Color.black);
        passwordField.setFont(new Font("Arial", Font.BOLD, 16));
        passwordField.setBorder(createMatteBorder(1, 1, 2, 1, Color.GREEN));
        centerPanel.add(passwordField);
    }

    /**
     * This method sets the buttons of JFrame.
     */
    public void setButtons() {
        ButtonGroup buttonGroup = new ButtonGroup();
        JPanel radioButtonPanel = new JPanel();
        radioButtonPanel.setBackground(Color.WHITE);

        managerRadioButton = new JRadioButton("Manager");
        managerRadioButton.setBackground(Color.WHITE);
        managerRadioButton.setFocusPainted(false);
        managerRadioButton.setFont(new Font("Arial", Font.BOLD, 18));
        buttonGroup.add(managerRadioButton);

        customerRadioButton = new JRadioButton("Customer");
        customerRadioButton.setBackground(Color.WHITE);
        customerRadioButton.setFocusPainted(false);
        customerRadioButton.setFont(new Font("Arial", Font.BOLD, 18));
        buttonGroup.add(customerRadioButton);

        radioButtonPanel.add(managerRadioButton);
        radioButtonPanel.add(customerRadioButton);

        centerPanel.add(radioButtonPanel);

        centerPanel.add(new JLabel());

        registerButton = new JButton("Register");
        registerButton.setBorder(null);
        registerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        registerButton.setFocusPainted(false);
        registerButton.setFont(new Font("Arial", Font.BOLD, 25));
        centerPanel.add(registerButton);
    }

    public void setActionListeners() {
        registerButton.addActionListener(e-> {
            String name = nameTextField.getText();
            String mobileNumber = mobileNumberTextField.getText();
            String userName = usernameTextField.getText();
            String password = passwordField.getText();

            String type = "";

            if (managerRadioButton.isSelected()) {
                type = "Manager";
            } else {
                type = "Customer";
            }

            if (name.equals("") || mobileNumber.equals("") || userName.equals("") || password.equals("") || (!managerRadioButton.isSelected() && !customerRadioButton.isSelected())) {
                JOptionPane.showMessageDialog(null, "Please Enter All Required Information");
                return;
            }

            if (userFeatures.alreadyContainsUser(userName, type)) {
                return;
            }

            String customerFileName = userFeatures.addUserToFile(name, mobileNumber, userName, password, type);
            this.dispose();

            if (type.equals("Manager")) {
                ManagerPage managerPage = new ManagerPage();
                managerPage.setVisible(true);
            } else {
                CustomerPage customerPage = new CustomerPage("C" + customerFileName + ".txt");
                customerPage.setVisible(true);
            }
        });
    }

}
