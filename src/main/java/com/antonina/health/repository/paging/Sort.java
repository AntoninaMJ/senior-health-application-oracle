package com.antonina.health.repository.paging;

public class Sort {

    private final Direction direction;
    private final String property;

    private Sort(Direction direction, String property) {
        this.direction = direction;
        this.property = property;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getProperty() {
        return property;
    }

    public static Sort by(Direction direction, String property) {
        return new Sort(direction, property);
    }

    public enum Direction {

        ASC, DESC;

        public static Direction fromString(String value) {
            return Direction.valueOf(value.toUpperCase());
        }
    }
}
