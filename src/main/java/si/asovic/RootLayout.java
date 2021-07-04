package si.asovic;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import si.asovic.backend.security.SecurityUtils;
import si.asovic.ui.LoginView;
import si.asovic.ui.admin.FlavorManageView;
import si.asovic.ui.admin.OrdersView;
import si.asovic.ui.admin.UnfilledView;
import si.asovic.ui.admin.UserManageView;
import si.asovic.ui.user.HistoryView;
import si.asovic.ui.user.OrderingView;
import si.asovic.ui.user.OrderingViewVertical;

@CssImport("./styles/shared-styles.css")
public class RootLayout extends AppLayout {

    public RootLayout() {
        if (SecurityUtils.isRoleUser()) {
            createUserDrawer();
        } else {
            createAdminDrawer();
        }
        createHeader();
    }

    private void createHeader() {
        H1 logo = new H1("Nektar Fog");
        logo.addClassName("logo");
        Button logoutButton = new Button("Logout");
        logoutButton.addClickListener(buttonClickEvent -> VaadinSession.getCurrent().getSession().invalidate());
        HorizontalLayout header = new HorizontalLayout(new DrawerToggle(), logo, logoutButton);
        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(logo);
        header.setWidth("100%");
        header.addClassName("header");
        addToNavbar(header);
    }

    private void createUserDrawer() {
        RouterLink order;
        if (LoginView.isMobile) {
            order = new RouterLink("Order", OrderingViewVertical.class);
        } else {
            order = new RouterLink("Order", OrderingView.class);
        }
        RouterLink history = new RouterLink("History", HistoryView.class);
        order.setHighlightCondition(HighlightConditions.sameLocation());
        history.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(order, history));
    }

    private void createAdminDrawer() {
        RouterLink flavorManage = new RouterLink("Flavor manager", FlavorManageView.class);
        RouterLink orders = new RouterLink("All orders", OrdersView.class);
        RouterLink unfilled = new RouterLink("Unfilled", UnfilledView.class);
        RouterLink users = new RouterLink("Users", UserManageView.class);
        flavorManage.setHighlightCondition(HighlightConditions.sameLocation());
        orders.setHighlightCondition(HighlightConditions.sameLocation());
        unfilled.setHighlightCondition(HighlightConditions.sameLocation());
        users.setHighlightCondition(HighlightConditions.sameLocation());
        addToDrawer(new VerticalLayout(unfilled, flavorManage, orders, users));
    }
}
