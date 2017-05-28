/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domains;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author Natália
 */
@Entity
@SequenceGenerator(name = "DEPARTAMENTO_SEQUENCE", sequenceName = "DEPARTAMENTO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Departamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTAMENTO_SEQUENCE")
    private int id;
    
    @Column
    private String nome;
    
    @Column
    private String sigla;
    
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "id_responsavel", referencedColumnName = "id")
    private Responsavel responsavel;
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSigla() {
        return sigla;
    }

    public void setSigla(String sigla) {
        this.sigla = sigla;
    }

    public Responsavel getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(Responsavel responsavel) {
        this.responsavel = responsavel;
    }
    
    @Override
    public boolean equals(Object o)
    {
        if (o != null)
        {
            if (o instanceof Departamento)
            {
                Departamento outro = (Departamento) o;
                if (this.id == outro.id)
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 67 * hash + this.id;
        return hash;
    }  
}
