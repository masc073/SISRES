# language: pt
Funcionalidade: Cadastrar, alterar e excluir os processos e atividades
  relacionadas.

#    Esquema do Cenario: Tentar inserir um processo sem atividades.
#      Dado a tela inicial cadastro de processos aberta
#      Quando o administrador informar o nome <nome> a duração em dias <duracao> o responsavel <responsavel> e salvar
#      Entao deve ser exibida a seguinte mensagem : "Devem ser adicionadas atividades ao processo!"
#  
#      Exemplos:
#        | nome                                      | duracao | responsavel             |
#        | "Solicitacao de comprovante de matricula" | "2"     | "Erick Lima Barros"     |
#
#
  Esquema do Cenario: Inserir processo
    Dado a tela inicial cadastro de processos aberta
    Quando o administrador definir o fluxo do processo com as seguintes  atividades:
      | Valida informacoes                | Setor de registro escolar do DASE |
      | Requisita aprovacao da biblioteca | Biblioteca                        |
      | Gera documento                    | Setor de registro escolar do DASE |
    E o administrador informar o nome <nome> a duração em dias <duracao> o responsavel <responsavel> e salvar
    Entao deve ser exibida a seguinte mensagem : "Processo cadastrado com Sucesso!"

    Exemplos: 
      | nome                       | duracao | responsavel               |
      | "Comprovante de matricula" | "2"     | "Nicole Goncalves Castro" |


  Esquema do Cenario: Alterar dados do processo
    Dado a tela de listagem de processos aberta
    Quando o administrador selecionar o processo <registro> que deseja alterar
    E informar o <nome> a duracao <duracao> e o <responsavel> para atualização
    Entao deve ser exibida a seguinte mensagem : "Processo alterado com Sucesso!"

    Exemplos: 
    | registro                   | nome                                      | duracao | responsavel               |
    | "Comprovante de matricula" | "Solicitacao de Comprovante de matricula" | "2"     | "Nicole Goncalves Castro" |

#
#  Esquema do Cenario: Remover processo
#    Dado a tela de listagem de processos aberta
#    Quando o administrador selecionar o registro <registro> que deseja remover 
#    Entao deve ser exibida a seguinte mensagem : "Processo removido com Sucesso!"
#
#    Exemplos: 
#      | registro                                  | 
#      | "Solicitacao de Comprovante de matricula" | 


#  Esquema do Cenario: Alterar processo
#    Dado a tela de listagem de processos aberta
#    Quando o administrador selecionar o registro <nome_atual> que deve alterar 
#    E o administrador informar o nome <nome_novo> a duração em dias <duracao> e o responsavel <responsavel>
#    E ir para aba de atividades e alterar as seguintes atividades:
#      | Valida informacoes                | Validar as informacoes | Setor de registro escolar do DASE |
#      | Gera documento                    | Gera o comprovante     | Setor de registro escolar do DASE |
#    E ir para a aba confirmação e salvar.
#    Entao deve ser exibida a seguinte mensagem : "Processo cadastrado com Sucesso!"
#
#    Exemplos: 
#    | nome_atual                 | nome_novo                                 | duracao | responsavel               |
#    | "Comprovante de matricula" | "Solicitacao do comprovante de matricula" | "2"     | "Nicole Goncalves Castro" |