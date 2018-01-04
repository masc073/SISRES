package servico;

import dominio.Grupo;
import dominio.Responsavel;
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

    @EJB
    ResponsavelServico responsavel_servico;

    public Grupo retorna_grupo(int id)
    {
        return em.find(Grupo.class, id);
    }

    public void associarAdministrador(Responsavel responsavel_adm)
    {
        List<Grupo> grupo_administradores = new ArrayList<>();
        Grupo grupo_administrador = retorna_grupo(0);
        grupo_administradores.add(grupo_administrador);

        responsavel_adm.setGrupo(grupo_administrador);
        em.merge(responsavel_adm);
    }

    public void associarServidor(Responsavel servidor)
    {
        List<Grupo> grupo_servidores = new ArrayList<>();
        Grupo grupo_servidor = retorna_grupo(0);
        grupo_servidores.add(grupo_servidor);

        servidor.setGrupo(grupo_servidor);
        em.merge(servidor);
    }

    public void associarChefeServidor(Responsavel servidor_chefe)
    {
        List<Grupo> grupo_chefe_servidor = new ArrayList<>();
        Grupo grupo_servidor_chefe = retorna_grupo(0);
        grupo_chefe_servidor.add(grupo_servidor_chefe);

        servidor_chefe.setGrupo(grupo_servidor_chefe);
        em.merge(servidor_chefe);
    }

    public void associarAluno(Responsavel aluno)
    {
        List<Grupo> grupo_alunos = new ArrayList<>();
        Grupo grupo_aluno = retorna_grupo(0);
        grupo_alunos.add(grupo_aluno);

        aluno.setGrupo(grupo_aluno);
        em.merge(aluno);
    }

    public String buscar_grupo_responsavel(String email)
    {
        String grupoAtual = "nenhum";
        Responsavel responsavel = responsavel_servico.getResponsavelByEmail(email);

        if (responsavel != null) {

            if (responsavel.isServidor()) {

                if (responsavel.isLider()) {
                    grupoAtual = "servidor_chefe";
                }
                else {
                    grupoAtual = "servidor";
                }

            }
            else {
                if (responsavel.isAdministrador()) {
                    grupoAtual = "administrador";
                }
                else {
                    grupoAtual = "aluno";
                }
            }
        }

        return grupoAtual;
    }

    public List<Grupo> listar()
    {
        return em.createQuery("select g from Grupo AS g", Grupo.class).getResultList();
    }
}
