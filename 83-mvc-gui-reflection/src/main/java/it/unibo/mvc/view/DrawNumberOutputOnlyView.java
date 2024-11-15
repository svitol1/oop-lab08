package it.unibo.mvc.view;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.api.DrawResult;


public class DrawNumberOutputOnlyView implements DrawNumberView{
    private static final String NEW_GAME = ": a new game starts!";

    public DrawNumberOutputOnlyView(){
        //lol
    }

    @Override
    public void setController(DrawNumberController observer) {
        //we don't need to communicate with the controller
    }

    @Override
    public void start() {
        //It prints on stdout so we dont't need to initialize the UI
    }

    @Override
    public void result(DrawResult res) {
        switch (res) {
            case YOURS_HIGH, YOURS_LOW -> {
                System.out.println(res.getDescription());
                return;
            }
            case YOU_WON -> System.out.println(res.getDescription() + NEW_GAME);
            case YOU_LOST -> System.out.println(res.getDescription() + NEW_GAME);
            default -> throw new IllegalStateException("Unknown game state");
        }
    }

}
