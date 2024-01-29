package com.github.elemocrack;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final int MAX_USERS = 10;
    public static AtomicInteger registeredUsers = new AtomicInteger(0);

    public static AtomicInteger packageID = new AtomicInteger(0);

    public static List<User> users = new ArrayList<>();

    public static List<Package> packages = new  ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        defaultAccounts();
        int attempts = 2;
        boolean logged = false;
        int id = 0;
        while (true) {
            System.out.println("1 Registrarse \n2 Iniciar sesion \n3 Apagar programa");
            if (scanner.hasNextInt()) {
                int opciones = scanner.nextInt();
                switch (opciones) {
                    case 1:
                        //registro
                        System.out.println("Ingrese la su nombre");
                        String name = scanner.next();
                        if (existsAccount(name)) {
                            System.out.println("la cuenta ya esta registrada");
                            break;
                        }
                        System.out.println("Ingrese la contraseña");
                        String password = scanner.next();
                        newUser(name, password);
                        System.out.println("Cuenta registrada con exito");
                        break;
                    case 2:
                        sesion:
                        while (true) {
                            if (!logged) {
                                System.out.println("Ingrese su nombre");
                                String inputName = scanner.next();
                                System.out.println("Ingrese su contraseña");
                                String inputPassword = scanner.next();
                                if (attempts > 0) {
                                    for (int i = 0; i < users.size(); i++) {
                                        User user = users.get(i);
                                        if (user != null) {
                                            if (user.getUserName().equals(inputName) && user.getPassword().equals(inputPassword)) {
                                                System.out.println("Nombre correcto");
                                                logged = true;
                                                id = i;
                                                break;
                                            }
                                        }
                                    }
                                    if (!logged) {
                                        System.out.println("Nombre o contraseña invalidos");
                                    }
                                } else {
                                    System.out.println("Te quedaste sin intentos");
                                    return;
                                }
                                attempts--;
                            } else {
                                System.out.println("Ingrese una opcion \n1 Envio de paquete \n2 Mostrar paquetes enviados\n3 Cerrar sesion");
                                if (scanner.hasNextInt()) {
                                    int opcion = scanner.nextInt();
                                    switch (opcion) {
                                        case 1:
                                            System.out.println("Ingrese el peso del paquete");
                                            if(scanner.hasNextDouble()){
                                                double weight = scanner.nextDouble();
                                                System.out.println("Ingrese su nombre");
                                                String senderName = scanner.next();
                                                System.out.println("Ingrese el nombre del destinatario");
                                                String recipientName = scanner.next();
                                                System.out.println("Ingrese la direccion");
                                                String recipientAddress = scanner.next();
                                                sendPackage(weight, senderName, recipientName, recipientAddress);
                                                System.out.println("Usted tiene que pagar " + weight*2 + " usd para enviar este paquete");
                                            }else{
                                                System.out.println("El monto es numerico");
                                                scanner.nextLine();
                                            }
                                            break;
                                        case 2:
                                            showPackages();
                                            break;
                                        case 3:
                                            logged = false;
                                            break sesion;
                                        default:
                                            System.out.println("No es opcion valida");
                                            break;
                                    }
                                } else {
                                    System.out.println("Las opciones son numericas");
                                    scanner.nextLine();
                                }
                            }
                        }
                        break;
                    case 3:
                        return;
                    default:
                        System.out.println("Esa opcion no existe");
                        break;
                }
            } else {
                System.out.println("Las opciones son numericas");
                scanner.nextLine();
            }
        }


    }

    public static void newUser(String name, String password) {
        if (registeredUsers.get() >= MAX_USERS) {
            System.out.println("No se pueden registrar mas cuentas");
            return;
        }

        User user = new User();
        user.setUserName(name);
        user.setPassword(password);

        users.add(user);

        registeredUsers.incrementAndGet();
    }

    public static boolean existsAccount(String accountName) {
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getUserName().equalsIgnoreCase(accountName)) {
                return true;
            }
        }
        return false;
    }

    public static void defaultAccounts() {
        newUser("valen", "valen");

    }
    public static void sendPackage(double weight, String senderName, String recipientName, String recipientAddress){
        Package sendedPackage = new Package();
        sendedPackage.setWeight(weight);
        sendedPackage.setSenderName(senderName);
        sendedPackage.setRecipientName(recipientName);
        sendedPackage.setRecipientAddress(recipientAddress);
        sendedPackage.setPackageID(packageID.get());

        packages.add(sendedPackage);

        packageID.incrementAndGet();
    }

    public static void showPackages(){
        for(int i = 0; i < packages.size(); i++){
            System.out.println("---------------");
            System.out.println("Id: " + packages.get(i).getPackageID());
            System.out.println("weight: " + packages.get(i).getWeight());
            System.out.println("sender name: " + packages.get(i).getSenderName());
            System.out.println("recipient name: " + packages.get(i).getRecipientName());
            System.out.println("recipient address: " + packages.get(i).getRecipientAddress());
        }
    }

}