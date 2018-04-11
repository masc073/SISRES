# language: pt
Funcionalidade: Testar as operacoes basicas do responsavel
  O sistema deve prover o cadastro, alteracao e remocao do responsavel de forma correta.

  Esquema do Cenario: Realizar cadastro do responsavel
    Dado a tela inicial do cadastro aberta
    Quando o usuario informar o <nome> , <email> , <titulo> e <unidade_organizacional>
    Entao deve ser exibida a mensagem "Responsável cadastrado com Sucesso!"

    Exemplos: 
      | nome              |  email                     | titulo            | unidade_organizacional          |
      | "Carlos Macedo"   | "carlos_macedo@gmail.com"  | "Professor"       | "Biblioteca"                    |
      | "Renata Macia"    | "renata_macia@gmail.com"   | "Administrador"   | ""                              |
      | "Joaquim Nunes"   | "joaquim_nunes@gmail.com"  | "diretor"         | "Serviço Social - Psicologia"   |
      | "Administrador"   | "ifpeadm01@gmail.com"      | "Administrador"   | ""                              |

#  Esquema do Cenario: Atualizar o nome do responsavel
#    Dado a tela inicial do responsavel aberta
#    Quando o administrador selecionar o <responsavel> que deseja alterar
#    E alterar o seu <nome>
#    Entao deve ser exibida a mensagem "Responsável alterado com Sucesso!"
#
#    Exemplos: 
#      | responsavel     | nome                |
#      | "Renata Macia"  | "Marcia Cavalcanti" |
#      | "Joaquim Nunes" | "Fabio Martins"     |
#      | "Carlos Macedo" | "Antônio Vitor"     |
#
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

