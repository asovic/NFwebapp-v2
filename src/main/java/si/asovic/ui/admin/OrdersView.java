package si.asovic.ui.admin;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import si.asovic.RootLayout;
import si.asovic.backend.data.entity.OrderEntity;
import si.asovic.backend.data.repository.BottleRepository;
import si.asovic.backend.data.repository.OrderRepository;
import si.asovic.backend.model.HistoryOrder;

import java.util.ArrayList;
import java.util.List;

@CssImport("./styles/shared-styles.css")
@Route(value = "allorders", layout = RootLayout.class)
@PageTitle("Orders | NF")
public class OrdersView extends VerticalLayout {

    private OrderRepository orderRepository;
    private BottleRepository bottleRepository;
    private UnfinishedOrderDetail orderDetailForm;
    private Grid<HistoryOrder> grid = new Grid<>(HistoryOrder.class);

    public OrdersView(OrderRepository orderRepository,
                      BottleRepository bottleRepository) {
        this.orderRepository = orderRepository;
        this.bottleRepository = bottleRepository;
        addClassName("order-history");
        orderDetailForm = new UnfinishedOrderDetail(orderRepository);
        orderDetailForm.setVisible(false);
        setSizeFull();
        Div div = new Div(grid, orderDetailForm);
        div.setSizeFull();
        div.addClassName("content");
        add(div);
        configureGrid();
        populateGrid();
    }

    private void configureGrid() {
        grid.addClassName("history-grid");
        grid.setSizeFull();
        grid.setColumns("date", "bottles", "comment", "status", "username");
        grid.asSingleSelect().addValueChangeListener(gridHistoryOrderComponentValueChangeEvent ->
                viewOrderDetail(gridHistoryOrderComponentValueChangeEvent.getValue()));
    }

    public void populateGrid() {
        List<HistoryOrder> historyOrders = new ArrayList<>();
        List<OrderEntity> orderEntities = orderRepository.findAll();
        orderEntities.forEach(orderEntity -> {
            HistoryOrder historyOrder = new HistoryOrder();
            historyOrder.setDate(orderEntity.getOrder_date());
            historyOrder.setComment(orderEntity.getComment());
            Long orderId = orderEntity.getId();
            historyOrder.setId(orderId);
            historyOrder.setBottles(bottleRepository.findByOrderid(orderId).size());
            historyOrder.setStatus(orderEntity.getStatus() == 0 ? "In progress" : "Completed");
            historyOrder.setUsername(orderEntity.getUsername());
            historyOrders.add(historyOrder);
        });
        grid.setItems(historyOrders);
    }

    private void viewOrderDetail(HistoryOrder value) {
        orderDetailForm.setOrder(orderRepository.getLazyLoaded(value.getId()));
        orderDetailForm.setVisible(true);
    }


}
