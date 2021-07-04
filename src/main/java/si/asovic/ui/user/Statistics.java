package si.asovic.ui.user;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import si.asovic.RootLayout;

@CssImport("./styles/shared-styles.css")
@Route(value = "stats", layout = RootLayout.class)
@PageTitle("Statistika | NF")
public class Statistics extends VerticalLayout {
}
