# language: pt
Funcionalidade: Testar as operacoes basicas do Requerimento
  O sistema deve permitir a inserção e o cancelamento do requerimento de forma correta.

  Esquema do Cenario: Realizar a insercao do Requerimento
    Dado a tela inicial de requerimento aberta
    Quando o aluno informar a matricula <matricula> o processo <processo> e salvar
    Entao deve ser exibida a mensagem sobre o requerimento "Requerimento aberto com Sucesso!"

    Exemplos: 
      | matricula        | processo                   |
      | "20141Y6-RC0331" | "Comprovante de matricula" |

  Esquema do Cenario: Tentar abrir requerimento para mesmo processo
    Dado a tela inicial de requerimento aberta
    Quando o aluno informar a matricula <matricula> o processo <processo> e salvar
    Entao deve ser exibida a mensagem sobre o requerimento "Registro já cadastrado no sistema, tente outro."

    Exemplos: 
      | matricula        | processo                   |
      | "20141Y6-RC0331" | "Comprovante de matricula" |

  Esquema do Cenario: Consultar requerimento cadastrado
    Dado a tela inicial de requerimento aberta
    Quando o aluno selecionar o <requerimento> que deseja visualizar
    Entao deve ser redirecionado para outra tela com as informacoes do requerimento

    Exemplos: 
      | requerimento               |
      | "Comprovante de matricula" |

  Esquema do Cenario: Realizar fluxo do requerimento
    Dado a tela da fila de requerimentos aberta
    Quando o responsavel selecionar o requerimento "Comprovante de matricula" que deseja dar andamento
    E informar os seus dados da <descricao_atividade> , anexar <arquivo> caso necessário e realizar e clicar no botao <acao>
    Entao deve ser exibida a mensagem sobre o requerimento <mensagem>

    Exemplos:
      | descricao_atividade                   |  arquivo       |  acao      |  mensagem                               |
      | "Informações corretas"                |                | "Aprovar"  |  "Atividade concluída com sucesso!"     |   
      | "O Aluno possui débito na biblioteca" |                | "Reprovar" |  "Atividade reprovada!"                 |
      | "Débito quitado"                      |                | "Aprovar"  |  "Atividade concluída com sucesso!"     |
      | "Comprovante de matrícula gerado"     |                | "Aprovar"  |  "Atividade concluída com sucesso!"     |
   
  Esquema do Cenario: Encerrar requerimento
    Dado a tela inicial de requerimento aberta
    Quando o aluno selecionar o <requerimento> que deseja encerrar
    Entao deve ser exibida a mensagem sobre o requerimento "Requerimento encerrado com Sucesso!"

    Exemplos: 
      | requerimento               |
      | "Comprovante de matricula" |
