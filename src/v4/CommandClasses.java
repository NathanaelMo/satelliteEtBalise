package v4;


import nicellipse.component.NiRectangle;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import static v4.CommandClasses.CommandUtils.parseBeaconPattern;
import static v4.CommandClasses.CommandUtils.parseColor;


public class CommandClasses {

    // Command interface
    interface Command {
        void execute(SimulationContext context);
    }

    // Context class to maintain simulation state
    class SimulationContext {
        private final NiRectangle container;
        private final Map<String, Object> variables = new HashMap<>();

        public SimulationContext(NiRectangle container) {
            this.container = container;
        }

        public void setVariable(String name, Object value) {
            variables.put(name, value);
        }

        public Object getVariable(String name) {
            return variables.get(name);
        }

        public NiRectangle getContainer() {
            return container;
        }
    }

    // Create Satellite Command
    static class CreateSatelliteCommand implements Command {
        private final String variableName;
        private final Map<String, Object> params;

        public CreateSatelliteCommand(String variableName, Map<String, Object> params) {
            this.variableName = variableName;
            this.params = params;
        }

        @Override
        public void execute(SimulationContext context) {
            int height = (int) params.getOrDefault("height", 100);
            int speed = (int) params.getOrDefault("speed", 2);
            Satellite.MovementPattern pattern = CommandUtils.parsePattern((String) params.getOrDefault("pattern", "#horizontal"));

            Satellite satellite = new Satellite(0, height, speed, false, pattern);
            SatelliteView satelliteView = new SatelliteView(satellite);

            if (params.containsKey("color")) {
                satelliteView.setBackground(parseColor((String) params.get("color")));
            }

            context.getContainer().add(satelliteView);
            context.setVariable(variableName, satellite);
            context.setVariable(variableName + "_view", satelliteView);
        }
    }

    // Create Beacon Command
    static class CreateBeaconCommand implements Command {
        private final String variableName;
        private final Map<String, Object> params;

        public CreateBeaconCommand(String variableName, Map<String, Object> params) {
            this.variableName = variableName;
            this.params = params;
        }

        @Override
        public void execute(SimulationContext context) {
            int depth = (int) params.getOrDefault("depth", 350);
            Balise.MovementPattern pattern = parseBeaconPattern((String) params.getOrDefault("pattern", "#horizontal"));

            Balise beacon = new Balise(0, depth, pattern);
            //BaliseView beaconView = new BaliseView(beacon);

            //if (params.containsKey("color")) {
              //  beaconView.setBackground(parseColor((String) params.get("color")));
            //}

            //context.getContainer().add(beaconView);
            //context.setVariable(variableName, beacon);
            //context.setVariable(variableName + "_view", beaconView);
        }
    }

    // Start Command
    static class StartCommand implements Command {
        private final String targetName;

        public StartCommand(String targetName) {
            this.targetName = targetName;
        }

        @Override
        public void execute(SimulationContext context) {
            // Start the appropriate component
            Object component = context.getVariable(targetName);
            if (component instanceof Balise) {
                ((Balise) component).update();
            } else if (component instanceof Satellite) {
                ((Satellite) component).update();
            }
        }
    }

    // Utility methods
    class CommandUtils {
        public static Color parseColor(String colorStr) {
            switch (colorStr.toLowerCase()) {
                case "#red":
                    return Color.RED;
                case "#blue":
                    return Color.BLUE;
                case "#green":
                    return Color.GREEN;
                case "#yellow":
                    return Color.YELLOW;
                default:
                    return Color.RED;
            }
        }

        public static Satellite.MovementPattern parsePattern(String patternStr) {
            switch (patternStr.toLowerCase()) {
                case "#horizontal":
                    return Satellite.MovementPattern.HORIZONTAL;
                case "#sinusoidal":
                    return Satellite.MovementPattern.SINUSOIDAL;
                case "#stationary":
                    return Satellite.MovementPattern.STATIONARY;
                default:
                    return Satellite.MovementPattern.HORIZONTAL;
            }
        }

        public static Balise.MovementPattern parseBeaconPattern(String patternStr) {
            switch (patternStr.toLowerCase()) {
                case "#horizontal":
                    return Balise.MovementPattern.HORIZONTAL;
                case "#sinusoidal":
                    return Balise.MovementPattern.SINUSOIDAL;
                case "#stationary":
                    return Balise.MovementPattern.STATIONARY;
                default:
                    return Balise.MovementPattern.HORIZONTAL;
            }
        }
    }
}