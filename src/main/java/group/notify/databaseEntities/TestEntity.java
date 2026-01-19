package group.notify.databaseEntities;
import jakarta.persistence.*;
@Entity
public class TestEntity {
    @Id
    public int id;
    public String text;
    public TestEntity(){}
}
