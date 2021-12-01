package gruppe8.nebula.entities;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.stream.IntStream;

public record TaskEntity(Long id, Long projectId, Long parent, String name, LocalDateTime startDate, LocalDateTime endDate) implements CharSequence {
    @Override
    public int length() {
        return this.toString().length();
    }

    @Override
    public char charAt(int index) {
        return this.toString().charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return this.toString().subSequence(start, end);
    }

    @Override
    public boolean isEmpty() {
        return this.toString().isEmpty();
    }

    @Override
    public IntStream chars() {
        return this.toString().chars();
    }

    @Override
    public IntStream codePoints() {
        return this.toString().codePoints();
    }
}