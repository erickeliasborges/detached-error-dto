package erro;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "email_group",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_email_group", columnNames = "name")
        }
)
public class EmailGroupErro {

    @Id
    @SequenceGenerator(name = "email_group_id_sequence", sequenceName = "email_group_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "uuid_to_self_registration")
    private String uuidToSelfRegistration;

}
