# cooperativavotacaodev

# Desafio Técnico
## Objetivo
No cooperativismo, cada associado possui um voto e as decisões são tomadas em assembleias, por votação. A partir disso, você precisa criar uma solução back-end para gerenciar essas sessões de votação. 

Essa solução deve ser executada na nuvem e promover as seguintes funcionalidades através de uma API REST: Utilizado requisição JSON

***

1. Associado
* Cadastrar (Método POST) - 
  * localhost:8080/associados 
    * { "nome":"Lucas","cpf":"2"}
* Consultar Todos (Método GET)
  * localhost:8080/associados

***

2. Nova pauta
* Cadastrar (Método POST)
  * localhost:8080/pautas
    * { "id":"3012","descricao":"Pauta"}

* Consultar Todos (Método GET)
  * localhost:8080/pautas


***


3. Abrir uma sessão de votação em uma pauta (a sessão de votação deve ficar aberta por um tempo determinado na chamada de abertura ou 1 minuto por default); 

* Abrir Sessão (Método PUT)
  * localhost:8080/pautas/{Id_Pauta}/sessão
    * { "tempo":1}


***

4. Receber votos dos associados em pautas (os votos são apenas 'Sim'/'Não'. Cada associado é identificado por um id único e pode votar apenas uma vez por pauta); 
* Inserir Voto (Método PUT)
  * localhost:8080/pautas/{IdPauta}/voto
    * { "cpfAssociado":"11111111111","valorVoto":"SIM"}


***

5. Contabilizar os votos e dar o resultado da votação na pauta. 
* Contabilizar Votos (Método PUT)
  * localhost:8080/pautas/{IdPauta}/contabilizarVotos


***


Para fins de exercício, a segurança das interfaces pode ser abstraída e qualquer chamada para as interfaces pode ser considerada como autorizada. A escolha da linguagem, frameworks e bibliotecas é livre (desde que não infrinja direitos de uso). 
É importante que as pautas e os votos sejam persistidos e que não sejam perdidos com o restart da aplicação.

* Foi Utilizado Spring Boot(SpringTool) com banco de dados local H2
* Aplicação inserida no Heroku com base PostgreSQL
* Foi utilizado Biblioteca Lombok
* Necessário troca o parâmetro spring.profiles.active dentro do arquivo  application.properties para o valor “dev” caso teste seja local.
* Link da Aplicação no Heroku (https://cooperativavotacaoapi.herokuapp.com/)
* Utilizar Postman para devidos testes
  * Postman(Teste Local): TestesCooperativaVotacaoLocal.postman_collection.json
  * Postman(Teste Heroku): TesteCooperativaVotacaoHeroku.postman_collection.json
