package gruppe8.nebula.models;

public record Message(Type type, String text) {
    public enum Type {
        INFO,
        SUCCESS,
        WARNING,
        ERROR
    }
}
