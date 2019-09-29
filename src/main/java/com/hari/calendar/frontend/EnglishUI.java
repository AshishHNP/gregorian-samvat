// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov Date: 4/15/2018 10:54:06 AM
// Home Page: http://members.fortunecity.com/neshkov/dj.html http://www.neshkov.com/dj.html - Check often for new
// version!
// Decompiler options: packimports(3)
// Source File Name: EnglishUI.java

package com.hari.calendar.frontend;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.hari.calendar.backend.EnglishToHinduOutput;

public class EnglishUI {
    private class PreviousNextListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (direction.equals("next")) {
                if (dayMonths.getSelectedIndex() < 30) dayMonths.setSelectedIndex(dayMonths.getSelectedIndex() + 1);
                else dayMonths.setSelectedIndex(0);
            } else if (dayMonths.getSelectedIndex() != 0) dayMonths.setSelectedIndex(dayMonths.getSelectedIndex() - 1);
            else dayMonths.setSelectedIndex(30);
            convertedTithi.setText(
                    EnglishToHinduOutput.getGregorianToHindu(new EnglishToHinduOutput((String)months.getSelectedItem(),
                            ((Integer)dayMonths.getSelectedItem()).intValue(), Long.parseLong(year.getText()))));
        }

        private String direction;
        final EnglishUI this$0;

        public PreviousNextListener(String direction) {
            this$0 = EnglishUI.this;
            this.direction = direction;
        }
    }

    public EnglishUI() {
        months = new JComboBox(EnglishToHinduOutput.gregorianMonths);
        months.setFont(new Font("Arial Unicode MS", 0, 60));
        dayMonths = new JComboBox();
        dayMonths.setFont(new Font("Arial Unicode MS", 0, 60));
        year = new JTextField("Enter Year");
        year.setFont(new Font("Arial Unicode MS", 0, 60));
        actionButton = new JButton("Convert to Samvat");
        actionButton.setFont(new Font("Arial Unicode MS", 0, 20));
        next = new JButton("Next");
        next.setFont(new Font("Arial Unicode MS", 0, 20));
        previous = new JButton("Previous");
        previous.setFont(new Font("Arial Unicode MS", 0, 20));
        convertedTithi = new JButton("   Converted Tithi       ");
        convertedTithi.setFont(new Font("Arial Unicode MS", 1, 80));
    }

    public void addComponentsToPane(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = 2;
        c.weightx = 0.5D;
        c.fill = 2;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(months, c);
        for (int i = 1; i < 32; i++)
            dayMonths.addItem(Integer.valueOf(i));

        c.fill = 2;
        c.weightx = 0.5D;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(dayMonths, c);
        c.fill = 2;
        c.weightx = 0.5D;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(year, c);
        c.fill = 2;
        c.ipady = 0;
        c.weighty = 1.0D;
        c.gridx = 1;
        c.gridwidth = 1;
        c.gridy = 1;
        pane.add(actionButton, c);
        c.fill = 2;
        c.ipady = 0;
        c.weighty = 1.0D;
        c.gridx = 2;
        c.gridwidth = 1;
        c.gridy = 1;
        pane.add(next, c);
        c.fill = 2;
        c.ipady = 0;
        c.weighty = 1.0D;
        c.gridx = 0;
        c.gridwidth = 1;
        c.gridy = 1;
        pane.add(previous, c);
        c.fill = 2;
        c.ipady = 40;
        c.weightx = 0.0D;
        c.anchor = 1792;
        c.gridwidth = 10;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(convertedTithi, c);
        actionButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                convertedTithi.setText(EnglishToHinduOutput
                        .getGregorianToHindu(new EnglishToHinduOutput((String)months.getSelectedItem(),
                                ((Integer)dayMonths.getSelectedItem()).intValue(), Long.parseLong(year.getText()))));
            }

            final EnglishUI this$0;

            {
                this$0 = EnglishUI.this;
            }
        });
        next.addActionListener(new PreviousNextListener("next"));
        previous.addActionListener(new PreviousNextListener("previous"));
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Gregorian Converter");
        frame.setDefaultCloseOperation(3);
        addComponentsToPane(frame.getContentPane());
        frame.pack();
        frame.setVisible(true);
    }

    // public static void main(String args[]) {
    // EnglishUI gbld = new EnglishUI();
    // gbld.createAndShowGUI();
    // }

    static final boolean shouldFill = true;
    static final boolean shouldWeightX = true;
    private JComboBox months;
    private JComboBox dayMonths;
    private JTextField year;
    private JButton convertedTithi;
    private JButton next;
    private JButton previous;
    private JButton actionButton;

}
