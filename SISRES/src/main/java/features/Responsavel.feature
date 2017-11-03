# language: pt
Funcionalidade: Testar as operacoes basicas do responsavel
  O sistema deve prover o cadastro, alteracao e remocao do responsavel de forma correta.

  Esquema do Cenario: Realizar cadastro do responsavel
    Dado a tela inicial do responsavel aberta
    Quando o administrador informar o <nome> e a senha <senha> do responsavel
    E confirmar a senha <senha>
    Entao deve ser exibida a mensagem "Responsável cadastrado com Sucesso!"

    Exemplos: 
      | nome            | senha     |
      | "Carlos Macedo" | "C2345M"  |
      | "Renata Macia"  | "Ren9845" |
      | "Joaquim Nunes" | "1221JM"  |

  Esquema do Cenario: Atualizar o nome do responsavel
    Dado a tela inicial do responsavel aberta
    Quando o administrador selecionar o <responsavel> que deseja alterar
    E alterar o seu <nome>
    Entao deve ser exibida a mensagem "Responsável alterado com Sucesso!"

    Exemplos: 
      | responsavel     | nome                |
      | "Renata Macia"  | "Marcia Cavalcanti" |
      | "Joaquim Nunes" | "Fabio Martins"     |
      | "Carlos Macedo" | "Antônio Vitor"     |

  Esquema do Cenario: Remover responsavel
    Dado a tela inicial do responsavel aberta
    Quando o administrador selecionar o <responsavel> que deseja remover
    Entao deve ser exibida a mensagem "Responsável removido com Sucesso!"

    Exemplos: 
      | responsavel         |
      | "Marcia Cavalcanti" |
      | "Fabio Martins"     |
      | "Antônio Vitor"     |

#  Quando tivermos a parte de logar ai será possível realizar a redefinição de senha do usuário atual.
#  Esquema do Cenario: Redefinir Senha
#    Dado a tela de redefinição de senha
#    Quando o responsável informar sua <senha_atual> e a <nova_senha>
#    E confirmar a nova senha <nova_senha>
#    Entao deve ser exibida a mensagem "Senha Redefinida com Sucesso!"
#
#    Exemplos: 
#      | senha_atual | senha_nova  |
#      | "C2345M"    | "oxasf123"  |
#      | "Ren9845"   | "jw134335n" |
#      | "1221JM"    | "mc1617299" |
