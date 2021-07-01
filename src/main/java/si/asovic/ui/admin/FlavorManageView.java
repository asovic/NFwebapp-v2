package si.asovic.ui.admin;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import si.asovic.RootLayout;

@CssImport("./styles/shared-styles.css")
@Route(value = "flavorManage", layout = RootLayout.class)
@PageTitle("Flavor Manage | NF")
public class FlavorManageView extends VerticalLayout {

    public FlavorManageView() {

    }
}
