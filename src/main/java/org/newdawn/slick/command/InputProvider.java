package org.newdawn.slick.command;

import org.newdawn.slick.Input;
import org.newdawn.slick.util.InputAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The central provider that maps real device input into abstract commands
 * defined by the developer. Registering a control against an command with this
 * class will cause the provider to produce an event for the command when the
 * input is pressed and released.
 * 
 * @author joverton
 */
class InputProvider {
    /** The commands that have been defined. */
    private final Map<Control, Command> commands;

    /** The list of listeners that may be listening. */
    private final List<InputProviderListener> listeners = new ArrayList<>();

    /** The input context we're responding to. */
    private final Input input;

    /** The command input states. */
    private final Map<Command, CommandState> commandState = new HashMap<>();

    /** True if this provider is actively sending events. */
    private boolean active = true;

    /**
     * Create a new input provider which will provide abstract input descriptions
     * based on the input from the supplied context.
     *
     * @param input
     *            The input from which this provider will receive events
     */
    public InputProvider(Input input) {
        this.input = input;

        input.addListener(new InputListenerImpl());
        commands = new HashMap<>();
    }

    /**
     * Get the list of commands that have been registered with the provider,
     * i.e. the commands that can be issued to the listeners.
     *
     * @return The list of commands (@see Command) that can be issued from this
     *         provider
     */
    public List getUniqueCommands() {
        final List<Command> uniqueCommands = new ArrayList<>();

        commands.values().stream().filter(command -> !uniqueCommands.contains(command)).forEach(uniqueCommands::add);

        return uniqueCommands;
    }

    /**
     * Get a list of the registered controls (@see Control) that can cause a
     * particular command to be invoked.
     *
     * @param command
     *            The command to be invoked
     * @return The list of controls that can cause the command (@see Control)
     */
    List getControlsFor(Command command) {
        List<Control> controlsForCommand = new ArrayList<>();

        for (Map.Entry<Control, Command> controlCommandEntry : commands.entrySet()) {
            Map.Entry entry = (Map.Entry) controlCommandEntry;
            Control key = (Control) entry.getKey();
            Command value = (Command) entry.getValue();

            if (value == command) {
                controlsForCommand.add(key);
            }
        }
        return controlsForCommand;
    }

    /**
     * Indicate whether this provider should be sending events.
     *
     * @param active
     *            True if this provider should be sending events
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Check if this provider should be sending events.
     *
     * @return True if this provider should be sending events
     */
    boolean isActive() {
        return active;
    }

    /**
     * Add a listener to the provider. This listener will be notified of
     * commands detected from the input.
     *
     * @param listener
     *            The listener to be added
     */
    public void addListener(InputProviderListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener from this provider. The listener will no longer be
     * provided with notification of commands performed.
     *
     * @param listener
     *            The listener to be removed
     */
    public void removeListener(InputProviderListener listener) {
        listeners.remove(listener);
    }

    /**
     * Bind an command to a control.
     *
     * @param command
     *            The command to bind to
     * @param control
     *            The control that is pressed/released to represent the command
     */
    public void bindCommand(Control control, Command command) {
        commands.put(control, command);

        if (commandState.get(command) == null) {
            commandState.put(command, new CommandState());
        }
    }

    /**
     * Clear all the controls that have been configured for a given command.
     *
     * @param command The command whose controls should be unbound
     */
    public void clearCommand(Command command) {
        List controls = getControlsFor(command);

        for (Object control : controls) {
            unbindCommand((Control) control);
        }
    }

    /**
     * Unbinds the command associated with this control.
     *
     * @param control
     *            The control to remove
     */
    void unbindCommand(Control control) {
        Command command = commands.remove(control);
        if (command != null) {
            if (!commandState.keySet().contains(command)) {
                commandState.remove(command);
            }
        }
    }

    /**
     * Get the recorded state for a given command.
     *
     * @param command
     *            The command to get the state for
     * @return The given command state
     */
    private CommandState getState(Command command) {
        return commandState.get(command);
    }

    /**
     * Check if the last control event we received related to the given command
     * indicated that a control was down.
     *
     * @param command
     *            The command to check
     * @return True if the last event indicated a button down
     */
    public boolean isCommandControlDown(Command command) {
        return getState(command).isDown();
    }

    /**
     * Check if one of the controls related to the command specified has been
     * pressed since we last called this method.
     *
     * @param command
     *            The command to check
     * @return True if one of the controls has been pressed
     */
    public boolean isCommandControlPressed(Command command) {
        return getState(command).isPressed();
    }

    /**
     * Fire notification to any interested listeners that a control has been
     * pressed indication an particular command.
     *
     * @param command
     *            The command that has been pressed
     */
    void firePressed(Command command) {
        getState(command).down = true;
        getState(command).pressed = true;

        if (!isActive()) {
            return;
        }

        for (InputProviderListener listener : listeners) {
            listener.controlPressed(command);
        }
    }

    /**
     * Fire notification to any interested listeners that a control has been
     * released indication an particular command should be stopped.
     *
     * @param command
     *            The command that has been pressed
     */
    void fireReleased(Command command) {
        getState(command).down = false;

        if (!isActive()) {
            return;
        }

        for (InputProviderListener listener : listeners) {
            listener.controlReleased(command);
        }
    }

    /**
     * A token representing the state of all the controls causing an command to
     * be invoked.
     *
     * @author kevin
     */
    private class CommandState {
        /** True if one of the controls for this command is down. */
        private boolean down;

        /** True if one of the controls for this command is pressed. */
        private boolean pressed;

        /**
         * Check if a control for the command has been pressed since last call.
         *
         * @return True if the command has been pressed
         */
        public boolean isPressed() {
            if (pressed) {
                pressed = false;
                return true;
            }

            return false;
        }

        /**
         * Check if the last event we had indicated the control was pressed.
         *
         * @return True if the control was pressed
         */
        public boolean isDown() {
            return down;
        }
    }

    /**
     * A simple listener to respond to input and look up any required commands.
     *
     * @author kevin
     */
    private class InputListenerImpl extends InputAdapter {
        /**
         * @see org.newdawn.slick.util.InputAdapter#isAcceptingInput()
         */
        @Override
        public boolean isAcceptingInput() {
            return true;
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#keyPressed(int, char)
         */
        @Override
        public void keyPressed(int key, char c) {
            Command command = commands.get(new KeyControl(key));
            if (command != null) {
                firePressed(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#keyReleased(int, char)
         */
        @Override
        public void keyReleased(int key, char c) {
            Command command = commands.get(new KeyControl(key));
            if (command != null) {
                fireReleased(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#mousePressed(int, int, int)
         */
        @Override
        public void mousePressed(int button, int x, int y) {
            Command command = commands.get(new MouseButtonControl(
                    button));
            if (command != null) {
                firePressed(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#mouseReleased(int, int, int)
         */
        @Override
        public void mouseReleased(int button, int x, int y) {
            Command command = commands.get(new MouseButtonControl(
                    button));
            if (command != null) {
                fireReleased(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerLeftPressed(int)
         */
        @Override
        public void controllerLeftPressed(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.LEFT));
            if (command != null) {
                firePressed(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerLeftReleased(int)
         */
        @Override
        public void controllerLeftReleased(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.LEFT));
            if (command != null) {
                fireReleased(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerRightPressed(int)
         */
        @Override
        public void controllerRightPressed(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.RIGHT));
            if (command != null) {
                firePressed(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerRightReleased(int)
         */
        @Override
        public void controllerRightReleased(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.RIGHT));
            if (command != null) {
                fireReleased(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerUpPressed(int)
         */
        @Override
        public void controllerUpPressed(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.UP));
            if (command != null)
                firePressed(command);
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerUpReleased(int)
         */
        @Override
        public void controllerUpReleased(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.UP));
            if (command != null) {
                fireReleased(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerDownPressed(int)
         */
        @Override
        public void controllerDownPressed(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.DOWN));
            if (command != null) {
                firePressed(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerDownReleased(int)
         */
        @Override
        public void controllerDownReleased(int controller) {
            Command command = commands
                    .get(new ControllerDirectionControl(controller,
                            ControllerDirectionControl.DOWN));
            if (command != null) {
                fireReleased(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerButtonPressed(int,
         *      int)
         */
        @Override
        public void controllerButtonPressed(int controller, int button) {
            Command command = commands
                    .get(new ControllerButtonControl(controller, button));
            if (command != null) {
                firePressed(command);
            }
        }

        /**
         * @see org.newdawn.slick.util.InputAdapter#controllerButtonReleased(int,
         *      int)
         */
        @Override
        public void controllerButtonReleased(int controller, int button) {
            Command command = commands
                    .get(new ControllerButtonControl(controller, button));
            if (command != null) {
                fireReleased(command);
            }
        }
    }
}
