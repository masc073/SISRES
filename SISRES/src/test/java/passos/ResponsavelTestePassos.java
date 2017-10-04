package passos;

import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import dominio.Responsavel;
import org.junit.Assert;
/**
 *
 * @author Natália
 */
public class ResponsavelTestePassos extends ConfiguracaoTeste {
    
    private Responsavel _responsavel;
    
    public ResponsavelTestePassos()
    {
       
    }
    
    @Dado("^o nome \"(.*?)\" e a sua senha digital \"(.*?)\"$")
    public void o_nome_e_a_sua_senha_digital(String nome, String senhaDigital)
    {
        _responsavel = new Responsavel(nome, senhaDigital);
    }
    
    @Dado("^a insercao do responsavel no banco de dados$")
    public void a_insercao_do_responsavel_no_banco_de_dados()
    {
        try
        {
            et = em.getTransaction();
            et.begin();
            em.persist(_responsavel);
            et.commit();
        }
        catch(Exception ex)
        {
            if(et != null)
                et.rollback();
        }   
    }
    
     @Entao("^o responsavel deve ser encontrado no banco de dados$") 
     public void o_responsavel_deve_ser_encontrado_no_banco_de_dados()
     {
         Responsavel responsavel;
         
         responsavel = em.find(Responsavel.class, _responsavel.getId());
         
         Assert.assertNotNull(responsavel);
     }        
             
     @Entao("^o responsavel \"(.*?)\" deve ser atualizado no banco de dados$")
     public void o_responsavel_deve_ser_atualizado_no_banco_de_dados()
     {
         
     }                
}
