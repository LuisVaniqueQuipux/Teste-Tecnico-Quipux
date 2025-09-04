# Quipux Back-end Teste

### Requisitos e Tecnologias:
- Java 21+
- Maven
- Spring boot 3.5.5
- H2 Database


### Clonar a Api REST para sua maquina local:
`git clone https://github.com/LuisVaniqueQuipux/Teste-Tecnico-Quipux.git`

### Rodando aplicação e acessando endpoints:
``http://localhost:8080/<endpoint>`` 

### Endpoints:
POST: ``http://localhost:8080/list`` Cria uma nova lista de Reprodução <br>
GET: ``http://localhost:8080/list`` Busca todas as listas de reprodução <br>
GET: ``http://localhost:8080/list/<listName>`` Busca uma lista por nome <br>
DELETE: ``http://localhost:8080/list/<listName>`` Deleta uma lista por nome <br>

### Testes Unitarios:
Na pasta raiz da aplicação utilize o comando ``mvn test``

### Autenticação e Autorização:
No package  src/main/java encontramos o arquivo DataLoader que cria 
um usuario padrão "admin" com senha "admin" dentro da Classe 
DataLoader temos a implementação de CommandLinerRunner e o seu metodo da interface
"Run" onde implementamos um usuario padrão para fazer a autenticação
e autorização que pode ser alterado no seguinte trecho dentro do metodo run ``user.setUsername("admin");
            user.setPassword(encoder.encode("admin"));
            user.setRole("ROLE_USER");`` 
