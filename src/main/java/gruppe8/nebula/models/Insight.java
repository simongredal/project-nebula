// Authors Mark Friis Larsen
package gruppe8.nebula.models;

public class Insight {
    private final String title;
    private final String body;

    public Insight(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    @Override
    public String toString() {
        return "Insight{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
