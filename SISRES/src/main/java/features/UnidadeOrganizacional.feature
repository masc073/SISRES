# language: pt
Funcionalidade: Realizar as operações básicas da unidade organizacional,
  como: inserir, alterar e remover.

  Esquema do Cenario: Inserir Unidade Organizacional
    Dado a tela inicial de Unidades Organizacionais aberta
    Quando o administrador informar o nome <nome> a sigla <sigla> e o responsavel <responsavel>
    Entao deve ser exibida a mensagem para a Unidade Organizacional "Unidade Organizacional cadastrada com Sucesso!"

    Exemplos: 
      | nome                                                 | sigla   | responsavel             |
      | "Departamento de gestao da tecnologia da informacao" | "DGTI"  | "Erick Lima Barros"     |
      | "Diretoria de extensão"                              | "DEX"   | "Livia Costa Carvalho"  |
      | "Diretoria de ensino"                                | "DEN"   | "Alex Santos Sousa"     |
      | "Assessoria de Comunicação"                          | "ASCOM" | "Maria Cunha Pinto"     |
      | "Setor de registro escolar"                          | "DASE"  | "Bruno Oliveira Castro" |

  Esquema do Cenario: Inserir Unidade Organizacional com mesma sigla
    Dado a tela inicial de Unidades Organizacionais aberta
    Quando o administrador informar o nome <nome> a sigla <sigla> e o responsavel <responsavel>
    Entao deve ser exibida a mensagem para a Unidade Organizacional "Registro já cadastrado no sistema, tente outro."

    Exemplos: 
      | nome                        | sigla | responsavel         |
      | "Diretoria de extensão II " | "DEX" | "Erick Lima Barros" |

  Esquema do Cenario: Alterar Unidade Organizacional
    Dado a tela inicial de Unidades Organizacionais aberta
    Quando o administrador selecionar o registro <registro> que deseja alterar
    E o administrador informar o nome <nome> a sigla <sigla> e o responsavel <responsavel> para atualização
    Entao deve ser exibida a mensagem para a Unidade Organizacional "Unidade Organizacional alterada com Sucesso!"

    Exemplos: 
      | registro                                             | nome                         | sigla  | responsavel             |
      | "Departamento de gestao da tecnologia da informacao" | "Departamento de tecnologia" | "DGTI" | "Erick Lima Barros"     |
      | "Assessoria de Comunicação"                          | "Assessoria de Comunicação"  | "ASCO" | "Maria Cunha Pinto"     |
      | "Setor de registro escolar"                          | "Setor de registro escolar"  | "DASE" | "Bruno Oliveira Castro" |

  Esquema do Cenario: Remoção da Unidade Organizacional
    Dado a tela inicial de Unidades Organizacionais aberta
    Quando o administrador deve selecionar a Unidade Organizacional <registro> que deseja remover
    Entao deve ser exibida a mensagem para a Unidade Organizacional "Unidade Organizacional removida com Sucesso!"

    Exemplos: 
      | registro                                             |
      | "Departamento de tecnologia"                         |
      | "Assessoria de Comunicação"                          |
      | "Setor de registro escolar"                          |
      | "Diretoria de extensão"                              |
      | "Diretoria de ensino"                                |
 