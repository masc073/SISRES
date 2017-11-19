# language: pt
Funcionalidade: Cadastrar, alterar e excluir os processos e atividades
  relacionadas.

  # ESTA PERMITINDO ADICIONAR UM PROCESSO SEM ATIVIDADES -> AJUSTAR
  #  Esquema do Cenario: Tentar inserir um processo sem atividades.
  #    Dado a tela inicial cadastro de processos aberta
  #    Quando o administrador informar o nome <nome> a duração em dias <duracao> e o responsavel <responsavel>
  #    E ir para a aba confirmação e salvar.
  #    Entao deve ser exibida a seguinte mensagem : "O processo deve conter atividades."
  #
  #    Exemplos:
  #      | nome                                      | duracao | responsavel             |
  #      | "Solicitação de comprovante de matrícula" | "2"     | "Erick Lima Barros"     |
  Esquema do Cenario: Inserir processo
    Dado a tela inicial cadastro de processos aberta
    Quando o administrador informar o nome <nome> a duração em dias <duracao> e o responsavel <responsavel>
    E ir para aba de atividades e adicionar as seguintes atividades:
      | Valida informacoes                | Setor de registro escolar do DASE |
      | Requisita aprovacao da biblioteca | Biblioteca                        |
      | Gera documento                    | Setor de registro escolar do DASE |
    E ir para a aba confirmação e salvar.
    Entao deve ser exibida a seguinte mensagem : "Processo cadastrado com Sucesso!"

    Exemplos: 
      | nome                       | duracao | responsavel               |
      | "Comprovante de matricula" | "2"     | "Nicole Goncalves Castro" |
