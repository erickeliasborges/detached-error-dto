package erro;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

import java.util.Objects;

@Path("teste")
@RequestScoped
public class NewsletterResourceErro {

    @Inject
    EntityManager entityManager;

    @Inject
    NewsletterMapperErro newsletterMapper;

    @POST
    @Path("group")
    @Transactional
    public Response save(EmailGroupErro entity) {
        return Response.status(Response.Status.CREATED).entity(entityManager.merge(entity)).build();
    }

    @POST
    @Path("newsletter")
    @Transactional
    public Response save(NewsletterErroDTO dto) {
        NewsletterErro entity = getEntityFromDtoById(dto);
        return Response.status(Response.Status.CREATED).entity(entityManager.merge(entity)).build();
    }

    @PUT
    @Transactional
    @Path("newsletter")
    public Response update(NewsletterErroDTO dto) {
        NewsletterErro entity = getEntityFromDtoById(dto);
        entityManager.merge(entity);
        return Response.ok().build();
    }

    /**
     * Caso não possuir o id no DTO, somente é feito a conversão, caso existir,
     * buscar o registro do banco para depois realizar a conversão,
     * isso serve para evitar de setar nulo no banco para os campos
     * que existem somente na entidade e não no DTO.
     * @param dto
     * @return
     */
    public NewsletterErro getEntityFromDtoById(NewsletterErroDTO dto) {
        if (Objects.isNull(dto.getId()))
            return newsletterMapper.toEntity(dto);
        else {
            NewsletterErro entityDatabase = entityManager.find(NewsletterErro.class, dto.getId());
            newsletterMapper.copyDtoToEntity(dto, entityDatabase);
            entityManager.flush(); // Força o flush pra simular o erro
            return entityDatabase;
        }
    }

}
