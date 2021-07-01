package si.asovic.ui.admin;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import si.asovic.RootLayout;

@CssImport("./styles/shared-styles.css")
@Route(value = "userManagement", layout = RootLayout.class)
@PageTitle("User Management | NF")
public class UserManageView extends VerticalLayout {

    public UserManageView() {

    }
}
