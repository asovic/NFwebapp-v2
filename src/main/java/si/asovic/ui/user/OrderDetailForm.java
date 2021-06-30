package si.asovic.ui.user;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import si.asovic.backend.data.entity.BottleEntity;
import si.asovic.backend.data.repository.BottleRepository;

public class OrderDetailForm extends VerticalLayout {

    private BottleRepository bottleRepository;

    Grid<BottleEntity> grid = new Grid<>(BottleEntity.class);
    private Long orderId = Long.valueOf(1);

    public OrderDetailForm(BottleRepository bottleRepository) {
        this.bottleRepository = bottleRepository;
        addClassName("order-detail");
        populateGrid();
    }

    private void populateGrid() {
        grid.setItems(bottleRepository.findByOrderid(orderId));
        grid.setClassName("order-detail-grid");
    }
}
