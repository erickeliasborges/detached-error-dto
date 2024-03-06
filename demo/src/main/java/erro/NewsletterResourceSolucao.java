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

@Path("solucao")
@RequestScoped
public class NewsletterResourceSolucao {

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
            NewsletterErro detachedEntity = getDetachedEntityFromDb(dto.getId());
            newsletterMapper.copyDtoToEntity(dto, detachedEntity);
            return detachedEntity;
        }
    }

    /**
     * Cria uma nova instância da entidade e passa todos os campos da
     * entidade encontrada no banco para a nova instância,
     * isso é feito para forçar o get de todos os campos na entidade do banco, inicializando as propriedades LAZY.
     * Tentei utilizar somente o detach do EntityManager na própria entidade do banco após converter,
     * para não precisar criar uma nova instância, porém assim,
     * se tiver propriedades LAZY que não foram carregadas antes de chamar o detach,
     * irá ocorrer o erro 'failed to lazily initialize a collection of role' ao chamar o get dessas propriedades.
     * @param id
     * @return
     */
    private NewsletterErro getDetachedEntityFromDb(Long id) {
        NewsletterErro databaseEntity = entityManager.find(NewsletterErro.class, id);
        NewsletterErro entityDetached = new NewsletterErro();
        newsletterMapper.copyEntityToEntity(databaseEntity, entityDetached);
        entityManager.detach(databaseEntity);
        return entityDetached;
    }

}
