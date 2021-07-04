package si.asovic.ui.admin;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.Example;
import si.asovic.RootLayout;
import si.asovic.backend.data.entity.FlavourEntity;
import si.asovic.backend.data.repository.FlavourRepository;

@CssImport("./styles/shared-styles.css")
@Route(value = "flavorManage", layout = RootLayout.class)
@PageTitle("Flavor Manage | NF")
public class FlavorManageView extends VerticalLayout {

    private FlavourRepository flavourRepository;
    Grid<FlavourEntity> flavorGrid = new Grid<>(FlavourEntity.class);
    TextField addFlavor = new TextField("Add new flavor:");
    Button saveFlavor = new Button("Save");

    public FlavorManageView(FlavourRepository flavourRepository) {
        this.flavourRepository = flavourRepository;
        flavorGrid.setColumns("flavour", "enabled");
        flavorGrid.setItems(flavourRepository.findAll());
        add(addFlavor, saveFlavor, flavorGrid);
        saveFlavor.addClickListener(buttonClickEvent -> {
            if (!addFlavor.isEmpty()) {
                addFlavour(addFlavor.getValue());
            } else {
                Notification.show("Please provide flavor.", 2000, Notification.Position.BOTTOM_CENTER);
            }
        });
        flavorGrid.asSingleSelect().addValueChangeListener(selectionEvent -> {
            Button disable = new Button("Disable");
            Button enable = new Button("Enable");
            Dialog dialog = new Dialog();
            dialog.open();
            FlavourEntity entity = selectionEvent.getValue();
            if (entity.isEnabled()) {
                dialog.add(disable);
            } else {
                dialog.add(enable);
            }
            disable.addClickListener(buttonClickEvent -> {
                disableFlavour(entity);
                dialog.close();
            });
            enable.addClickListener(buttonClickEvent -> {
                enableFlavour(entity);
                dialog.close();
            });
        });
    }

    private void disableFlavour(FlavourEntity entity) {
        entity.setEnabled(false);
        flavourRepository.save(entity);
        Notification.show("Flavor " + entity.getFlavour() + " disabled.", 2000, Notification.Position.BOTTOM_CENTER);
    }

    private void enableFlavour(FlavourEntity entity) {
        entity.setEnabled(true);
        flavourRepository.save(entity);
        Notification.show("Flavor " + entity.getFlavour() + " enabled.", 2000, Notification.Position.BOTTOM_CENTER);
    }

    private void addFlavour(String flavour) {
        FlavourEntity entity = new FlavourEntity();
        entity.setFlavour(flavour);
        if (!flavourRepository.exists(Example.of(entity))) {
            flavourRepository.save(entity);
            Notification.show("Flavor " + flavour + " added.", 2000, Notification.Position.BOTTOM_CENTER);
        } else {
            Notification.show("ERROR Flavor " + flavour + " already exists.", 2000, Notification.Position.BOTTOM_CENTER);
        }
    }
}
