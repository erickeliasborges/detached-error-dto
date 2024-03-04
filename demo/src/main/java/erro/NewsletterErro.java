package erro;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "newsletter")
public class NewsletterErro {

    public static final String URL_TO_UNSUBSCRIBE_KEY = "${URL_TO_UNSUBSCRIBE}";

    @Id
    @SequenceGenerator(name = "newsletter_id_sequence", sequenceName = "newsletter_id_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 2000, nullable = false)
    private String description;

    @OneToMany(mappedBy = "newsletter", cascade = { CascadeType.MERGE, CascadeType.PERSIST }, orphanRemoval = true)
    @JsonManagedReference
    private Set<NewsletterEmailGroupErro> emailGroups = new HashSet<>();

}
