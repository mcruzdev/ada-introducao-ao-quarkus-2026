package tech.ada;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.resteasy.reactive.jackson.SecureField;

import java.time.LocalDate;

public record PessoaDTO(
        @JsonProperty("nome_completo") String nomeCompleto,
        Integer idade,
        @JsonProperty("data_nascimento")
        @JsonFormat(pattern = "dd/MM/yyyy") // MM -> mes, yyyyy -> ano, dd -> dia
        LocalDate dataNascimento,
        @SecureField(rolesAllowed = "user")
        String senha) {}

