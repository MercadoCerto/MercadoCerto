# Deploy do MercadoCerto no Railway

## O que foi adaptado no codigo
- `application.properties`: banco e porta agora vem de variaveis de ambiente (com default local), dialeto MariaDB fixo e pool de conexoes limitado.
- Frontend: todas as chamadas usam URL relativa (`/api/...`) em vez de `http://localhost:8080`.
- `Dockerfile`: build com Java 21 e limite de memoria do JVM (evita crash de OOM / exit 137).

## Passos no Railway
1. **Confirme o repositorio e a branch certos** no servico (Settings > Source). Esse foi o erro anterior.
2. **Root Directory = `startup`** (onde estao o Dockerfile e o pom.xml).
3. Adicione o banco: Create > Database > Add MariaDB.
4. No servico da APLICACAO (aba Variables), configure (troque `MariaDB` pelo nome real do servico e confira os nomes exatos das variaveis na aba Variables do banco):
   ```
   SPRING_DATASOURCE_URL=jdbc:mariadb://${{MariaDB.MARIADBHOST}}:${{MariaDB.MARIADBPORT}}/${{MariaDB.MARIADBDATABASE}}
   SPRING_DATASOURCE_USERNAME=${{MariaDB.MARIADBUSER}}
   SPRING_DATASOURCE_PASSWORD=${{MariaDB.MARIADBPASSWORD}}
   ```
   - O nome do banco padrao do Railway costuma ser `railway`, nao `MercadoCerto`. Ajuste a URL ou crie o banco.
   - Confirme que aparece o indicador de "referencia resolvida" ao lado de cada variavel.
5. Importe a estrutura com `mercdadocertocreate.sql` (o ddl-auto=update tambem cria as tabelas pelas entidades).
6. Settings > Networking > Generate Domain.
7. (Opcional) Imagens enviadas somem a cada redeploy. Para manter, monte um Volume em `/app/uploads`.

## Conferir que o deploy pegou o codigo novo
- Aba Deployments: o hash do commit bate com o ultimo commit?
- Build Logs: aparece `FROM maven:3.9-eclipse-temurin-21`? (sinal de build pelo Dockerfile)

## Local continua funcionando
Sem variaveis de ambiente, usa os defaults: localhost:3306/MercadoCerto, root/root, porta 8080.
