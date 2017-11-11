# language: pt
Funcionalidade: Cadastrar, alterar e remover feriados no sistema.
  Que irão simbolizar os dias não úteis durante o execução de algum processo.

  Esquema do Cenario: Inserção de feriados
    Dado a tela inicial de feriados aberta
    Quando o administrador informar a <data> e o <nome> do feriado
    Entao deve ser exibida a mensagem para o feriado "Feriado cadastrado com Sucesso!"

    Exemplos: 
      | data         | nome                 |
      | "25/12/2017" | "Natal"              |
      | "1/05/2017"  | "Dia do Trabalhador" |
      | "2/11/2017"  | "Finados"            |

  Esquema do Cenario: Inserir feriado com mesmo nome
    Dado a tela inicial de feriados aberta
    Quando o administrador informar a <data> e o <nome> do feriado
    Entao deve ser exibida a mensagem para o departamento "Registro já cadastrado no sistema, tente outro."

    Exemplos: 
     | data         | nome                 |
     | "24/12/2017" | "Natal"              |

  Esquema do Cenario: Alteração de feriados
    Dado a tela inicial de feriados aberta
    E o administrador deve selecionar o registro <registro> que deseja alterar
    Quando o administrador informar a <data> e o <nome> do feriado para atualização
    Entao deve ser exibida a mensagem para o feriado "Feriado alterado com Sucesso!"

    Exemplos: 
      | registro             | data        | nome                 |
      | "Dia do Trabalhador" | "2/06/2017" | "Dia do Trabalhador" |
      | "Finados"            | "2/11/2017" | "Dia de Finados"     |

  Esquema do Cenario: Remoção de feriados
    Dado a tela inicial de feriados aberta
    Quando o administrador deve selecionar o registro <registro> que deseja remover
    Entao deve ser exibida a mensagem para o feriado "Feriado removido com Sucesso!"

    Exemplos: 
      | registro             |
      | "Dia do Trabalhador" |
      | "Dia de Finados"     |
      | "Natal"              |
