package erro;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(
        name = "newsletter_email_group",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_newsletter_email_group", columnNames = { "newsletter_id", "email_group_id" })
        }
)
public class NewsletterEmailGroupErro {

    @Id
    @SequenceGenerator(name = "newsletter_email_group_id_sequence", sequenceName = "newsletter_email_group_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "newsletter_id")
    @JsonBackReference
    private NewsletterErro newsletter;

    @ManyToOne
    @JoinColumn(name = "email_group_id")
    private EmailGroupErro emailGroup;

}

