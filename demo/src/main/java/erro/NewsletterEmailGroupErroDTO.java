package erro;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsletterEmailGroupErroDTO {

    private Long id;
    private NewsletterErro newsletter;
    private EmailGroupErro emailGroup;

}
