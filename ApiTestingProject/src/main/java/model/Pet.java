package model;
import java.util.List;


public class Pet {
    public long id;
    public Category category;
    public String name;
    public List<String> photoUrls;
    public List<Tag> tags;
    public String status;



    public static class Category {
        public long id;
        public String name;

        public Category(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class Tag {
        public long id;
        public String name;

        public Tag(long id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public Pet() {
    }

    public Pet(long id, Category category, String name, List<String> photoUrls, List<Tag> tags, String status) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.photoUrls = photoUrls;
        this.tags = tags;
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
