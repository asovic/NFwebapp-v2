package si.asovic.ui.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import si.asovic.backend.data.entity.BottleEntity;
import si.asovic.backend.data.entity.OrderEntity;
import si.asovic.backend.data.repository.OrderRepository;

public class UnfinishedOrderDetail extends VerticalLayout {

    private OrderRepository orderRepository;
    Grid<BottleEntity> grid = new Grid<>(BottleEntity.class);
    Button finishButton = new Button("Order finished");
    private OrderEntity entity;

    public UnfinishedOrderDetail(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.orderRepository = orderRepository;
        grid.setColumns("aroma", "nic");
        grid.setClassName("order-detail-grid");
        add(grid, finishButton);
        finishButton.addClickListener(buttonClickEvent -> markAsFinished(entity.getId()));
        finishButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        addClassName("history-order-detail");
    }

    public void setOrder(OrderEntity order) {
        this.entity = order;
        grid.setItems(order.getBottle());
        if (entity.getStatus() == 1) {
            finishButton.setVisible(false);
        }
        expand(grid);
    }

    private void markAsFinished(Long id) {
        orderRepository.markAsFinished(id);
    }
}
