package servico;

import dominio.Grupo;
import dominio.Responsavel;
import dominio.Titulos;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

/**
 * Responsável por listar e associar grupos aos responsáveis no banco de dados.
 *
 * @author Natália Amâncio
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GrupoServico extends Servico
{

    public Grupo retorna_grupo(Long id)
    {
        return em.find(Grupo.class, id);
    }
  
    /** Associa responsável ao grupo de Administrador.
     * @return Responsavel
     * @param responsavel_adm
     */
    public Responsavel associarAdministrador(Responsavel responsavel_adm)
    {
        List<Grupo> grupo_administradores = new ArrayList<>();
        Grupo grupo_administrador = retorna_grupo(Long.valueOf(2));
        grupo_administradores.add(grupo_administrador);

        responsavel_adm.setGrupo(grupo_administrador);
        return responsavel_adm;
    }
    
    /** Associa responsável ao grupo de servidor.
     * @return Responsavel
     * @param servidor
     */
    public Responsavel associarServidor(Responsavel servidor)
    {
        List<Grupo> grupo_servidores = new ArrayList<>();
        Grupo grupo_servidor = retorna_grupo(Long.valueOf(1));
        grupo_servidores.add(grupo_servidor);

        servidor.setGrupo(grupo_servidor);
        return servidor;
    }
    
    /** Associa responsável ao grupo de aluno.
     * @return Responsavel
     * @param aluno
     */
    public Responsavel associarAluno(Responsavel aluno)
    {
        List<Grupo> grupo_alunos = new ArrayList<>();
        Grupo grupo_aluno = retorna_grupo(Long.valueOf(0));
        grupo_alunos.add(grupo_aluno);

        aluno.setGrupo(grupo_aluno);

        return aluno;
    }

    /** Lista todos os Grupos cadastrados no banco de dados.
     * @return List<Grupo> Lista de grupos
     */
    public List<Grupo> listar()
    {
        return em.createQuery("select g from Grupo AS g", Grupo.class).getResultList();
    }
}
