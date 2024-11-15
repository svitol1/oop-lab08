package it.unibo.mvc;

import it.unibo.mvc.api.DrawNumberController;
import it.unibo.mvc.api.DrawNumberView;
import it.unibo.mvc.controller.DrawNumberControllerImpl;
import it.unibo.mvc.model.DrawNumberImpl;

import java.lang.reflect.InvocationTargetException;
import java.util.List;


/**
 * Application entry-point.
 */
public final class LaunchApp {

    private LaunchApp() { }

    /**
     * Runs the application.
     *
     * @param args ignored
     * @throws ClassNotFoundException if the fetches class does not exist
     * @throws NoSuchMethodException if the 0-ary constructor do not exist
     * @throws InvocationTargetException if the constructor throws exceptions
     * @throws InstantiationException if the constructor throws exceptions
     * @throws IllegalAccessException in case of reflection issues
     * @throws IllegalArgumentException in case of reflection issues
     */
    public static void main(final String... args) throws InstantiationException, ClassNotFoundException, NoSuchMethodException {
        final var model = new DrawNumberImpl();
        final DrawNumberController app = new DrawNumberControllerImpl(model);
        final String OUTPUT_VIEW = "OutputOnly";
        final String SWING_VIEW = "Swing";

        try {
            /*
             * Both of the implementation are good; but it's preferred 
             * the second because it doesn't create useless variables. 
             */
            //final Class<? extends Object> classSwing = Class.forName("it.unibo.mvc.DrawNumber" + SWING_VIEW + "View");
            //final Class<? extends Object> classOutputOnly = Class.forName("it.unibo.mvc.DrawNumber" + OUTPUT_VIEW + "View");
            //final List<Class<? extends Object>> classList = List.of(classSwing, classOutputOnly);
            final List<Class<? extends Object>> classList = List.of(
            Class.forName("it.unibo.mvc.view.DrawNumber" + SWING_VIEW + "View"),
            Class.forName("it.unibo.mvc.view.DrawNumber" + OUTPUT_VIEW + "View"));

            for (Class<? extends Object> clask : classList) {
                for(int i = 0; i < 3; i++){
                    app.addView((DrawNumberView) clask.getConstructor().newInstance());
                }
            }
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException(e.getMessage());

        } catch (NoSuchMethodException f) {
            throw new NoSuchMethodException(f.getMessage());

        } catch (IllegalArgumentException | IllegalAccessException g) {
            throw new IllegalArgumentException(g.getMessage());

        } catch (InvocationTargetException | InstantiationException h) {
            throw new InstantiationException(h.getMessage());
        }
    }
}
