package erro;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class NewsletterErroDTO {

    private Long id;

    private String description;

    private Set<NewsletterEmailGroupErroDTO> emailGroups = new HashSet<>();

}
