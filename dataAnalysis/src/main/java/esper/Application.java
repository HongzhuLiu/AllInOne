package esper;

import com.espertech.esper.client.*;

/**
 * Created by LHZ on 2016/9/29.
 */
public class Application {

    public static void main(String[] args) throws InterruptedException {
        Configuration config = new Configuration();
        config.addEventTypeAutoName("esper");
        EPServiceProvider epService = EPServiceProviderManager.getDefaultProvider(config);
        String epl1 = "select avg(price) from OrderEvent.win:time(10 sec) having price<100";
        String epl2 = "select avg(price) from OrderEvent.win:time_batch(1 sec)";
        EPStatement statement = epService.getEPAdministrator().createEPL(epl1);
        MyListener listener = new MyListener();
        statement.addListener(listener);

        OrderEvent event1 = new OrderEvent("shirt", 100);
        OrderEvent event2 = new OrderEvent("shirt", 50);
        epService.getEPRuntime().sendEvent(event1);
        epService.getEPRuntime().sendEvent(event2);
        Thread.sleep(2000);
    }
}

class MyListener implements UpdateListener {
    public void update(EventBean[] newEvents, EventBean[] oldEvents) {
        EventBean event = newEvents[0];
        System.out.println("avg=" + event.get("avg(price)"));
    }
}

class OrderEvent {
    private String itemName;
    private double price;

    public OrderEvent(String itemName, double price) {
        this.itemName = itemName;
        this.price = price;
    }

    public String getItemName() {
        return itemName;
    }

    public double getPrice() {
        return price;
    }
}