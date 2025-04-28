package org.megasoft.model;

public class ProjectPrice {
    private final int id;
    private final long price;

    public ProjectPrice(int id, long price) {
        this.id = id;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Project ID: " + id + ", Price: $" + price;
    }
}
