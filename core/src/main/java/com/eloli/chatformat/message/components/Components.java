package com.eloli.chatformat.message.components;

import java.util.ArrayList;
import java.util.List;

public class Components extends Component {
    public List<Component> components = new ArrayList<>();
    public Components(Component ...components){
        for (Component component : components) {
            this.components.add(component);
        }
    }
}
