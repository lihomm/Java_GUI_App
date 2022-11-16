package BackEnd;

import java.util.ArrayList;
import java.util.List;

public class Customer extends User{

    private List<Order> orderList;

    public Customer(String ID, String name, String phoneNumber, String userName, String password) {
        super(ID, name, phoneNumber, userName, password);
        orderList = new ArrayList<>();
    }

    public void addOrder(Order order) {
        orderList.add(order);
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}
