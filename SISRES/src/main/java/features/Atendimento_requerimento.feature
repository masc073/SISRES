# language: pt
Funcionalidade: Testar o atendimento ao requerimento, aprovando e reprovando atividades.

Esquema do Cenario: Realizar fluxo do requerimento
    Dado a tela da fila de requerimentos aberta
    Quando o responsavel selecionar o requerimento "Comprovante de matricula" que deseja dar andamento
    E informar os seus dados da <descricao_atividade> , anexar <arquivo> caso necessário e realizar e clicar no botao <acao>
    Entao deve ser exibida a mensagem sobre o fluxo do requerimento <mensagem>

    Exemplos:
      | descricao_atividade                   |  arquivo       |  acao      |  mensagem                               |
      | "Informações corretas"                |                | "Aprovar"  |  "Atividade concluída com sucesso!"     |   
      | "O Aluno possui débito na biblioteca" |                | "Reprovar" |  "Atividade reprovada!"                 |
      | "Débito quitado"                      |                | "Aprovar"  |  "Atividade concluída com sucesso!"     |
      | "Ok!"                                 |                | "Aprovar"  |  "Atividade concluída com sucesso!"     |
      | "Comprovante de matrícula gerado"     |                | "Aprovar"  |  "Atividade concluída com sucesso!"     |