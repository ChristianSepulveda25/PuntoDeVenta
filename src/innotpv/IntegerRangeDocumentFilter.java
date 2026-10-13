/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package innotpv;

/**
 *
 * @author ChristianSepulveda
 */
import javax.swing.text.*;

public class IntegerRangeDocumentFilter extends DocumentFilter {
    private int maxValue;

    public IntegerRangeDocumentFilter(int maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) return;
        if (isValid(fb, offset, 0, string)) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) text = "";
        if (isValid(fb, offset, length, text)) {
            super.replace(fb, offset, length, text, attrs);
        }
    }

    private boolean isValid(FilterBypass fb, int offset, int length, String textToAdd) throws BadLocationException {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, textToAdd);

        // Permitir que el campo quede vacío temporalmente mientras el usuario borra
        if (sb.length() == 0) {
            return true;
        }

        try {
            int value = Integer.parseInt(sb.toString());
            // Validar que esté entre 0 y el valor máximo (100) y que no tenga ceros a la izquierda innecesarios (ej. "05")
            return value >= 0 && value <= maxValue && sb.toString().equals(String.valueOf(value));
        } catch (NumberFormatException e) {
            return false; // Si no es un número válido (letras, símbolos, puntos), lo rechaza
        }
    }
}
