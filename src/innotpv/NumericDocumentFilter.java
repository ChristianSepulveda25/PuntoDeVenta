/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package innotpv;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
/**
 *
 * @author ChristianSepulveda
 */
public class NumericDocumentFilter extends DocumentFilter {

    private final int maxLength;

    public NumericDocumentFilter(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string,
                             AttributeSet attr) throws BadLocationException {
        if (string == null) return;

        if (string.matches("\\d*") &&
            fb.getDocument().getLength() + string.length() <= maxLength) {
            super.insertString(fb, offset, string, attr);
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
                        AttributeSet attrs) throws BadLocationException {
        if (text == null) return;

        int newLength = fb.getDocument().getLength() - length + text.length();

        if (text.matches("\\d*") && newLength <= maxLength) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}
