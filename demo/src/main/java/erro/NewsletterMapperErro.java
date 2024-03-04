package erro;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Optional;

@Mapper(componentModel = "cdi")
public interface NewsletterMapperErro extends GenericMapper<NewsletterErro, NewsletterErroDTO> {

    @AfterMapping
    default void setNewsletter(@MappingTarget NewsletterErro newsletter) {
        /**
         * Seta a newsletter em cada item pois está anotado como @OneToMany e é bidirecional (mappedBy),
         * sem isso o NewsletterEmailGroup ficaria sem o vínculo da newsletter.
         */
        Optional.ofNullable(newsletter.getEmailGroups())
                .ifPresent(emailGroups -> emailGroups.forEach(emailGroup -> emailGroup.setNewsletter(newsletter)));
    }

}
