# language: pt
  @ResponsavelTeste
  Funcionalidade: Testar as operacoes basicas do responsavel

    Contexto: Cria um responsavel
        Dado o nome "Vânia" e a sua senha digital "123456"

    Cenario: Insere um responsavel no banco de dados
      Dado a insercao do responsavel no banco de dados
      Entao o responsavel deve ser encontrado no banco de dados

    Cenario: Altera informacoes de um responsavel no banco de dados
      Dado o nome "Carlos" e a sua senha digital "ABC123"
      Entao o responsavel "Vânia" deve ser atualizado no banco de dados
