# language: pt
Funcionalidade: Realizar as operações básicas do departamento,
  como: inserir, alterar e remover.

  Esquema do Cenario: Inserir Departamento
    Dado a tela inicial de departamentos aberta
    Quando o administrador informar o nome <nome> a sigla <sigla> e o responsavel <responsavel>
    Entao deve ser exibida a mensagem para o departamento "Departamento cadastrado com Sucesso!"

    Exemplos: 
      | nome                                                 | sigla   | responsavel             |
      | "Departamento de gestao da tecnologia da informacao" | "DGTI"  | "Erick Lima Barros"     |
      | "Diretoria de extensão"                              | "DEX"   | "Livia Costa Carvalho"  |
      | "Diretoria de ensino"                                | "DEN"   | "Alex Santos Sousa"     |
      | "Assessoria de Comunicação"                          | "ASCOM" | "Maria Cunha Pinto"     |
      | "Setor de registro escolar"                          | "DASE"  | "Bruno Oliveira Castro" |

  Esquema do Cenario: Inserir departamentos com mesma sigla
    Dado a tela inicial de departamentos aberta
    Quando o administrador informar o nome <nome> a sigla <sigla> e o responsavel <responsavel>
    Entao deve ser exibida a mensagem para o departamento "Registro já cadastrado no sistema, tente outro."

    Exemplos: 
      | nome                        | sigla | responsavel         |
      | "Diretoria de extensão II " | "DEX" | "Erick Lima Barros" |

  Esquema do Cenario: Alterar Departamento
    Dado a tela inicial de departamentos aberta
    Quando o administrador selecionar o registro <registro> que deseja alterar
    E o administrador informar o nome <nome> a sigla <sigla> e o responsavel <responsavel> para atualização
    Entao deve ser exibida a mensagem para o departamento "Departamento alterado com Sucesso!"

    Exemplos: 
      | registro                                             | nome                         | sigla  | responsavel             |
      | "Departamento de gestao da tecnologia da informacao" | "Departamento de tecnologia" | "DGTI" | "Erick Lima Barros"     |
      | "Assessoria de Comunicação"                          | "Assessoria de Comunicação"  | "ASCO" | "Maria Cunha Pinto"     |
      | "Setor de registro escolar"                          | "Setor de registro escolar"  | "DASE" | "Bruno Oliveira Castro" |

  Esquema do Cenario: Remoção de departamentos
    Dado a tela inicial de departamentos aberta
    Quando o administrador deve selecionar o departamento <registro> que deseja remover
    Entao deve ser exibida a mensagem para o departamento "Departamento removido com Sucesso!"

    Exemplos: 
      | registro                                             |
      | "Departamento de tecnologia"                         |
      | "Assessoria de Comunicação"                          |
      | "Setor de registro escolar"                          |
      | "Diretoria de extensão"                              |
      | "Diretoria de ensino"                                |
 