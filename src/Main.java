import java.util.ArrayList;
import java.util.List;

class Order {
    private int orderId;
    private List<OrderItem> items = new ArrayList<>();
    private IPaymentProcessor paymentProcessor;
    private IDeliveryService deliveryService;
    private double totalPrice;

    public Order(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setPaymentProcessor(IPaymentProcessor paymentProcessor) {
        this.paymentProcessor = paymentProcessor;
    }

    public void setDeliveryService(IDeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotalPrice();
    }

    private void calculateTotalPrice() {
        totalPrice = items.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
    }

    public IPaymentProcessor getPaymentProcessor() {
        return paymentProcessor;
    }

    public IDeliveryService getDeliveryService() {
        return deliveryService;
    }
}

class OrderItem {
    private String productName;
    private double price;
    private int quantity;

    public OrderItem(String productName, double price, int quantity) {
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }
}

interface IPaymentProcessor {
    boolean processPayment(Order order);
}

class CreditCardPaymentProcessor implements IPaymentProcessor {
    public boolean processPayment(Order order) {
        System.out.println("Обработка оплаты кредитной картой для заказа " + order.getOrderId() + " на сумму " + order.getTotalPrice());
        return true;
    }
}

class PayPalPaymentProcessor implements IPaymentProcessor {
    public boolean processPayment(Order order) {
        System.out.println("Обработка оплаты PayPal для заказа " + order.getOrderId() + " на сумму " + order.getTotalPrice());
        return true;
    }
}

class BankTransferPaymentProcessor implements IPaymentProcessor {
    public boolean processPayment(Order order) {
        System.out.println("Обработка оплаты банковским переводом для заказа " + order.getOrderId() + " на сумму " + order.getTotalPrice());
        return true;
    }
}

interface IDeliveryService {
    void deliverOrder(Order order);
}

class CourierDeliveryService implements IDeliveryService {
    public void deliverOrder(Order order) {
        System.out.println("Доставка заказа " + order.getOrderId() + " курьером.");
    }
}

class PostDeliveryService implements IDeliveryService {
    public void deliverOrder(Order order) {
        System.out.println("Доставка заказа " + order.getOrderId() + " почтой.");
    }
}

class PickUpPointDeliveryService implements IDeliveryService {
    public void deliverOrder(Order order) {
        System.out.println("Доставка заказа " + order.getOrderId() + " в пункт выдачи.");
    }
}

interface INotificationService {
    void sendNotification(Order order, String message);
}

class EmailNotificationService implements INotificationService {
    public void sendNotification(Order order, String message) {
        System.out.println("Отправка уведомления по email для заказа " + order.getOrderId() + ": " + message);
    }
}

class SmsNotificationService implements INotificationService {
    public void sendNotification(Order order, String message) {
        System.out.println("Отправка SMS-уведомления для заказа " + order.getOrderId() + ": " + message);
    }
}

class DiscountCalculator {
    public double calculateDiscount(Order order) {
        System.out.println("Расчет скидки...");
        return 0.0;
    }
}

public class Main {
    public static void main(String[] args) {
        Order order = new Order(1);
        order.addItem(new OrderItem("Товар A", 10, 2));
        order.addItem(new OrderItem("Товар B", 20, 1));

        order.setPaymentProcessor(new CreditCardPaymentProcessor());
        order.setDeliveryService(new CourierDeliveryService());

        DiscountCalculator discountCalculator = new DiscountCalculator();
        double discount = discountCalculator.calculateDiscount(order);

        if (order.getPaymentProcessor().processPayment(order)) {
            System.out.println("Оплата прошла успешно.");
            order.getDeliveryService().deliverOrder(order);

            INotificationService notification = new EmailNotificationService();
            notification.sendNotification(order, "Ваш заказ " + order.getOrderId() + " был размещен.");
        } else {
            System.out.println("Оплата не прошла.");
        }
    }
}
