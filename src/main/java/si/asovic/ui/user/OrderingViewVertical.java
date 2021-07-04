package si.asovic.ui.user;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import si.asovic.RootLayout;
import si.asovic.backend.data.entity.BottleEntity;
import si.asovic.backend.data.entity.FlavourEntity;
import si.asovic.backend.data.entity.OrderEntity;
import si.asovic.backend.data.entity.ShoppingCartEntity;
import si.asovic.backend.data.repository.CartRepository;
import si.asovic.backend.data.repository.OrderRepository;
import si.asovic.backend.model.ShoppingCartItem;
import si.asovic.backend.security.SecurityUtils;
import si.asovic.backend.service.FlavourService;

import java.time.LocalDate;
import java.util.*;

@CssImport("./styles/shared-styles.css")
@Route(value = "m_order", layout = RootLayout.class)
@PageTitle("Naročilo | NF")
public class OrderingViewVertical extends VerticalLayout {

    private FlavourService flavourRepository;
    private CartRepository cartRepository;
    private OrderRepository orderRepository;

    private Grid<ShoppingCartItem> cartGrid = new Grid<>(ShoppingCartItem.class);
    private VerticalLayout layoutForButtons = new VerticalLayout();
    private VerticalLayout verticalLayout = new VerticalLayout();
    Button orderButton = new Button("Order");
    Button resetButton = new Button("Reset");
    TextArea opombe = new TextArea("Order notes...");
    TextArea summary = new TextArea("Total bottles: ");
    List<ShoppingCartItem> shoppingCartItems;

    public OrderingViewVertical(FlavourService flavourRepository,
                                CartRepository cartRepository,
                                OrderRepository orderRepository) {
        this.flavourRepository = flavourRepository;
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        addClassName("order");
        setSizeFull();
        verticalLayout.setAlignItems(Alignment.CENTER);
        verticalLayout.add(generateFlavour(), new Span(), generateCart());
        add(verticalLayout);
        configureButtons();
        populateCartFromHistory();
    }

    private VerticalLayout generateFlavour() {
        VerticalLayout orderingLayout = new VerticalLayout();
        List<FlavourEntity> flavourEntities = flavourRepository.getFlavours();
        List<String> flavours = new ArrayList<>();
        flavourEntities.forEach(flavourEntity -> flavours.add(flavourEntity.getFlavour()));
        ComboBox<String> flavourBox = new ComboBox<>();
        flavours.sort(String::compareTo);
        flavourBox.setItems(flavours);
        flavourBox.setAllowCustomValue(false);
        flavourBox.setLabel("Flavour");
        flavourBox.setRequired(true);
        ComboBox<String> nicBox = new ComboBox<>();
        NumberField amountField = new NumberField();
        Button addButton = new Button("Add to cart");
        addButton.addClickListener(buttonClickEvent -> {
            if (flavourBox.isEmpty() || nicBox.isEmpty() || amountField.getValue() == 0) {
                Notification.show("Invalid order, please check your selection.", 5000, Notification.Position.BOTTOM_CENTER);
            } else {
                ShoppingCartItem cartItem = new ShoppingCartItem();
                cartItem.setFlavour(flavourBox.getValue());
                cartItem.setNic(nicBox.getValue());
                cartItem.setAmount(amountField.getValue() == null ? 1 : amountField.getValue().intValue());
                addToCart(cartItem);
            }
        });
        nicBox.setItems(getComboboxValuesNic());
        nicBox.setValue("3mg");
        nicBox.setAllowCustomValue(false);
        nicBox.setLabel("Nicotine (mg/ml)");
        amountField.setHasControls(true);
        amountField.setStep(1);
        amountField.setMax(15);
        amountField.setMin(1);
        amountField.setLabel("# of bottles");
        amountField.setValue(1d);
        orderingLayout.add(flavourBox, nicBox, amountField, addButton);
        orderingLayout.setAlignItems(Alignment.CENTER);
        return orderingLayout;
    }

    private Collection<String> getComboboxValuesNic() {
        return Arrays.asList("0mg", "1mg", "2mg", "3mg", "4mg", "5mg", "6mg", "7mg", "8mg", "9mg", "10mg", "11mg", "12mg", "13mg", "14mg", "15mg");
    }

    private void configureButtons() {
        orderButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        resetButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        resetButton.addClickListener(buttonClickEvent -> clearCart());
        orderButton.addClickListener(buttonClickEvent -> saveOrder());
    }

    private VerticalLayout generateCart() {
        Div buttonsAndOpombeDiv = new Div();
        buttonsAndOpombeDiv.setSizeFull();
        VerticalLayout cartLayout = new VerticalLayout();
        shoppingCartItems = new ArrayList<>();
        opombe.setPlaceholder("");
        opombe.setWidthFull();
        summary.setValue(String.valueOf(shoppingCartItems.size()));
        summary.setReadOnly(true);
        layoutForButtons.setWidth(null);
        layoutForButtons.setAlignItems(Alignment.CENTER);
        layoutForButtons.add(orderButton, resetButton);
        cartGrid.setColumns("flavour", "nic", "amount");
        cartGrid.setWidth("400p");
        cartGrid.addComponentColumn(shoppingCartItem -> {
            Button remove = new Button("X");
            remove.addThemeVariants(ButtonVariant.LUMO_ERROR);
            remove.addClickListener(buttonClickEvent -> {
                shoppingCartItems.remove(shoppingCartItem);
                refreshCart();
            });
            return remove;
        });
        buttonsAndOpombeDiv.add(opombe, layoutForButtons);
        cartLayout.add(cartGrid, summary, buttonsAndOpombeDiv);
        return cartLayout;
    }

    private void addToCart(ShoppingCartItem selectedItems) {
        shoppingCartItems.add(selectedItems);
        refreshCart();
    }

    private void clearCart() {
        shoppingCartItems.clear();
        refreshCart();
    }

    private void refreshCart() {
        cartGrid.setItems(shoppingCartItems);
        final Integer[] totalBottles = {0};
        shoppingCartItems.forEach(shoppingCartItem -> totalBottles[0] = totalBottles[0] + shoppingCartItem.getAmount());
        summary.setValue(String.valueOf(totalBottles[0]));
        cartRepository.deleteAll();
        List<ShoppingCartEntity> shoppingCartEntities = new ArrayList<>();
        String username = SecurityUtils.getUsername();
        shoppingCartItems.forEach(shoppingCartItem -> {
            ShoppingCartEntity entity = new ShoppingCartEntity();
            entity.setFlavour(shoppingCartItem.getFlavour());
            entity.setNicotine(shoppingCartItem.getNic());
            entity.setAmount(shoppingCartItem.getAmount());
            entity.setUsername(username);
            shoppingCartEntities.add(entity);
        });
        cartRepository.saveAll(shoppingCartEntities);
    }

    private void populateCartFromHistory() {
        String username = SecurityUtils.getUsername();
        List<ShoppingCartEntity> cartEntities = cartRepository.findByUsername(username);
        cartEntities.forEach(shoppingCartEntity -> {
            ShoppingCartItem cartItem = new ShoppingCartItem();
            cartItem.setFlavour(shoppingCartEntity.getFlavour());
            cartItem.setNic(shoppingCartEntity.getNicotine());
            cartItem.setAmount(shoppingCartEntity.getAmount());
            shoppingCartItems.add(cartItem);
        });
        cartGrid.setItems(shoppingCartItems);
        summary.setValue(String.valueOf(shoppingCartItems.size()));
    }

    private void saveOrder() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrder_date(LocalDate.now());
        orderEntity.setUsername(SecurityUtils.getUsername());
        orderEntity.setComment(opombe.isEmpty() ? "" : opombe.getValue());
        List<BottleEntity> bottleEntities = new ArrayList<>();
        shoppingCartItems.forEach(shoppingCartItem -> {
            for (int i = 0; i < shoppingCartItem.getAmount(); i++) {
                BottleEntity bottleEntity = new BottleEntity();
                bottleEntity.setAroma(shoppingCartItem.getFlavour());
                bottleEntity.setNic(shoppingCartItem.getNic());
                bottleEntities.add(bottleEntity);
            }
        });
        orderEntity.setBottle(bottleEntities);
        orderRepository.save(orderEntity);
        clearCart();
        Notification.show("Order successful. Thank you.", 5000, Notification.Position.MIDDLE);
    }
}
