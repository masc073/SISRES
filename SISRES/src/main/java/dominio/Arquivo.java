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

/** Define o arquivo e suas caracteristicas. 
 * @author Natália Amâncio
 */

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
    
    
    /** Retorna arquivo como stream de bytes
     * @return StreamedContent Conteúdo da stream
     */
    public StreamedContent getStream()
    {
        InputStream stream = new ByteArrayInputStream(arquivo);
        return new DefaultStreamedContent(stream, extensao, nome);
    }

    /** Atribui um valor para o stream
     * @param stream Conteúdo da stream
     */
    public void setStream(StreamedContent stream)
    {
        this.stream = stream;
    }
  
    /** Retorna um array de bytes que representa o arquivo
     * @return byte[] Array de bytes que representa o arquivo
     */
    public byte[] getArquivo()
    {
        return arquivo;
    }

    /** Atribui uma instância para o arquivo
     * @param arquivo Array de bytes que representa o arquivo
     */
    public void setArquivo(byte[] arquivo)
    {
        this.arquivo = arquivo;
    }

    /** Retorna a extensão do arquivo 
     * @return String - Extensão do arquivo
     */
    public String getExtensao()
    {
        return extensao;
    }

    /** Atribui um valor para a extensão do arquivo
     * @param extensao - Extensão do aquivo
     */
    public void setExtensao(String extensao)
    {
        this.extensao = extensao;
    }

    /** Retorna o nome original do arquivo
     * @return String - Nome original do arquivo
     */
    public String getNome_normal()
    {
        return nome;
    }
    
    /** Retorna nome codificado do arquivo
     * @return String - Nome codificado
     */
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

    /** Atribui valor ao campo nome do arquivo.
     * @param nome Nome do arquivo
     */
    public void setNome(String nome)
    {
        this.nome = nome;
    }
}
