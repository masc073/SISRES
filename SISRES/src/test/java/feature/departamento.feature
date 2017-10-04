# language: pt
  @DepartamentoTeste
  Funcionalidade: Testar as operacoes basicas do departamento
    O sistema deve prover operações básicas do departamento de forma correta.
   
    Contexto: Cria o departamento com seu respectivo responsavel
      Dado que o departamento tem nome de "Recursos Humanos" com sigla "RH" e o responsavel
        | nome            | senhaDigital   |
        | "José Barbosa"  | "123456"       |   
   
    Cenario: Inserir um deparmento
      Dado a insercao do departamento
      Entao o departamento deve existir no banco de dados

