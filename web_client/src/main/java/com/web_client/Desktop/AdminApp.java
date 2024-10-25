package com.web_client.Desktop;

import com.web_client.Model.CD;
import com.web_client.Model.Loan;
import com.web_client.logiquemetier.AdminOperationsBean;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.naming.InitialContext;


import java.util.List;

public class AdminApp {
    private AdminOperationsBean adminOps;

    public AdminApp() {
        try {
            InitialContext ctx = new InitialContext();
            adminOps = (AdminOperationsBean) ctx.lookup("java:global/logiquemetier/AdminOperationsBean");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showAddCdForm() {
        JFrame frame = new JFrame("Add CD");
        JTextField titleField = new JTextField(20);
        JTextField artistField = new JTextField(20);
        JTextField genreField = new JTextField(20);

        JButton addButton = new JButton("Add CD");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CD cd = new CD();
                cd.setTitle(titleField.getText());
                cd.setArtist(artistField.getText());
                adminOps.addCd(cd);
                JOptionPane.showMessageDialog(frame, "CD Added");
                frame.dispose(); // Ferme le formulaire après ajout
            }
        });

        frame.add(new JLabel("Title:"));
        frame.add(titleField);
        frame.add(new JLabel("Artist:"));
        frame.add(artistField);
        frame.add(new JLabel("Genre:"));
        frame.add(genreField);
        frame.add(addButton);

        frame.setLayout(new java.awt.FlowLayout());
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public void showEditCdForm(Long cdId) {
        JFrame frame = new JFrame("Edit CD");
        CD cd = adminOps.getCdById(cdId); // Récupère le CD à modifier
        JTextField titleField = new JTextField(cd.getTitle(), 20);
        JTextField artistField = new JTextField(cd.getArtist(), 20);

        JButton updateButton = new JButton("Update CD");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cd.setTitle(titleField.getText());
                cd.setArtist(artistField.getText());
                adminOps.updateCd(cd); // Met à jour le CD
                JOptionPane.showMessageDialog(frame, "CD Updated");
                frame.dispose(); // Ferme le formulaire après mise à jour
            }
        });

        frame.add(new JLabel("Title:"));
        frame.add(titleField);
        frame.add(new JLabel("Artist:"));
        frame.add(artistField);
        frame.add(updateButton);

        frame.setLayout(new java.awt.FlowLayout());
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public void showDeleteCdForm() {
        JFrame frame = new JFrame("Delete CD");
        JTextField idField = new JTextField(20);
        JButton deleteButton = new JButton("Delete CD");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Long cdId = Long.parseLong(idField.getText());
                adminOps.deleteCd(cdId); // Supprime le CD
                JOptionPane.showMessageDialog(frame, "CD Deleted");
            }
        });

        frame.add(new JLabel("CD ID:"));
        frame.add(idField);
        frame.add(deleteButton);

        frame.setLayout(new java.awt.FlowLayout());
        frame.setSize(300, 200);
        frame.setVisible(true);
    }

    public void showCurrentLoans() {
        JFrame frame = new JFrame("Current Loans");
        List<Loan> loans = adminOps.getCurrentloans(); // Récupère les emprunts en cours

        StringBuilder loanDetails = new StringBuilder();
        for (Loan loan : loans) {
            loanDetails.append("CD Title: ").append(loan.getCd().getTitle())
                    .append(", User ID: ").append(loan.getUserId())
                    .append(", Loan Date: ").append(loan.getLoanDate()).append("\n");
        }

        JTextArea textArea = new JTextArea(10, 30);
        textArea.setText(loanDetails.toString());
        textArea.setEditable(false);
        frame.add(new JScrollPane(textArea));

        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        AdminApp app = new AdminApp();

        app.showAddCdForm();

    }
}

