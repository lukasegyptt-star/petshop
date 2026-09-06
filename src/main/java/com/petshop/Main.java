package com.petshop;

import com.petshop.dao.ClienteDAO;
import com.petshop.dao.MascotaDAO;
import com.petshop.dao.TurnoDAO;
import com.petshop.dao.UsuarioDAO;
import com.petshop.modelo.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private static final ClienteDAO clienteDAO = new ClienteDAO();
    private static final MascotaDAO mascotaDAO = new MascotaDAO();
    private static final TurnoDAO turnoDAO = new TurnoDAO();

    public static void main(String[] args) {
        System.out.println("=== PETSHOP ===");
        Usuario usuario = login();
        if (usuario == null) {
            System.out.println("Usuario o contraseña incorrectos.");
            return;
        }
        System.out.println("Bienvenido/a " + usuario.getNombreCompleto() + " (" + usuario.getRol() + ")");

        switch (usuario.getRol()) {
            case ADMINISTRADOR -> menuAdministrador(usuario);
            case ADMINISTRATIVO -> menuAdministrativo(usuario);
            case BANADOR -> menuBanador(usuario);
        }
    }

    private static Usuario login() {
        System.out.print("Usuario: ");
        String nombreUsuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();
        return usuarioDAO.autenticar(nombreUsuario, contrasena);
    }

    // ---------- MENÚ ADMINISTRADOR ----------
    private static void menuAdministrador(Usuario admin) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Registrar administrativo");
            System.out.println("2. Registrar bañador");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            switch (sc.nextLine()) {
                case "1" -> registrarFuncionario(Rol.ADMINISTRATIVO);
                case "2" -> registrarFuncionario(Rol.BANADOR);
                case "0" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void registrarFuncionario(Rol rol) {
        System.out.print("Nombre de usuario: ");
        String nombreUsuario = sc.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = sc.nextLine();
        System.out.print("Nombre completo: ");
        String nombreCompleto = sc.nextLine();
        usuarioDAO.registrar(new Usuario(0, nombreUsuario, contrasena, nombreCompleto, rol));
        System.out.println(rol + " registrado correctamente.");
    }

    // ---------- MENÚ ADMINISTRATIVO ----------
    private static void menuAdministrativo(Usuario administrativo) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ ADMINISTRATIVO ---");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Registrar mascota");
            System.out.println("3. Agendar turno");
            System.out.println("4. Ver agenda del día");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            switch (sc.nextLine()) {
                case "1" -> registrarCliente(administrativo);
                case "2" -> registrarMascota();
                case "3" -> agendarTurno(administrativo);
                case "4" -> verAgendaDelDia();
                case "0" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void registrarCliente(Usuario creador) {
        System.out.print("Nombre del cliente: ");
        String nombre = sc.nextLine();
        System.out.print("Teléfono: ");
        String telefono = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        clienteDAO.registrar(new Cliente(0, nombre, telefono, email, creador.getId()));
        System.out.println("Cliente registrado correctamente.");
    }

    private static void registrarMascota() {
        System.out.print("ID del cliente dueño: ");
        int clienteId = Integer.parseInt(sc.nextLine());
        if (clienteDAO.buscarPorId(clienteId) == null) {
            System.out.println("No existe un cliente con ese ID.");
            return;
        }
        System.out.print("Nombre de la mascota: ");
        String nombre = sc.nextLine();
        System.out.print("Especie: ");
        String especie = sc.nextLine();
        System.out.print("Raza: ");
        String raza = sc.nextLine();
        mascotaDAO.registrar(new Mascota(0, nombre, especie, raza, clienteId));
        System.out.println("Mascota registrada correctamente.");
    }

    // Menú desplegable con las 3 opciones de servicio pedidas
    private static TipoServicio elegirTipoServicio() {
        System.out.println("Seleccione el servicio:");
        System.out.println("1. " + TipoServicio.BANO.getDescripcion());
        System.out.println("2. " + TipoServicio.CORTE_PELO.getDescripcion());
        System.out.println("3. " + TipoServicio.BANO_Y_CORTE.getDescripcion());
        System.out.print("Opción: ");
        return switch (sc.nextLine()) {
            case "1" -> TipoServicio.BANO;
            case "2" -> TipoServicio.CORTE_PELO;
            case "3" -> TipoServicio.BANO_Y_CORTE;
            default -> null;
        };
    }

    private static void agendarTurno(Usuario creador) {
        System.out.print("ID de la mascota: ");
        int mascotaId = Integer.parseInt(sc.nextLine());
        if (mascotaDAO.buscarPorId(mascotaId) == null) {
            System.out.println("No existe una mascota con ese ID.");
            return;
        }

        TipoServicio tipoServicio = elegirTipoServicio();
        if (tipoServicio == null) {
            System.out.println("Opción de servicio inválida.");
            return;
        }

        System.out.print("Fecha (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
        System.out.print("Hora (HH:mm): ");
        LocalTime hora = LocalTime.parse(sc.nextLine());

        Turno turno = new Turno(0, mascotaId, tipoServicio, fecha, hora, EstadoTurno.PENDIENTE, null, creador.getId());
        boolean agendado = turnoDAO.agendar(turno);
        if (agendado) {
            System.out.println("Turno agendado correctamente.");
        } else {
            System.out.println("No se pudo agendar: ya se alcanzó el cupo de 10 baños para ese día.");
        }
    }

    private static void verAgendaDelDia() {
        System.out.print("Fecha a consultar (yyyy-MM-dd): ");
        LocalDate fecha = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ISO_LOCAL_DATE);
        List<Turno> turnos = turnoDAO.listarPorFecha(fecha);
        if (turnos.isEmpty()) {
            System.out.println("No hay turnos para ese día.");
        } else {
            turnos.forEach(System.out::println);
        }
    }

    // ---------- MENÚ BAÑADOR ----------
    private static void menuBanador(Usuario banador) {
        boolean salir = false;
        while (!salir) {
            System.out.println("\n--- MENÚ BAÑADOR ---");
            System.out.println("1. Ver agenda del día");
            System.out.println("2. Agendar turno");
            System.out.println("3. Finalizar turno");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            switch (sc.nextLine()) {
                case "1" -> verAgendaDelDia();
                case "2" -> agendarTurno(banador);
                case "3" -> finalizarTurno();
                case "0" -> salir = true;
                default -> System.out.println("Opción inválida.");
            }
        }
    }

    private static void finalizarTurno() {
        System.out.print("ID del turno a finalizar: ");
        int turnoId = Integer.parseInt(sc.nextLine());
        turnoDAO.finalizar(turnoId);
        System.out.println("Turno finalizado.");
    }
}
