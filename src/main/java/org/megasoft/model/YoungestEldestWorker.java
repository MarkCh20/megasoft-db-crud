package org.megasoft.model;

import java.time.LocalDate;

public class YoungestEldestWorker {
    private final String type;
    private final String name;
    private final LocalDate birthday;

    public YoungestEldestWorker(String type, String name, LocalDate birthday) {
        this.type = type;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return type + " - Name: " + name + ", Birthday: " + birthday;
    }
}
