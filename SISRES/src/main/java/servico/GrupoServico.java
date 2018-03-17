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

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionManagement(TransactionManagementType.CONTAINER)
public class GrupoServico extends Servico
{

    public Grupo retorna_grupo(Long id)
    {
        return em.find(Grupo.class, id);
    }
    
    public void teste()
    {
        System.out.println("DEEEEEEEEEU CERTO!");
    }

    public void associarAdministrador(Responsavel responsavel_adm)
    {
        List<Grupo> grupo_administradores = new ArrayList<>();
        Grupo grupo_administrador = retorna_grupo(Long.valueOf(2));
        grupo_administradores.add(grupo_administrador);

        responsavel_adm.setGrupo(grupo_administrador);
        em.merge(responsavel_adm);
    }

    public void associarServidor(Responsavel servidor)
    {
        List<Grupo> grupo_servidores = new ArrayList<>();
        Grupo grupo_servidor = retorna_grupo(Long.valueOf(1));
        grupo_servidores.add(grupo_servidor);

        servidor.setGrupo(grupo_servidor);
        em.merge(servidor);
    }

//    public void associarChefeServidor(Responsavel servidor_chefe)
//    {
//        List<Grupo> grupo_chefe_servidor = new ArrayList<>();
//        Grupo grupo_servidor_chefe = retorna_grupo(0);
//        grupo_chefe_servidor.add(grupo_servidor_chefe);
//
//        servidor_chefe.setGrupo(grupo_servidor_chefe);
//        em.merge(servidor_chefe);
//    }

    public void associarAluno(Responsavel aluno)
    {
        List<Grupo> grupo_alunos = new ArrayList<>();
        Grupo grupo_aluno = retorna_grupo(Long.valueOf(0));
        grupo_alunos.add(grupo_aluno);

        aluno.setGrupo(grupo_aluno);
        em.merge(aluno);
    }


    public List<Grupo> listar()
    {
        return em.createQuery("select g from Grupo AS g", Grupo.class).getResultList();
    }
}
