package br.medtec.usuario;

import br.medtec.generic.JpaGenericRepository;
import br.medtec.utils.ConsultaBuilder;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class JpaUsuarioRepository extends JpaGenericRepository<Usuario> implements UsuarioRepository {

    public JpaUsuarioRepository(){
        super(Usuario.class);
    }

    public Usuario findByEmail(String email){
        ConsultaBuilder consulta = createConsultaBuilder();

        consulta.select("u")
                .from("Usuario u")
                .where("u.email = :email")
                .param("email", email);


        return (Usuario) consulta.primeiroRegistro();
    }

}
