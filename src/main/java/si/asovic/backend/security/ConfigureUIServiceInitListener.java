package si.asovic.backend.security;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import si.asovic.ui.LoginView;

public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {
    @Override
    public void serviceInit(ServiceInitEvent serviceInitEvent) {
        serviceInitEvent.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    private void authenticateNavigation(BeforeEnterEvent beforeEnterEvent) {
        if (!LoginView.class.equals(beforeEnterEvent.getNavigationTarget())
                && !SecurityUtils.isUserLoggedIn()) {
            beforeEnterEvent.rerouteTo(LoginView.class);
        }
    }
}
