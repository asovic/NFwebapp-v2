package si.asovic.ui.user;

import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.charts.model.Tooltip;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import si.asovic.RootLayout;
import si.asovic.backend.data.entity.BottleEntity;
import si.asovic.backend.data.entity.OrderEntity;
import si.asovic.backend.data.repository.BottleRepository;
import si.asovic.backend.data.repository.OrderRepository;
import si.asovic.backend.model.HistoryOrder;
import si.asovic.backend.security.SecurityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Route(value = "history", layout = RootLayout.class)
@CssImport("./styles/shared-styles.css")
@PageTitle("Zgodovina | NF")
public class HistoryView extends VerticalLayout {

    private OrderRepository orderRepository;
    private BottleRepository bottleRepository;
    private OrderDetailForm orderDetailForm;
    private Grid<HistoryOrder> grid = new Grid<>(HistoryOrder.class);
    private List<OrderEntity> orderEntities;

    public HistoryView(OrderRepository orderRepository,
                       BottleRepository bottleRepository) {
        this.orderRepository = orderRepository;
        this.bottleRepository = bottleRepository;
        addClassName("order-history");
        orderDetailForm = new OrderDetailForm(orderRepository);
        orderDetailForm.setVisible(false);
        setSizeFull();
        configureGrid();
        populateGrid();
        Div div = new Div(grid, orderDetailForm);
        div.setSizeFull();
        div.addClassName("content");
        Div chartDiv = new Div();
        chartDiv.add(createChart());
        add(div, chartDiv);
    }

    private void configureGrid() {
        grid.addClassName("history-grid");
        grid.setSizeFull();
        grid.setColumns("date", "bottles", "status", "comment");
        grid.asSingleSelect().addValueChangeListener(gridHistoryOrderComponentValueChangeEvent ->
                viewOrderDetail(gridHistoryOrderComponentValueChangeEvent.getValue()));
    }

    public void populateGrid() {
        String username = SecurityUtils.getUsername();
        List<HistoryOrder> historyOrders = new ArrayList<>();
        orderEntities = orderRepository.findByUsername(username);
        orderEntities.forEach(orderEntity -> {
            HistoryOrder historyOrder = new HistoryOrder();
            historyOrder.setDate(orderEntity.getOrder_date());
            historyOrder.setComment(orderEntity.getComment());
            Long orderId = orderEntity.getId();
            historyOrder.setId(orderId);
            historyOrder.setBottles(bottleRepository.findByOrderid(orderId).size());
            historyOrder.setStatus(orderEntity.getStatus() == 0 ? "In progress" : "Completed");
            historyOrders.add(historyOrder);
        });
        grid.setItems(historyOrders);
    }

    private void viewOrderDetail(HistoryOrder value) {
        orderDetailForm.setOrder(orderRepository.getLazyLoaded(value.getId()));
        orderDetailForm.setVisible(true);
    }

    private Chart createChart() {
        Map<String, Integer> stats = new HashMap<>();
        List<BottleEntity> allBottles = new ArrayList<>();
        orderEntities.forEach(orderEntity -> allBottles.addAll(bottleRepository.findByOrderid(orderEntity.getId())));
        allBottles.forEach(bottleEntity -> {
            if (!stats.containsKey(bottleEntity.getAroma())) {
                stats.put(bottleEntity.getAroma(), 1);
            } else {
                stats.computeIfPresent(bottleEntity.getAroma(), (s, integer) -> integer + 1);
            }
        });
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        stats.forEach((s, integer) -> dataSeries.add(new DataSeriesItem(s, integer)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }
}
