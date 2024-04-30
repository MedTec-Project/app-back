package br.medtec.repositories;

import br.medtec.entity.Usuario;
import br.medtec.utils.ConsultaBuilder;
import br.medtec.utils.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class LoginRepository extends GenericRepository<Usuario> {

    public LoginRepository(){
        super(Usuario.class);
    }



    public Usuario findByEmailAndSenha(String email){
        ConsultaBuilder consulta = createConsultaBuilder();

        consulta.select("u")
                .from("Usuario u")
                .where("u.email = :email")
                .param("email", email);


        return (Usuario) consulta.primeiroRegistro();
    }

}
