package org.megasoft.model;

public class LongestProject {
    private final int id;
    private final int monthCount;

    public LongestProject(int id, int monthCount) {
        this.id = id;
        this.monthCount = monthCount;
    }

    @Override
    public String toString() {
        return "Project ID: " + id + ", Duration (months): " + monthCount;
    }
}
