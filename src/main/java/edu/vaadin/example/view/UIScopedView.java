package edu.vaadin.example.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Slider;
import com.vaadin.ui.VerticalLayout;

import javax.annotation.PostConstruct;

/**
 * Created by yl on 28.08.17.
 */
@UIScope
@SpringView(name = UIScopedView.VIEW_NAME)
public class UIScopedView extends VerticalLayout implements View {
    public static final String VIEW_NAME = "ui";


    private Label pushLabel;

    private Slider slider;

    private Button startBtn;


    @PostConstruct
    void init() {
        startBtn = new Button("Start");
        slider = new Slider();
        slider.setMin(0.0);
        slider.setMax(3.0);
        slider.setValue(0.0);
        slider.setWidth("300px");
        pushLabel = new Label("show slider value");
        this.addComponent(startBtn);
        this.addComponent(slider);
        this.addComponent(pushLabel);

    }



    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // This view is constructed in the init() method()
    }

    public Label getPushLabel() {
        return pushLabel;
    }

    public void setPushLabel(Label pushLabel) {
        this.pushLabel = pushLabel;
    }

    public Slider getSlider() {
        return slider;
    }

    public void setSlider(Slider slider) {
        this.slider = slider;
    }

    public Button getStartBtn() {
        return startBtn;
    }

    public void setStartBtn(Button startBtn) {
        this.startBtn = startBtn;
    }


}
