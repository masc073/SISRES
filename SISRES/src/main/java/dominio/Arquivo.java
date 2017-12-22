package dominio;

import excecao.ExcecaoSistema;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.SequenceGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@Entity
@SequenceGenerator(name = "ARQUIVO_SEQUENCE", sequenceName = "ARQUIVO_SEQUENCE", allocationSize = 1, initialValue = 1)
public class Arquivo extends EntidadeNegocio implements Serializable
{

    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(name = "BLOB_ARQUIVO", nullable = true)
    private byte[] arquivo;

    @NotNull
    @Column(name = "TXT_EXTENSAO_ARQUIVO", nullable = true)
    private String extensao;

    @XmlAttribute(required = true)
    @NotBlank
    @Column(name = "TXT_NOME_ARQUIVO", nullable = true)
    private String nome;
    
    @Transient
    private StreamedContent stream;

    public StreamedContent getStream()
    {
        InputStream stream = new ByteArrayInputStream(arquivo);
        return new DefaultStreamedContent(stream, extensao, nome);
    }

    public void setStream(StreamedContent stream)
    {
        this.stream = stream;
    }
  
    public byte[] getArquivo()
    {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo)
    {
        this.arquivo = arquivo;
    }

    public String getExtensao()
    {
        return extensao;
    }

    public void setExtensao(String extensao)
    {
        this.extensao = extensao;
    }

    public String getNome_normal()
    {
        return nome;
    }
    
    public String getNome()
    {
        String nomeCodificado = null;

        try {
            nomeCodificado = URLEncoder.encode(this.nome, "UTF-8");
        }
        catch (UnsupportedEncodingException ex) {
            throw new ExcecaoSistema(ex);
        }
        return nomeCodificado;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

//    public StreamedContent retorna_arquivo()
//    {
//        InputStream stream = new ByteArrayInputStream(arquivo);
//        return new DefaultStreamedContent(stream, extensao, nome);
//    }

}
