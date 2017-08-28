package edu.vaadin.example.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by yl on 28.08.17.
 */
@SpringView(name = DefaultView.VIEW_NAME)
public class DefaultView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "";
    private Button loginBtn;

    @PostConstruct
    void init() {
        loginBtn = new Button("Login");
        addComponent(loginBtn);

        loginBtn.addClickListener(event->{
            getUI().getNavigator().navigateTo(UIScopedView.VIEW_NAME);
        });
    }

    public Button getLoginBtn() {
        return loginBtn;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }
}