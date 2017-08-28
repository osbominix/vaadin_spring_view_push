package edu.vaadin.example;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.annotation.SpringViewDisplay;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import edu.vaadin.example.view.UIScopedView;

/**
 * Created by yl on 28.08.17.
 */
@Theme("valo")
@SpringUI
@SpringViewDisplay
@Push
public class MyUI extends UI implements ViewDisplay {

    private Panel springViewDisplay;

    private UIScopedView uiScopedView;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        setContent(root);

        /*final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton("View Scoped View",
                UIScopedView.VIEW_NAME));
        root.addComponent(navigationBar);*/

        springViewDisplay = new Panel();
        springViewDisplay.setSizeFull();
        root.addComponent(springViewDisplay);
        root.setExpandRatio(springViewDisplay, 1.0f);

        getUI().getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                return true;
            }

            public void afterViewChange(ViewChangeEvent event) {
                View currentView = getUI().getNavigator().getCurrentView();
                if (currentView instanceof UIScopedView) {

                    uiScopedView = (UIScopedView) currentView;
                    uiScopedView.getStartBtn().addClickListener(btnEvent->{
                        new sliderThread().start();
                    });
                    uiScopedView.getSlider().addValueChangeListener(labelEvent->{
                        new labelThread().start();
                    });
                }
            }
        });

    }



    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        button.addClickListener(event -> getUI().getNavigator().navigateTo(viewName));
        return button;
    }

    @Override
    public void showView(View view) {
        springViewDisplay.setContent((Component) view);
    }


    class sliderThread extends Thread {
        @Override
        public void run() {
            try {

                uiScopedView.getUI().access(new Runnable() {
                    @Override
                    public void run() {
                        uiScopedView.getStartBtn().setEnabled(false);
                    }
                });

                while(uiScopedView.getSlider().getValue() < uiScopedView.getSlider().getMax()) {
                    Thread.sleep(500);
                    uiScopedView.getUI().access(new Runnable() {
                        @Override
                        public void run() {
                            uiScopedView.getSlider().setValue(uiScopedView.getSlider().getValue() + 1.0);
                        }
                    });
                }


                uiScopedView.getUI().access(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        uiScopedView.getSlider().setValue(uiScopedView.getSlider().getMin());
                    }
                });


                uiScopedView.getUI().access(new Runnable() {
                    @Override
                    public void run() {
                        uiScopedView.getStartBtn().setEnabled(true);
                    }
                });

            } catch (InterruptedException e) {
                e.printStackTrace();
                //log exception if needed.
            }
        }
    }

    class labelThread extends Thread {
        @Override
        public void run() {
            uiScopedView.getUI().access(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    uiScopedView.getPushLabel().setValue(uiScopedView.getSlider().getValue().toString());
                }
            });
        }
    }
}
