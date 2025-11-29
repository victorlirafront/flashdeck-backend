==========================================
  POSTMAN COLLECTION - FLASHCARDS API
==========================================

ARQUIVO: Flashcards_API.postman_collection.json

COMO USAR:
----------
1. Abra o Postman
2. Clique em "Import" (canto superior esquerdo)
3. Selecione o arquivo "Flashcards_API.postman_collection.json"
4. Clique em "Import"
5. Pronto! A collection já está configurada com todas as variáveis

VARIÁVEIS INCLUÍDAS:
--------------------
- base_url: http://localhost:8080
- auth_token: Token JWT (preenchido automaticamente após login/registro)
- user_name: Nome do usuário (preenchido automaticamente)

ENDPOINTS DISPONÍVEIS:
----------------------
1. Register User - POST /auth/register
   Registra um novo usuário e retorna token JWT

2. Login - POST /auth/login
   Faz login e retorna token JWT

3. Login - Invalid Credentials
   Testa login com senha incorreta (deve retornar 401)

4. Register - Duplicate Email
   Testa registro com email já existente (deve retornar 409)

5. Login - User Not Found
   Testa login com usuário inexistente (deve retornar 404)

RECURSOS AUTOMÁTICOS:
---------------------
✅ Token JWT é salvo automaticamente após login/registro
✅ Variáveis são atualizadas automaticamente
✅ Console do Postman mostra mensagens de sucesso/erro

EXEMPLO DE USO:
---------------
1. Execute "Register User" primeiro
2. O token será salvo automaticamente
3. Execute "Login" para testar o login
4. Use {{auth_token}} em outras requisições que precisem de autenticação

NOTAS:
------
- Certifique-se de que a aplicação está rodando na porta 8080
- O token JWT tem validade de 2 horas
- Para usar o token, adicione o header: Authorization: Bearer {{auth_token}}

