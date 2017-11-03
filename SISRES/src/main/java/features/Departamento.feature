# language: pt
Funcionalidade: Realizar as operações básicas do departamento,
  como: inserir, alterar e remover.

  Esquema do Cenario: Inserir Departamento
    Dado os responsaveis cadastrados no sistema
    | nome                 | senha       | 
    | Emilly Cavalcanti    | Kui4iequee  |
    | Sofia Pinto          | oMohf3Eexoh |
    | Nicole Santos        | Main2eeng   | 
    | Victor Ribeiro       | eib9AeNgeaz |
    | Danilo Rocha Cardoso | xogaeK9chie |
    Quando o administrador informar o nome <nome> a senha <sigla> e o responsavel <responsavel>
    Entao deve ser exibida a mensagem "Departamento cadastrado com Sucesso!"

    Exemplos: 
    | nome                                                 | sigla   | responsavel            |
    | "Departamento de gestão de tecnologia da informação" | "DGTI"  | "Sofia Pinto"          |
    | "Diretoria de extensão"                              | "DEX"   | "Nicole Santos"        |
    | "Diretoria de ensino"                                | "DEN"   | "Emilly Cavalcanti"    | 
    | "Assessoria de Comunicação"                          | "ASCOM" | "Danilo Rocha Cardoso" |
    | "Setor de registro escolar"                          | "DASE"  | "Victor Ribeiro"       |
 