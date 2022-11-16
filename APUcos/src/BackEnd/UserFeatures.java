package BackEnd;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFeatures {
    // Private Constants
    private static final String USERS_FILE_NAME = "users.txt";
    private static final String FOODS_FILE_NAME = "foods.txt";
    private List<User> userList;
    private List<String> foodList;

    public UserFeatures() {
        setUserList();
        setFoodList();
    }

    public void setUserList() {
        userList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(USERS_FILE_NAME));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] elements = line.split(",");
                userList.add(new User(elements[0], elements[1], elements[2], elements[3], elements[4]));
            }

            bufferedReader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Not Found");
        }
    }

    public void setFoodList() {
        foodList = new ArrayList<>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FOODS_FILE_NAME));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                foodList.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Not Found");
        }
    }

    public boolean alreadyContainsUser(String userName, String type) {
        for (User user : userList) {
            if (user.getUserName().equals(userName)
            && user.getID().charAt(0) == type.charAt(0)) {
                JOptionPane.showMessageDialog(null, "This Username and Type Already Exists");
                return true;
            }
        }

        return false;
    }

    public boolean canLogin(String userName, String password, String type) {
        for (User user : userList) {
            if (user.getUserName().equals(userName)
                    && user.getPassword().equals(password)
                    && user.getID().charAt(0) == type.charAt(0)) {
                return true;
            }
        }
        JOptionPane.showMessageDialog(null, "Wrong Username and Password");
        return false;
    }


    public void addUserToFile(String name, String mobileNumber, String userName, String password, String type) {
        String randomNumber = String.valueOf(getRandomNumber());
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(USERS_FILE_NAME, true));

            if (type.equals("Manager")) {
                bufferedWriter.append("M");
            } else {
                bufferedWriter.append("C");
            }
            bufferedWriter
                    .append(randomNumber).append(",")
                    .append(name).append(",")
                    .append(mobileNumber).append(",")
                    .append(userName).append(",")
                    .append(password).append("\n");
            JOptionPane.showMessageDialog(null, type + " " + name + "'s Data Added Successfully to File.");
            bufferedWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Not Found");
        }

        if (type.equals("Customer")) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C" + randomNumber +".txt", true));
                bufferedWriter.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "File Not Found");
            }
        }
    }

    public void addOrderToFile(String fileName, Order order) {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName, true));

            bufferedWriter
                    .append(order.getID()).append(",")
                    .append(order.getPaid()).append(",")
                    .append(order.getFeedback()).append(",");

            int i = 0;
            while (i < order.getFoods().size() -1) {
                bufferedWriter.append(order.getFoods().get(i)).append(",");
                i++;
            }
            bufferedWriter.append(order.getFoods().get(i)).append("\n");
            bufferedWriter.close();
        } catch (IOException e) {

        }
    }

    public Object[] getOrderIDs(String fileName) {
        List<String> orderIDs = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineData = line.split(",");

                if (lineData[1].equals("Not Paid")) {
                    orderIDs.add(lineData[0]);
                }
            }
            bufferedReader.close();
        } catch (IOException e) {

        }

        return orderIDs.toArray();
    }

    public void updateFeedback(String fileName, String orderID, String feedback) {
        List<Order> orderList = new ArrayList<>();

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineData = line.split(",");
                Order order = new Order(lineData[0]);
                order.setPaid(lineData[1]);
                order.setFeedback(lineData[2]);
                List<String> foods = new ArrayList<>();
                for (int i = 3; i < lineData.length; i ++) {
                    foods.add(lineData[i]);
                }
                order.setFoods(foods);
                orderList.add(order);
            }
            bufferedReader.close();
        } catch (IOException e) {

        }


        for (Order order : orderList) {
            if (order.getID().equals(orderID)) {
                order.setPaid("Paid");
                order.setFeedback(feedback);
            }
        }

        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));

            for (Order order : orderList) {
                bufferedWriter
                        .append(order.getID()).append(",")
                        .append(order.getPaid()).append(",")
                        .append(order.getFeedback()).append(",");

                int i = 0;
                while (i < order.getFoods().size() -1) {
                    bufferedWriter.append(order.getFoods().get(i)).append(",");
                    i++;
                }
                bufferedWriter.append(order.getFoods().get(i)).append("\n");
            }
            bufferedWriter.close();
        } catch (IOException e) {

        }
    }

    public void setFoodDataToFile() {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(FOODS_FILE_NAME));

            for (String food : foodList) {
                bufferedWriter.append(food).append("\n");
            }

            bufferedWriter.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "File Not Found");
        }
    }

    public void addFood(String food) {
        foodList.add(food);
        setFoodDataToFile();
    }

    public void removeFood(String food) {
        foodList.remove(food);
        setFoodDataToFile();
    }

    public boolean isFoodAlreadyExists(String userFood) {
        for (String food : foodList) {
            if (food.equalsIgnoreCase(userFood)) {
                return true;
            }
        }
        return false;
    }

    public Object[] getFoodList() {
        return foodList.toArray();
    }

    public String getCustomerFileName(String username, String password, String type) {
        for (User user : userList) {
            if (user.getUserName().equals(username)
                    && user.getPassword().equals(password)
                    && user.getID().charAt(0) == type.charAt(0)) {
                return user.getID() + ".txt";
            }
        }
        return "";
    }


    public Object[] getCustomerIDs() {
        List<String> customerIDs = new ArrayList<>();
        for (User user : userList) {
            if (user.getID().charAt(0) == 'C') {
                customerIDs.add(user.getID() + " " + user.getName());
            }
        }
        return customerIDs.toArray();
    }

    public String getCustomerInformation(String customerID) {
        StringBuilder output = new StringBuilder();
        String fileName = customerID + ".txt";

        for (User user : userList) {
            if (user.getID().equals(customerID)) {
                output.append("     Customer Name : ").append(user.getName()).append("\n");
                output.append("     Customer Mobile Number : ").append(user.getPhoneNumber()).append("\n");
                output.append("     Customer User Name : ").append(user.getUserName()).append("\n");
                output.append("---------------------------------------------------------------\n");
            }
        }

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] lineData = line.split(",");
                output.append("\n     Order ID : ").append(lineData[0]).append("\n");
                output.append("     Payment : ").append(lineData[1]).append("\n");
                output.append("     FeedBack : ").append(lineData[2]).append("\n");

                int item = 1;
                for (int i = 3; i < lineData.length; i ++) {
                    output.append("     Item ").append(item).append(" : ").append(lineData[i]).append("\n");
                    item++;
                }
                output.append("---------------------------------------------------------------\n");
            }
            bufferedReader.close();
        } catch (IOException e) {

        }

        return output.toString();
    }


    /**
     * This method returns random number
     * Between 10000 and 20000
     * @return random Number
     */
    public static int getRandomNumber() {
        return (int) ((Math.random() * (20000 - 10000)) + 10000);
    }
}
