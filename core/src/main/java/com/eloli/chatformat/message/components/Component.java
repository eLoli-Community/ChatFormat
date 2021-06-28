package com.eloli.chatformat.message.components;

import java.util.Collections;

public class Component {
    public static Component components(Component... components) {
        if (components.length == 0) {
            return new Component();
        }
        if (components.length == 1) {
            return components[0];
        }
        return new Components(components);
    }

    public Component add(Component... components) {
        if (components.length == 0) {
            return this;
        }
        if (this instanceof Components) {
            Collections.addAll(((Components) this).components, components);
            return this;
        } else {
            return new Components(this).add(components);
        }
    }
}
