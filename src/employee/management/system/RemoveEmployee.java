package employee.management.system;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;

public class RemoveEmployee extends JFrame implements ActionListener {

    Choice choiceEMPID;
    JButton delete, back;

    public RemoveEmployee() {
        // Set up the frame
        setTitle("Remove Employee");
        setSize(1000, 400);
        setLocation(300, 150);
        setLayout(null);

        // Employee ID Label and Choice
        JLabel label = new JLabel("Employee ID");
        label.setBounds(50, 50, 100, 30);
        label.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(label);

        choiceEMPID = new Choice();
        choiceEMPID.setBounds(200, 50, 150, 30);
        add(choiceEMPID);

        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT empID FROM employee");
            while (resultSet.next()) {
                choiceEMPID.add(resultSet.getString("empID"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Name Label and Text
        JLabel labelName = new JLabel("Name");
        labelName.setBounds(50, 100, 100, 30);
        labelName.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(labelName);

        JLabel textName = new JLabel();
        textName.setBounds(200, 100, 100, 30);
        add(textName);

        // Phone Label and Text
        JLabel labelPhone = new JLabel("Phone");
        labelPhone.setBounds(50, 150, 100, 30);
        labelPhone.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(labelPhone);

        JLabel textPhone = new JLabel();
        textPhone.setBounds(200, 150, 100, 30);
        add(textPhone);

        // Email Label and Text
        JLabel labelemail = new JLabel("Email");
        labelemail.setBounds(50, 200, 100, 30);
        labelemail.setFont(new Font("Tahoma", Font.BOLD, 15));
        add(labelemail);

        JLabel textEmail = new JLabel();
        textEmail.setBounds(200, 200, 100, 30);
        add(textEmail);

        // Load employee details when an ID is selected
        loadEmployeeDetails(choiceEMPID.getSelectedItem(), textName, textPhone, textEmail);

        choiceEMPID.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                loadEmployeeDetails(choiceEMPID.getSelectedItem(), textName, textPhone, textEmail);
            }
        });

        // Delete Button
        delete = new JButton("Delete");
        delete.setBounds(80, 300, 100, 30);
        delete.setBackground(Color.BLACK);
        delete.setForeground(Color.WHITE);
        delete.addActionListener(this);
        add(delete);

        // Back Button
        back = new JButton("Back");
        back.setBounds(220, 300, 100, 30);
        back.setBackground(Color.BLACK);
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        // Background Image
        ImageIcon i11 = new ImageIcon(ClassLoader.getSystemResource("icons/rback.png"));
        Image i22 = i11.getImage().getScaledInstance(1120, 630, Image.SCALE_DEFAULT);
        ImageIcon i33 = new ImageIcon(i22);
        JLabel imgg = new JLabel(i33);
        imgg.setBounds(0, 0, 1120, 630);
        add(imgg);

        setVisible(true);
    }

    private void loadEmployeeDetails(String empID, JLabel textName, JLabel textPhone, JLabel textEmail) {
        try {
            conn c = new conn();
            ResultSet resultSet = c.statement.executeQuery("SELECT * FROM employee WHERE empID = '" + empID + "'");
            if (resultSet.next()) {
                textName.setText(resultSet.getString("name"));
                textPhone.setText(resultSet.getString("phone"));
                textEmail.setText(resultSet.getString("email"));
            }
            c.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == delete) {
            try {
                conn c = new conn();
                String query = "DELETE FROM employee WHERE empID = '" + choiceEMPID.getSelectedItem() + "'";
                int rowsAffected = c.statement.executeUpdate(query);

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Employee Deleted Successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "Employee Deletion Failed");
                }
                c.close();
                setVisible(false);
                new Main_class();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == back) {
            setVisible(false);
            new Main_class();
        }
    }

    public static void main(String[] args) {
        new RemoveEmployee();
    }
}
