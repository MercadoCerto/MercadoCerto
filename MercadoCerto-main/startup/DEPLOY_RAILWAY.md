# Deploy do MercadoCerto no Railway

O código já está adaptado para a nuvem. O que foi alterado:

- `application.properties` agora lê banco e porta de variáveis de ambiente (com default local).
- O frontend usa URLs relativas (`/api/...`) em vez de `http://localhost:8080`.
- `Dockerfile` com Java 21 e limite de memória do JVM (resolve o crash de OOM / exit 137).
- Pool de conexões do banco limitado para a instância pequena do Railway.

## Passo a passo

### 1. Suba o repositório no GitHub
Com o `Dockerfile` na raiz do projeto (pasta `startup`), o Railway detecta e builda por ele automaticamente.

### 2. Crie o projeto no Railway
- New Project → Deploy from GitHub repo → selecione o repositório.
- Importante: se o `Dockerfile` não está na raiz do repo, configure o **Root Directory** do serviço para a pasta `startup` em Settings.

### 3. Adicione o banco MariaDB
- No projeto: Create → Database → Add MariaDB.

### 4. Configure as variáveis de ambiente do serviço da APLICAÇÃO
Na aba **Variables** do serviço Java, adicione (troque `MariaDB` pelo nome real do serviço de banco):

```
SPRING_DATASOURCE_URL=jdbc:mariadb://${{MariaDB.MARIADBHOST}}:${{MariaDB.MARIADBPORT}}/${{MariaDB.MARIADBDATABASE}}
SPRING_DATASOURCE_USERNAME=${{MariaDB.MARIADBUSER}}
SPRING_DATASOURCE_PASSWORD=${{MariaDB.MARIADBPASSWORD}}
```

> Confira os nomes exatos das variáveis na aba Variables do serviço MariaDB. Use o host **interno** (`*.railway.internal`) sempre que possível.

### 5. Importe o banco
- Use o arquivo `mercdadocertocreate.sql` (na pasta `startup`) para criar a estrutura.
- O Hibernate está com `ddl-auto=update`, então ele também cria/atualiza as tabelas a partir das entidades. O `.sql` é útil para dados iniciais.

### 6. Gere o domínio público
- Serviço da aplicação → Settings → Networking → Generate Domain.

### 7. (Opcional) Imagens enviadas pelos usuários
O sistema de arquivos do Railway é efêmero: arquivos enviados somem a cada redeploy. Para manter as imagens de produto:
- Settings → adicione um **Volume** montado em `/app/uploads`.

## Rodando localmente (continua funcionando)
Sem nenhuma variável de ambiente, o app cai nos defaults: banco em `localhost:3306/MercadoCerto`, usuário `root`/`root`, porta `8080`.
