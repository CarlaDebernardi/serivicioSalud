package com.serviciosalud.demo.servicios;

import com.serviciosalud.demo.MiExcepcion.MiExcepcion;
import com.serviciosalud.demo.entidades.Imagen;
import com.serviciosalud.demo.entidades.Paciente;
import com.serviciosalud.demo.entidades.Usuario;
import com.serviciosalud.demo.enumeraciones.Roles;
import com.serviciosalud.demo.repositorios.PacienteRepositorio;
import com.serviciosalud.demo.repositorios.UsuarioRepositorio;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Samu Noah
 */
@Service
public class PacienteServicio /*implements UserDetailsService*/ {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private ImagenServicio imagenServicio;

    /*metodo para registrar usuario*/
    @Transactional
    public void registrar(String nombre, String apellido, Integer dni, String email, Integer telefono,
            String sexo, String password, String password2, String obraSocialPaciente, Integer numeroDeAfiliado, String motivoConsulta) throws MiExcepcion {

        validar(nombre, apellido, dni, email, telefono,
                sexo, password, password2, obraSocialPaciente, numeroDeAfiliado, motivoConsulta);

        Paciente paciente = new Paciente();
        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setDni(dni);
        paciente.setEmail(email);
        /* paciente.setNombreUsuario(nombreUsuario);*/
        paciente.setTelefono(telefono);
        /*paciente.setActivo(activo);*/
        paciente.setSexo(sexo);

        /*  paciente.setPassword(new BCryptPasswordEncoder().encode(password));*/
        paciente.setPassword(password);

        paciente.setObraSocialPaciente(obraSocialPaciente);
        paciente.setNumeroDeAfiliado(numeroDeAfiliado);
        paciente.setMotivoDeConsulta(motivoConsulta);

        paciente.setRol(Roles.PACIENTE);

        /*  Date fecha = new Date();
        paciente.setFechaDeNacimiento(fecha);*/

 /*  Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImg(imagen);*/
        usuarioRepositorio.save(paciente);

    }

    /*metodo para actualizar usuario*/
    @Transactional
    public void actualizar(MultipartFile archivo, String idPaciente, String nombre, String apellido, Integer dni, String email, Integer telefono,
            String sexo, String password, String password2, String obraSocialPaciente, Integer numeroDeAfiliado, String motivoConsulta) throws MiExcepcion {

        validar(nombre, apellido, dni, email, telefono,
                sexo, password, password2, obraSocialPaciente, numeroDeAfiliado, motivoConsulta);

        Optional<Paciente> respuesta = pacienteRepositorio.findById(idPaciente);

        if (respuesta.isPresent()) {

            Paciente paciente = respuesta.get();

            paciente.setNombre(nombre);
            paciente.setApellido(apellido);
            paciente.setDni(dni);
            paciente.setEmail(email);
            paciente.setTelefono(telefono);
            paciente.setSexo(sexo);

            /* usuario.setPassword(new BCryptPasswordEncoder().encode(password));*/
            paciente.setPassword(password);

            paciente.setRol(Roles.PACIENTE);

            paciente.setObraSocialPaciente(obraSocialPaciente);
            paciente.setNumeroDeAfiliado(numeroDeAfiliado);
            paciente.setMotivoDeConsulta(motivoConsulta);

            String idImagen = null;

            if (paciente.getImg() != null) {

                idImagen = paciente.getImg().getId();
            }

            Imagen imagen = imagenServicio.actualizar(archivo, idImagen);

            paciente.setImg(imagen);

            pacienteRepositorio.save(paciente);

        }
    }

    /*    prueba  pero sigue sin funcionar*/
    @Transactional
    public void crearUsuariolisto() {

        /* Usuario paciente = new Usuario();*/
        Paciente paciente = new Paciente();
        paciente.setNombre("Diego");
        paciente.setApellido("Maradona");
        paciente.setDni(12345678);
        paciente.setEmail("Dios@10");
        /* paciente.setNombreUsuario(nombreUsuario);*/
        paciente.setTelefono(87654321);
        /*paciente.setActivo(activo);*/
        paciente.setSexo("M");

        /*  paciente.setPassword(new BCryptPasswordEncoder().encode(password));*/
        paciente.setPassword("1234567");

        paciente.setRol(Roles.PACIENTE);

        paciente.setObraSocialPaciente("Eterno");
        paciente.setNumeroDeAfiliado(10101010);
        paciente.setMotivoDeConsulta("la mano de Dios");
        /*Date fecha = new Date();
        paciente.setFechaDeNacimiento(fecha);*/
 /*  Imagen imagen = imagenServicio.guardar(archivo);
        paciente.setImg(imagen);*/

 /* usuarioRepositorio.save(paciente);*/
        pacienteRepositorio.save(paciente);
    }

    /* aqui termina la prueba  */
    @Transactional
    public void borrarPorId(String id) {
        pacienteRepositorio.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Paciente getOne(String id) {
        return pacienteRepositorio.getOne(id);
    }

    @Transactional(readOnly = true)
    public Paciente buscarPorDni(Integer dni) {
        return usuarioRepositorio.buscarUsuarioPorDni(dni);
    }

    @Transactional(readOnly = true)
    public Paciente buscarPorEmail(String email) {
        return usuarioRepositorio.buscarUsuarioPorEmail(email);
    }

    @Transactional
    public List<Paciente> listaPacientes() {

        return pacienteRepositorio.findAll();
    }

    /*metodo de validacion*/
    private void validar(String nombre, String apellido, Integer dni, String email,
            Integer telefono, String sexo, String password, String password2, String obraSocialPaciente, Integer numeroDeAfiliado, String motivoConsulta) throws MiExcepcion {

        if (nombre == null || nombre.isEmpty()) {
            throw new MiExcepcion("el nombre del usuario no puede ser nulo ni estar vacío");
        }
        if (apellido == null || apellido.isEmpty()) {
            throw new MiExcepcion("el apellido del usuario no puede ser nulo ni estar vacío");
        }
        if (dni == null || dni <= 0) {
            throw new MiExcepcion("el DNI del usuario no puede ser nulo ni ser menor o igual a 0");
        }
        if (email == null || email.isEmpty()) {
            throw new MiExcepcion("el email no puede ser nulo ni estar vacío");
        }
        /*if (nombreUsuario == null || nombreUsuario.isEmpty()) {
            throw new MiExcepcion("el nombre del nombre de usuario no puede ser nulo ni estar vacío");
        }*/
        if (obraSocialPaciente == null || obraSocialPaciente.isEmpty()) {
            throw new MiExcepcion("la obra social no puede ser nulo ni estar vacío");
        }
        if (motivoConsulta == null || motivoConsulta.isEmpty()) {
            throw new MiExcepcion("el motivo de la consulta no puede ser nulo ni estar vacío");
        }
        if (sexo == null || sexo.isEmpty()) {
            throw new MiExcepcion("el sexo no puede ser nulo ni estar vacío");
        }
        if (numeroDeAfiliado == null || numeroDeAfiliado <= 0) {
            throw new MiExcepcion("el numero de afiliado no puede ser nulo ni ser menor o igual a 0");
        }
        if (telefono == null || telefono <= 0) {
            throw new MiExcepcion("el telefono del usuario no puede ser nulo ni ser menor o igual a 0");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiExcepcion("el password del usuario no puede estar vacío y debe tener más de 6 digitos");
        }
        if (!password2.equals(password)) {
            throw new MiExcepcion("los passwords ingresados deben ser iguales");
        }
    }
}
