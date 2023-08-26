package mystical.corps.mist.ToDo;

import java.util.Objects;

public record ToDo(String title, String description, String priority, String category, String startDate, String endDate) {
    public ToDo {
        Objects.requireNonNull(title);
        Objects.requireNonNull(description);
        Objects.requireNonNull(priority);
        Objects.requireNonNull(category);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
    }
}
