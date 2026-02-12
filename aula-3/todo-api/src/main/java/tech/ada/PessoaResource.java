package tech.ada;

import io.quarkus.logging.Log;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path("pessoas")
public class PessoaResource {

    List<Pessoa> pessoas = new ArrayList<>();

    @POST
    @Consumes({"application/json"})
    @Produces(MediaType.APPLICATION_JSON)
    @ResponseStatus(201)
    public Response criaPessoa(PessoaDTO pessoaDTO) {
        System.out.println("sout: " + pessoaDTO);

        Pessoa pessoa = new Pessoa(pessoaDTO.nomeCompleto(), pessoaDTO.idade());

        pessoas.add(pessoa);

        Log.info(pessoaDTO);

        return Response.status(201).header("Location", "/pessoas/" + pessoa.getNome()).build();
    }

    @GET
    public List<PessoaDTO> buscaPessoas() {
//        List<PessoaDTO> dtos = new ArrayList<>();
//        for (Pessoa pessoa : pessoas) { // pra cada pessoa na lista
//            PessoaDTO pessoaDTO = new PessoaDTO(pessoa.getNome(), pessoa.getIdade(), LocalDate.now(), pessoa.getSenha());
//            dtos.add(pessoaDTO);
//        }
//
//        return dtos;
        List<PessoaDTO> list = pessoas.stream()
                .map(pessoa -> new PessoaDTO(pessoa.getNome(), pessoa.getIdade(), LocalDate.now(), pessoa.getSenha()))
                // para cada pessoa na stream de Pessoa eu crio um PessoaDTO
                .toList();

        return list;

    }

}
