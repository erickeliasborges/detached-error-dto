Para simular o erro basta executar na ordem as requests da collection demo/Erro.postman_collection.json.

--PROBLEMA--<br>
O problema que tive foi o erro 'detached entity passed to persist: erro.NewsletterEmailGroupErro' ao fazer uma operação de UPDATE no objeto principal (newsletter), pois na conversão de dto pra entidade feita pela classe gerada pelo mapstruct, os itens das listas do meu objeto principal são convertidos criando uma nova referência do item com o id, ou seja, ao copiar do dto pra entidade, é feito um for pela lista, criado um novo item da entidade e passado valor por valor conforme o item do dto, aí que deu o problema, pois como antes de passar pra conversão do mapper eu fazia um findById, esse meu objeto principal ficava como MANAGED (vinculado a sessão do hibernate), nesse momento as listas também estavam como MANAGED, todos os objetos, mas ao passar pelo mapper, acontecia o que comentei no início, deixando as entidades das listas do meu objeto principal sem vínculo com o hibernate, então qualquer operação que eu fizesse no hibernate (query, flush) sem antes salvar essas entidades, iria ocorrer o erro de detached.
Esse problema ocorre pois nesse projeto estou utilizando o CASCADE na lista de NewsletterEmailGroupErro dentro do objeto principal (Newsletter), e como a classe de conversão cria um novo item passando o id, o hibernate entende que é detached, pois esta informado o id e não está na sessão do hibernate, para os itens sem id funciona normalmente pois aí o hibernate iria criar o registro.

--SOLUÇÃO--<br>
A solução do erro foi mais simples que imaginei, percebi que quando é utilizado a própria entidade para o update (que funciona normalmente), sem ter o DTO, quando é convertido o JSON recebido pra entidade, nenhum estado é definido para a entidade, como se fosse uma nova instância, e o meu problema era que eu fazia o findById da entidade, e acabava ficando como MANAGED, porém as listas não (por isso o erro de detached), e no meu projeto real eu utilizava essa mesma entidade para passar ao service, assim, quando executava qualquer query antes de chamar o merge, o hibernate chamava o flush, e aí dava o erro 'detached entity passed to persist'. Portanto, para resolver bastou eu criar uma nova entidade (sem estado, como se tivesse vindo do front usando a entidade, sem o DTO) e passar os dados da entidade encontrada pelo findById para a nova instância da entidade, somente após isso, converter os dados do DTO para essa nova instância. Criei a classe NewsletterResourceSolucao com a solução do problema, também na collection do postman, criei a request 'Passo 3 - Solução' para testar a correção.
