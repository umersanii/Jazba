package jazba.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    private final StringProperty id;
    private final StringProperty name;
    private final StringProperty membership;

    public User(String id, String name, String membership) {
        this.id = new SimpleStringProperty(id);
        this.name = new SimpleStringProperty(name);
        this.membership = new SimpleStringProperty(membership);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getMembership() {
        return membership.get();
    }

    public StringProperty membershipProperty() {
        return membership;
    }
}

