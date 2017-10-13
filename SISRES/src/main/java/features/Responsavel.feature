# language: pt
  @ResponsavelTeste
  Funcionalidade: Testar as operacoes basicas do responsavel
    O sistema deve prover o cadastro, alteracao e remocao do responsavel de forma correta.
    
    Esquema do Cenario: Testar o cadastro do responsavel
      Dado o responsavel com nome <nome> ainda nao cadastrado
      Quando o usuário informar o <nome> e a senha <senha> do responsável
      E confirmar a senha <senha>
      Entao deve ser exibida a mensagem "Responsável cadastrado com Sucesso!"
      E o registro deve ser inserido no banco de dados
   
Exemplos: 
| nome           | senha   |
| Carlos Macedo  | C2345M  |
| Renata Macia   | Ren9845 |
| Joaquim Nunes  | 1221JM  |


    Esquema do Cenario: Atualizar o nome do responsavel
        Dado o responsavel ja cadastrado no sistema
        Quando o usuario selecionadar o <responsavel> que deseja alterar
        E alterar o seu <nome>
        Entao deve ser exibida a mensagem "Responsável alterado com Sucesso!" 
        E o registro deve ser alterado no banco de dados 

Exemplos: 
| reponsavel     | nome              |  
| Renata Macia   | Marcia Cavalcanti |
| Joaquim Nunes  | Fabio Martins     |
| Carlos Macedo  | Antônio Vitor     |


Esquema do Cenario: Atualizar a senha do responsavel
      Dado o responsavel ja cadastrado no sistema
      Quando o usuario selecionar o <responsavel> que deseja alterar
      E informar a sua <senha_atual> atual
      E informar a sua nova <senha_nova> e confirmá-la
      Entao deve ser exibida a mensagem "Responsável alterado com Sucesso!" 
      E o registro deve ser alterado no banco de dados 

Exemplos: 
| reponsavel     | senha_atual | senha_nova |  
| Renata Macia   |  C2345M     | oxasf123   |
| Joaquim Nunes  |  Ren9845    | jw134335n  |
| Carlos Macedo  |  1221JM     | mc1617299  |

Esquema do Cenario: Remover responsavel
      Dado o responsavel ja cadastrado no sistema
      Quando o usuario selecionar o <responsavel> que deseja remover
      Entao deve ser exibida a mensagem "Responsável removido com Sucesso!" 
      E o registro deve ser removido do banco de dados 

Exemplos: 
| reponsavel     |
| Renata Macia   |
| Joaquim Nunes  |
| Carlos Macedo  | 
