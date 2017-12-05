# language: pt
Funcionalidade: Testar as operacoes basicas do Requerimento
  O sistema deve permitir a inserção e o cancelamento do requerimento de forma correta.

  Esquema do Cenario: Realizar a insercao do Requerimento
    Dado a tela inicial de requerimento aberta
    Quando o aluno informar a matricula <matricula> o processo <processo> e salvar
    Entao deve ser exibida a mensagem sobre o requerimento "Requerimento aberto com Sucesso!"

    Exemplos: 
      | matricula        | processo                    |
      | "20141Y6-RC0331" | "Comprovante de matricula"  |

  Esquema do Cenario: Tentar abrir requerimento para mesmo processo
    Dado a tela inicial de requerimento aberta
    Quando o aluno informar a matricula <matricula> o processo <processo> e salvar
    Entao deve ser exibida a mensagem sobre o requerimento "Registro já cadastrado no sistema, tente outro."

    Exemplos: 
      | matricula        | processo                    |
      | "20141Y6-RC0331" | "Comprovante de matricula"  |

  Esquema do Cenario: Consultar requerimento cadastrado
    Dado a tela inicial de requerimento aberta
    Quando o aluno selecionar o <requerimento> que deseja visualizar
    Entao deve ser redirecionado para outra tela com as informacoes do requerimento

    Exemplos: 
      | requerimento                | 
      | "Comprovante de matricula"  |

#  Esquema do Cenario: Remover responsavel
#    Dado a tela inicial do responsavel aberta
#    Quando o administrador selecionar o <responsavel> que deseja remover
#    Entao deve ser exibida a mensagem "Responsável removido com Sucesso!"
#
#    Exemplos: 
#      | responsavel         |
#      | "Marcia Cavalcanti" |
#      | "Fabio Martins"     |
#      | "Antônio Vitor"     |
