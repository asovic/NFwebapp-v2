package si.asovic.backend.service;

import com.vaadin.flow.component.notification.Notification;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import si.asovic.backend.data.entity.FlavourEntity;
import si.asovic.backend.data.repository.FlavourRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class FlavourService {

    private FlavourRepository flavourRepository;
    List<String> nicValues = Arrays.asList("0mg", "1mg", "2mg", "3mg", "4mg", "5mg", "6mg", "7mg", "8mg", "9mg", "10mg", "11mg", "12mg", "13mg", "14mg", "15mg");
    List<Integer> amounts = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15);

    public FlavourService(FlavourRepository flavourRepository) {
        this.flavourRepository = flavourRepository;
    }

    public List<FlavourEntity> getFlavours() {
        return flavourRepository.findAll();
    }

//    public List<FlavoursGrid> getFlavoursForGrid() {
//        List<FlavourEntity> fromDb = flavourRepository.findAll();
//        List<FlavoursGrid> result = new ArrayList<>();
//        for (FlavourEntity entity : fromDb) {
//            List<NicotineValues> nicotineValues = new ArrayList<>();
//            List<OrderAmount> orderAmounts = new ArrayList<>();
//            FlavoursGrid flavoursGrid = new FlavoursGrid();
//            flavoursGrid.setFlavour(entity.getFlavour());
//            nicValues.forEach(s -> nicotineValues.add(new NicotineValues(s)));
//            amounts.forEach(integer -> orderAmounts.add(new OrderAmount(integer)));
//            flavoursGrid.setNicotineValues(nicotineValues);
//            flavoursGrid.setOrderAmounts(orderAmounts);
//            result.add(flavoursGrid);
//        }
//        return result;
//    }

    public void addNewFlavour(FlavourEntity flavour) {
        flavourRepository.saveAndFlush(flavour);
    }

    public void removeFlavour(FlavourEntity flavour) {
        Optional<FlavourEntity> fromDb = flavourRepository.findOne(Example.of(flavour));
        if (fromDb.isPresent()) {
            flavourRepository.delete(fromDb.get());
            Notification.show("Flavour deleted", 3000, Notification.Position.BOTTOM_CENTER);
        } else {
            Notification.show("Flavour you want to delete does not exist", 3000, Notification.Position.BOTTOM_CENTER);
        }
    }

//    @PostConstruct
    private void aVoid() {
        List<FlavourEntity> flavourEntities = new ArrayList<>();
        for (String flavour : flavoursList) {
            FlavourEntity entity = new FlavourEntity();
            entity.setFlavour(flavour);
            flavourEntities.add(entity);
        }
        flavourRepository.saveAll(flavourEntities);
    }

 List<String> flavoursList = Arrays.asList("Apple (Double)",
        "Apple Pie",
        "Apricot",
        "Banana",
        "Blackberry",
        "Blackcurrant",
        "Blueberry",
        "Bubblegum",
        "Cappuccino",
        "Cheesecake (Graham Crust)",
        "Cinnamon Roll",
        "Citrus Mix",
        "Coconut",
        "Cola",
        "Double Chocolate",
        "Dragonfruit",
        "Energy Drink",
        "Forest Fruit",
        "Glazed Doughnut",
        "Grapefruit",
        "Grapes",
        "Greek Yogurt",
        "Green Tea",
        "Heisenberg",
        "Juicy Peach",
        "Kiwi",
        "Lemon Lime",
        "Lemon Sicily",
        "Vanilla Classic",
        "Mango",
        "Marshmallow",
        "Melon",
        "Orange",
        "Orange Creamsicle",
        "Peanut Butter Cup",
        "Pear",
        "Pear Drops",
        "Pina Colada",
        "Pineapple",
        "Pinkman",
        "Raspberry",
        "Rum",
        "Spearmint",
        "Strawberry",
        "Vanilla Bean Ice Cream",
        "Vanilla Custard",
        "Vanilla Swirl",
        "Wild Cherry",
        "Grape Soda",
        "Tangerine",
        "Juicy Lemon",
        "Sweet Tea",
        "Tiger's Blood",
        "Pink Lemonade",
        "Blue Custard",
        "Red Custard",
        "Black Custard",
        "Apri Custard",
        "Trojanc",
        "Pita",
        "Tropic Mix",
        "Summer Breeze",
        "Cherry Coke",
        "Glass Apple",
        "Bounty",
        "Paradise Punch",
        "Grapple",
        "Melon Grejp",
        "NF Jogurt",
        "Lime Pie",
        "Peach Jogurt",
        "Peachream",
        "Lemonade",
        "Yucatan",
        "Bluepear",
        "Synergy",
        "Peach Ice Tea",
        "Unicorn Milk",
        "Bad Drip",
        "Kanzi");

}