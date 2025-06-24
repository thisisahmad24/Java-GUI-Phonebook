package Phonebook;

import java.io.*;
import java.util.*;

public class PhonebookManager {
    Contact[] contacts = new Contact[100];
    int count = 0;
    String filePath = "contacts.txt";

    public PhonebookManager() {
        loadFromFile();
    }

    public void addContact(Contact c) {
        if (count < contacts.length) {
            contacts[count] = c;
            count++;
            saveToFile();
        }
    }

    public Contact search(String name) {
        for (int i = 0; i < count; i++) {
            if (contacts[i].name.equalsIgnoreCase(name)) {
                return contacts[i];
            }
        }
        return null;
    }

    public void delete(String name) {
        for (int i = 0; i < count; i++) {
            if (contacts[i].name.equalsIgnoreCase(name)) {
                for (int j = i; j < count - 1; j++) {
                    contacts[j] = contacts[j + 1];
                }
                contacts[count - 1] = null;
                count--;
                saveToFile();
                break;
            }
        }
    }

    public void edit(String name, String phone, String email) {
        Contact c = search(name);
        if (c != null) {
            c.phone = phone;
            c.email = email;
            saveToFile();
        }
    }

    public String getAllContacts() {
        StringBuilder all = new StringBuilder();
        for (int i = 0; i < count; i++) {
            all.append(contacts[i].toString()).append("\n");
        }
        return all.toString();
    }

    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filePath))) {
            for (int i = 0; i < count; i++) {
                pw.println(contacts[i].name + "," + contacts[i].phone + "," + contacts[i].email);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadFromFile() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null && count < contacts.length) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    contacts[count++] = new Contact(parts[0], parts[1], parts[2]);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
