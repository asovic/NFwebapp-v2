package si.asovic.ui.user;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import si.asovic.backend.data.entity.BottleEntity;
import si.asovic.backend.data.entity.OrderEntity;
import si.asovic.backend.data.repository.OrderRepository;

public class OrderDetailForm extends VerticalLayout {

    private OrderRepository orderRepository;

    Grid<BottleEntity> grid = new Grid<>(BottleEntity.class);
    Button deleteButton = new Button("Delete");
    Button reorderButton = new Button("Reorder");
    private OrderEntity entity;

    public OrderDetailForm(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        grid.setColumns("aroma", "nic");
        grid.setClassName("order-detail-grid");
        grid.setHeight("auto");
        add(grid, deleteButton, reorderButton);
        reorderButton.addClickListener(buttonClickEvent -> reorder());
        deleteButton.setVisible(false);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteButton.addClickListener(buttonClickEvent -> deleteOrder());
        addClassName("history-order-detail");
    }

    public void setOrder(OrderEntity order) {
        this.entity = order;
        if (order.getStatus() == 0) {
            deleteButton.setVisible(true);
        } else {
            deleteButton.setVisible(false);
        }
        grid.setItems(order.getBottle());
    }

    private void deleteOrder() {
        if (entity.getStatus() == 0) {
            orderRepository.deleteById(entity.getId());
            UI.getCurrent().getPage().reload();
        }
    }

    private void reorder() {
        Notification.show("Feature coming soon...", 2000, Notification.Position.BOTTOM_CENTER);
    }

}
